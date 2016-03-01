/**
 * Copyright (c) 2007-2015, Institute of Information Systems (Sven Groppe and contributors of LUPOSDATE), University of Luebeck
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 	- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * 	  disclaimer.
 * 	- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * 	  following disclaimer in the documentation and/or other materials provided with the distribution.
 * 	- Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
 * 	  products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package lupos.distributed.p2p.query.withsubgraph;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lupos.datastructures.bindings.Bindings;
import lupos.datastructures.bindings.BindingsFactory;
import lupos.datastructures.items.literal.LiteralFactory;
import lupos.datastructures.items.literal.LiteralFactory.MapType;
import lupos.datastructures.queryresult.QueryResult;
import lupos.distributed.operator.ISubgraphExecutor;
import lupos.distributed.operator.format.operatorcreator.IOperatorCreator;
import lupos.distributed.operator.format.operatorcreator.QueryClientOperatorCreator;
import lupos.distributed.p2p.network.AbstractP2PNetwork;
import lupos.distributed.p2p.network.IP2PMessageListener;
import lupos.distributed.p2p.storage.StorageWithDistributionStrategy;
import lupos.distributed.query.QueryClient;
import lupos.distributed.storage.IStorage;
import lupos.distributed.storage.distributionstrategy.tripleproperties.KeyContainer;
import lupos.distributed.storage.util.LocalExecutor;
import lupos.endpoint.client.formatreader.JSONFormatReader;
import lupos.endpoint.client.formatreader.MIMEFormatReader;
import lupos.endpoint.client.formatreader.XMLFormatReader;
import lupos.endpoint.server.format.Formatter;
import lupos.endpoint.server.format.JSONFormatter;
import lupos.endpoint.server.format.XMLFormatter;
import lupos.engine.evaluators.BasicIndexQueryEvaluator;
import lupos.engine.evaluators.QueryEvaluator;
import lupos.engine.operators.index.Dataset;
import lupos.misc.Tuple;

import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * This is an implementation of {@link lupos.distributed.operator.ISubgraphExecutor} which is used in
 * P2P-QueryClients for receiving subgraph messages, evaluating these subgraphs
 * and answering the request with the local result.
 *
 * @author Bjoern
 * @param <T>
 *            The key container type
 * @version $Id: $Id
 */
public class P2P_SubgraphExecuter<T> implements
		ISubgraphExecutor<KeyContainer<T>>, IP2PMessageListener {
	/**
	 * Maximal length of a serialized subgraph in logging output
	 */
	public static final int MAX_DEBUG_LENGTH = 200;

	/**
	 * Maximal parall request handling
	 */
	public static int NUMBER_OF_PARALLEL_REQUESTS = 20;

	/**
	 * Displays the current state of open processes for subgraphs
	 */
	public static boolean SHOW_DEBUG_PROCESSINGS_LIST = false;

	/**
	 * Static helper class for parsing and creating header for input-stream
	 * message to be sent and received.
	 *
	 * @author Bjoern
	 *
	 */
	public static class P2PInputStreamMessage {
		public static final int HEADING_BYTES = 37;

		private P2PInputStreamMessage() {
		}

		/**
		 * Creates a header for the input stream that is added first
		 *
		 * @param messageID
		 *            the message number (unique)
		 * @param answer
		 *            is this a answer message?
		 * @return the header bytes
		 */
		public static byte[] createHeader(final String messageID, final boolean answer) {
			final byte[] bytes = ((answer ? "1" : "0") + messageID + "").getBytes();
			if (bytes.length != HEADING_BYTES) {
				throw new RuntimeException(
						"Headings for inputstream message are not in defined size. Needs a header of "
								+ HEADING_BYTES + " bytes length.");
			}
			return bytes;
		}

		/**
		 * Parses an inputstream and removes the header of this steam
		 *
		 * @param is
		 *            the inputstream (should be starting at byte 0)
		 * @return the tuple containing the message-id and the identifier,
		 *         whether the message is an answer or not (so it's a request)
		 * @throws IOException
		 *             error while parsing
		 */
		public static Tuple<String, Boolean> parseInputStream(final InputStream is)
				throws IOException {
			final byte[] b = new byte[HEADING_BYTES];
			is.read(b);
			final String s = new String(b);
			if (s == null || s.length() != HEADING_BYTES) {
				throw new IOException("Message's header is wrong.");
			}
			return new Tuple<String, Boolean>(s.substring(1),
					(s.charAt(0) == '1') ? true : false);
		}
	}

	/**
	 * Java Object for sending subgraphs and other messages
	 *
	 * @author Bjoern
	 *
	 */
	public static class P2PMessage {
		/*
		 * type of message: request of subgraph-evaluation
		 */
		private static final String SUBGRAPH_REQUEST = "sgReq";
		/*
		 * type of message: answer / result of subgraph-evaluation (back to
		 * asking node)
		 */
		private static final String SUBGRAPH_RESPONSE = "sgResponse";

		private String type;
		private String id;
		private String message;
		private String key;

		/**
		 * New P2P Message with the given type, id and message
		 *
		 * @param type
		 *            the message type (see constants)
		 * @param id
		 *            the unique id (answer of a request should match)
		 * @param message
		 *            the message in string representation
		 */
		public P2PMessage(final String type, final String id, final String message) {
			this.type = type;
			this.id = id;
			this.message = message;
		}

		/**
		 * New P2P Message with the given type, id and message
		 *
		 * @param type
		 *            the message type (see constants)
		 * @param id
		 *            the unique id (answer of a request should match)
		 * @param message
		 *            the message in string representation
		 * @param key
		 *            the distribution key
		 */
		public P2PMessage(final String type, final String id, final String message, final String key) {
			this(type, id, message);
			this.key = key;
		}

		@Override
		public String toString() {
			if (this.key == null) {
				return String.format("%s##%s##%s", this.type, this.id, this.message);
			} else {
				return String.format("%s##%s##%s##%s", this.type, this.id, this.message, this.key);
			}
		}

		/**
		 * Function of unmarshalling a P2P-Message from string-representation
		 * into java object
		 *
		 * @param s
		 *            the string representation of a
		 *            P2PMessage#toString()-String
		 * @return the object or error, if not parseable
		 */
		public static P2PMessage parse(final String s) {
			if (s != null) {
				final String[] splitted = s.split("##");
				if (splitted.length == 3) {
					return new P2PMessage(splitted[0], splitted[1], splitted[2]);
				} else if (splitted.length == 4) {
					return new P2PMessage(splitted[0], splitted[1],
							splitted[2], splitted[3]);
				}
			}
			throw new RuntimeException("Not parsable: " + s);
		}
	}

	/**
	 * Time in seconds to wait for a result ...
	 */
	public static long SLEEP = 500000;

	private AbstractP2PNetwork<?> p2pNetwork;
	private IStorage storage;
	private final ExecutorService executer;


		public final Runnable displayMap = new Runnable() {
			@Override
			public void run() {
				final StringBuilder b = new StringBuilder();
				b.append("\n");
				final Set<String> set = P2P_SubgraphExecuter.this.waitingResults.keySet();
				b.append("SubgraphExecuter - Active processes:");
				b.append(set.size());
				b.append("\n");
				b.append("--------------------------------------------------------------------\n");
				b.append("#    |    message-id                                    |  done\n");
				b.append("--------------------------------------------------------------------\n");
				final int before = b.length();
				int counter = 0;
				for (final String s : set) {
					final DelayedResult<?> item = P2P_SubgraphExecuter.this.waitingResults.get(s);
					final boolean done = item.isDone();
					b.append(this.toSize(++counter,(""+set.size()).length()) + ">        " + s + "             " + done + "\n");
				}
				if (b.length() == before) {
					b.append("> no active process found\n");
				}
				b.append("--------------------------------------------------------------------\n");
				b.append("\n");
				System.out.println(b.toString());
			}

			private String toSize(final int i, final int length) {
				String str = i+"";
				while (str.length() != length) {
					str = "0" + str;
				}
				return str;
			}
		};



	/**
	 * New instance of a subgraph executer, conntected with nodes of a
	 * P2P-network.
	 */
	public P2P_SubgraphExecuter() {
		this.executer = Executors
				.newFixedThreadPool(NUMBER_OF_PARALLEL_REQUESTS);
		if (SHOW_DEBUG_PROCESSINGS_LIST) {
			final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

			scheduler.scheduleAtFixedRate(this.displayMap, 0, 1, TimeUnit.MINUTES);
		}
	}

	private final Logger l = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 *
	 * This is called by the {@link QueryClient} in LuposDate, because this is
	 * registered as {@link ISubgraphExecutor}, for evaluation subgraphs.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public QueryResult evaluate(final KeyContainer<T> key,
			final String subgraphSerializedAsJSON, final BindingsFactory bindingsFactory) {
		/*
		 * Store bindings factory on first evaluation
		 */
		if (this.bindingsFactory == null) {
			this.bindingsFactory = bindingsFactory;
		}
		/*
		 * get unique id
		 */
		final String id = UUID.randomUUID().toString();
		String p2pKey = "";

		if (!this.hasP2PNetwork()) {
			throw new RuntimeException(
					"No P2P-network connected for sending subgraph-container.");
		}
		final AbstractP2PNetwork<?> network = this.getP2PNetwork();

		/*
		 * get the node id, where to sent the subgraph by the given key
		 */
		if (this.hasStorage()
				&& this.getStorage() instanceof StorageWithDistributionStrategy) {
			p2pKey = ((StorageWithDistributionStrategy<T>) this.getStorage())
					.getKey(key);
		} else {
			/*
			 * if no storage connected, use this as key.
			 */
			p2pKey = String.format("%s%s", key.type, key.key);
		}

		this.l.debug(String.format("Sending to node %s subgraph: %s ", p2pKey,
				subgraphSerializedAsJSON));

		/*
		 * send subgraph to the corresponding node
		 */
		final P2PMessage messageToSend = new P2PMessage(P2PMessage.SUBGRAPH_REQUEST,
				id, subgraphSerializedAsJSON, p2pKey);

		try {

			this.l.debug(String.format("Waiting for answer of message-id: %s", id));
			DelayedResult<QueryResult> waitingResult;
			this.waitingResults.put(id,
					waitingResult = new DelayedResult<QueryResult>());
			network.sendMessage(p2pKey, messageToSend.toString());

			QueryResult queryResult = new QueryResult();
			try {
				queryResult = waitingResult.get(SLEEP, TimeUnit.SECONDS);
				this.l.debug(String.format("Waiting for answer of message-id: %s: finished", id));
			} catch (final ExecutionException e) {
				this.l.error(String.format(
						"Error waiting for result of message-id %s", id), e);
				final RuntimeException re = new RuntimeException(String.format(
						"Error waiting for result of message-id %s", id));
				re.addSuppressed(e);
				throw re;
			} catch (final TimeoutException e) {
				this.l.error(String.format(
						"Error waiting for result of message-id %s, timeout!",
						id), e);
				final RuntimeException re = new RuntimeException(String.format(
						"Error waiting for result of message-id %s, timout!",
						id));
				re.addSuppressed(e);
				throw re;
			}
			return queryResult;
		} catch (final InterruptedException e) {
			this.l.error(String
					.format("Error waiting of result for subgraph from node %s",
							p2pKey), e);
		}
		// Wait for answer of message id
		this.l.warn(String
				.format("Error waiting of result for subgraph from node %s. So return empty query-result.",
						p2pKey));
		return new QueryResult();
	}

	/**
	 * DelayedResult is a {@link Future}, that waits until the result is set. <br>
	 * This is internally used for the asynchrony answer of subgraph
	 * submissions, where this object waits, until the response message arrived.
	 *
	 * @author Bjoern
	 *
	 * @param <T>
	 *            The type of the result that is used (in our case,
	 *            {@link QueryResult}, because the subgraph's answer is always a
	 *            {@link QueryResult})
	 */
	@SuppressWarnings("hiding")
	private class DelayedResult<T> implements Future<T> {

		private static final long SLEEP = 500;
		private final AtomicBoolean canceled = new AtomicBoolean(false);
		private final AtomicBoolean hasResult = new AtomicBoolean(false);
		private T result = null;

		/**
		 * Sets the result (if the result is set, the {@link Future} gets its
		 * answer)
		 *
		 * @param result
		 *            the result which is set
		 */
		public void setResult(final T result) {
			synchronized (result) {
				this.result = result;
			}
			this.hasResult.set(true);
		}

		@Override
		public boolean cancel(final boolean mayInterruptIfRunning) {
			return false;
		}

		@Override
		public boolean isCancelled() {
			return this.canceled.get();
		}

		@Override
		public boolean isDone() {
			return this.hasResult.get();
		}

		@Override
		public T get() throws InterruptedException, ExecutionException {
			try {
				/*
				 * wait 1 hour, if failed, then another 1 hour ... (yeah, we are
				 * very pessimistic ... :D )
				 */
				return this.get(1, TimeUnit.HOURS);
			} catch (final TimeoutException e) {
				return this.get();
			}
		}

		@Override
		public T get(final long timeout, final TimeUnit unit) throws InterruptedException,
				ExecutionException, TimeoutException {
			/*
			 * take the maximum waiting time
			 */
			long start = TimeUnit.MILLISECONDS.convert(timeout, unit);
			while (true) {
				/*
				 * abort, if canceled or result is set
				 */
				if (this.canceled.get() || this.hasResult.get()) {
					break;
				}
				start -= SLEEP;
				/*
				 * sleep, only for better CPU usage
				 */
				TimeUnit.MILLISECONDS.sleep(SLEEP);
				/*
				 * if time is over, throw the error
				 */
				if (start < 0) {
					throw new TimeoutException(String.format(
							"Timeout: %d %s are gone!", timeout, unit.name()));
				}
			}
			/*
			 * if there is a result, return the result!
			 */
			if (this.hasResult.get()) {
				final T ret;
				synchronized (this.result) {
					ret = this.result;
				}
				return ret;
			} else {
				/* otherwise null, so canceled */
				return null;
			}
		}
	}

	/**
	 * Is any p2p network ( {@link lupos.distributed.p2p.network.AbstractP2PNetwork} ) instance connected with
	 * this {@link lupos.distributed.operator.ISubgraphExecutor}
	 *
	 * @return a boolean.
	 */
	protected boolean hasP2PNetwork() {
		return this.p2pNetwork != null;
	}

	/**
	 * Is any {@link lupos.distributed.storage.IStorage} instance connected with this
	 * {@link lupos.distributed.operator.ISubgraphExecutor}
	 *
	 * @return a boolean.
	 */
	protected boolean hasStorage() {
		return this.storage != null;
	}

	/**
	 * Returns the p2p-network conntected, if any instance connected.
	 *
	 * @return p2p-network for sending messages
	 */
	public AbstractP2PNetwork<?> getP2PNetwork() {
		return this.p2pNetwork;
	}

	/**
	 * Sets a new p2p-network ( {@link lupos.distributed.p2p.network.AbstractP2PNetwork} ) for this
	 * {@link lupos.distributed.operator.ISubgraphExecutor}.
	 *
	 * @param p2pNetwork
	 *            The new network to set.
	 */
	public void setP2PNetwork(final AbstractP2PNetwork<?> p2pNetwork) {
		/*
		 * only set if instance given.
		 */
		if (p2pNetwork == null) {
			return;
		}

		/*
		 * if overwriting existing connected p2p-implementation network, remove
		 * old listener and add new listener to actual network.
		 */
		if (this.hasP2PNetwork()) {
			this.getP2PNetwork().removeMessageListener(this);
		}
		this.p2pNetwork = p2pNetwork;
		if (this.hasP2PNetwork()) {
			this.getP2PNetwork().addMessageListener(this);
		}
	}

	/**
	 * Gets the {@link lupos.distributed.storage.IStorage} for resolving key as node in p2p-network.
	 *
	 * @return the storage
	 */
	public IStorage getStorage() {
		return this.storage;
	}

	/**
	 * Sets / Connects the {@link lupos.distributed.storage.IStorage} used in {@link lupos.distributed.query.QueryClient}.
	 *
	 * @param storage
	 *            the new storage
	 */
	public void setStorage(final IStorage storage) {
		if (storage == null) {
			return;
		}
		this.storage = storage;
	}

	private QueryEvaluator<?> evaluator;

	/**
	 * Gets the local evaluator of the node (for evaluation incoming subgraphs)
	 *
	 * @return the evaluator
	 */
	public QueryEvaluator<?> getEvaluator() {
		return this.evaluator;
	}

	/**
	 * Sets the local evaluator for evaluating incoming subgraphs
	 *
	 * @param evaluator
	 *            the local evaluater
	 */
	public void setEvaluator(final QueryEvaluator<?> evaluator) {
		this.evaluator = evaluator;
	}

	/**
	 * Is any evaluater for evaluating local subgraphs connected with this
	 * {@link lupos.distributed.operator.ISubgraphExecutor}?
	 *
	 * @return a boolean.
	 */
	protected boolean hasEvaluator() {
		return this.getEvaluator() != null;
	}

	/*
	 * store results of subgraphs in this map and inform waiting instance, that
	 * result is back.
	 */
	private final Map<String, DelayedResult<QueryResult>> waitingResults = Collections
			.synchronizedMap(new HashMap<String, DelayedResult<QueryResult>>());

	private BindingsFactory bindingsFactory = null;


	/*
	 * If any answer (response) is received with a given id, store the answer
	 * here. The waiting thread for this message is called internally.
	 */
	private void addMessageResult(final String id, final QueryResult qr) {
		final DelayedResult<QueryResult> result = this.waitingResults.get(id);
		if (result != null) {
			result.setResult(qr);
		} else {
			throw new RuntimeException("Error in " + this + " - no entry: "
					+ id);
		}
		if (SHOW_DEBUG_PROCESSINGS_LIST) {
			this.displayMap.run();
		}
	}

	/*
	 * If any answer (response) is received with a given id, store the answer
	 * here. The waiting thread for this message is called internally.
	 */
	private void addMessageResult(final String id, final String message) {

		final MIMEFormatReader reader = new JSONFormatReader();
		// read in the message (JSON message result)
		Bindings.instanceClass = lupos.datastructures.bindings.BindingsMap.class;

		QueryResult qr = null;
		try {
			qr = reader.getQueryResult(
					new ByteArrayInputStream(message.getBytes("UTF-8")),
					message,this.bindingsFactory);
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		final DelayedResult<QueryResult> result = this.waitingResults.get(id);
		if (result != null) {
			result.setResult(qr);
		} else {
			throw new RuntimeException("Error in " + this + " - no entry: "
					+ id);
		}
		if (SHOW_DEBUG_PROCESSINGS_LIST) {
			this.displayMap.run();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMessage(final String message, final String from) {
		this.l.debug(String.format(
				"Got message via P2P-network from %s: %s",
				from,
				message.substring(0,
						Math.min(message.length(), MAX_DEBUG_LENGTH))));
		/*
		 * get the JSON formatter, because we know, subgraphs are serialized in
		 * JSON
		 */
		final Formatter formatter = new JSONFormatter();

		/*
		 * try to parse the message
		 */
		P2PMessage msg = null;
		try {
			msg = P2PMessage.parse(message);
		} catch (final RuntimeException e) {
			this.l.error(String.format(
					"Error unmarshalling received message from %s: %s", from,
					message), e);
		}

		/*
		 * if this message is a response of subgraph requests, store this result
		 * and inform waiting instance, that result is received.
		 */
		if (msg.type != null && msg.type.equals(P2PMessage.SUBGRAPH_RESPONSE)) {
			this.l.debug(String.format(
					"Got result from message %s: %s",
					msg.id,
					msg.message.substring(0,
							Math.min(msg.message.length(), MAX_DEBUG_LENGTH))));
			/*
			 * add the result and inform the waiting thread
			 */
			this.addMessageResult(msg.id, msg.message);
			return;
		} else if (msg.type != null
				&& msg.type.equals(P2PMessage.SUBGRAPH_REQUEST)) {
			/*
			 * so here, the message is a request, so evaluate the request with
			 * local evaluator and sent the result back
			 */
			// Get the subgraph and data for unmarshalling the subgraph into
			// operator graph.
			final String subgraphSerializedAsJSONString = msg.message;
			if (!this.hasEvaluator()) {
				throw new RuntimeException(
						"No local executer found for evaluation subgraph.");
			}

			final String _answerId = msg.id;
			final Runnable task = new Runnable() {

				@Override
				public void run() {
					// get the local evaluator and things for unmarshalling data
					final QueryEvaluator<?> eval = P2P_SubgraphExecuter.this.getEvaluator();
					Dataset dataset = null;
					// get the creator
					final IOperatorCreator creater = new QueryClientOperatorCreator();
					// get the dataset
					if (eval instanceof BasicIndexQueryEvaluator) {
						dataset = ((BasicIndexQueryEvaluator) eval)
								.getDataset();
					}
					try {
						/*
						 * now evaluate the subgraph and get the result back as
						 * Tuple, where the first component is the serialized
						 * result, and the second is the MIME-type (would be
						 * here: JSON)
						 */
						P2P_SubgraphExecuter.this.l.debug(String.format(
								"Local executing subgraph: %s on %s",
								subgraphSerializedAsJSONString, this));


						LiteralFactory.setType(MapType.NOCODEMAP);

						/*
						 * this message request is sent via streaming, because
						 * P2P network does support
						 */
						if (P2P_SubgraphExecuter.this.getP2PNetwork().supportsStreaming()) {
							/*
							 * for streaming we use XML als formatter
							 */
							final Formatter _formatter = new XMLFormatter();
							/*
							 * create header and evaluate the subgraph
							 */
							final byte[] head = P2PInputStreamMessage.createHeader(
									_answerId, true);
							final InputStream is = LocalExecutor.evaluateSubgraphAndGetStream(
											subgraphSerializedAsJSONString,
											dataset, creater, _formatter,
											P2P_SubgraphExecuter.this, head);
							if (P2P_SubgraphExecuter.this.hasP2PNetwork()) {
								P2P_SubgraphExecuter.this.l.debug(String.format(
										"Sending streaming-result from %s",
										from));
								P2P_SubgraphExecuter.this.getP2PNetwork().sendMessageTo(from, is);
							}
							P2P_SubgraphExecuter.this.l.debug(String.format(
									"Sending streaming-result from %s: finished",
									from));
							return;
						}
						// else: send the result as JSON-String ...

						final Tuple<String, String> result = LocalExecutor
								.evaluateSubgraphAndReturnSerializedResult(
										subgraphSerializedAsJSONString,
										dataset, creater, formatter,
										P2P_SubgraphExecuter.this);
						// JSON-result, so send this as result to the node, that
						// asked
						// for this.

						final String returnedSubgraph = result.getFirst();
						if (P2P_SubgraphExecuter.this.hasP2PNetwork()) {
							P2P_SubgraphExecuter.this.l.debug(String
									.format("Local executed subgraph result \"%s\" for request \"%s\" sent back to node %s",
											returnedSubgraph.substring(Math
													.min(returnedSubgraph
															.length(),
															MAX_DEBUG_LENGTH)),
											subgraphSerializedAsJSONString,
											from));
							/*
							 * important: create answer with the same id as in
							 * the request sent, so that the receiver knows,
							 * that this is the result.
							 */
							final P2PMessage msg = new P2PMessage(
									P2PMessage.SUBGRAPH_RESPONSE, _answerId,
									returnedSubgraph);
							P2P_SubgraphExecuter.this.getP2PNetwork().sendMessageTo(from, msg.toString());
							P2P_SubgraphExecuter.this.l.debug(String
									.format("Local executed subgraph result \"%s\" for request \"%s\" sent back to node %s: finished",
											returnedSubgraph.substring(Math
													.min(returnedSubgraph
															.length(),
															MAX_DEBUG_LENGTH)),
											subgraphSerializedAsJSONString,
											from));
						}
						// MIME-type, unused here
						result.getSecond();
					} catch (JSONException | IOException e) {
						P2P_SubgraphExecuter.this.l.error("Error parsing and creating JSON subgraph", e);
					}
				}
			};
			final Runnable taskWrapper = new Runnable() {

				@Override
				public void run() {
					final int taskID = P2P_SubgraphExecuter.this.taskCounter.getAndIncrement();
					System.out.println("Starting task: " + taskID + " (total allowed: " + NUMBER_OF_PARALLEL_REQUESTS + ")");
					task.run();
					System.out.println("Ending task: " + taskID + " (total allowed: " + NUMBER_OF_PARALLEL_REQUESTS + ")");
				}
			};
			/*
			 * executes the task (parallel, if NUMBER_OF_PARALLISM greater than 1)
			 */
			this.executer.execute(taskWrapper);

		}
	}

	private final AtomicInteger taskCounter = new AtomicInteger();

	/** {@inheritDoc} */
	@Override
	public void onMessage(InputStream in, final String from) {

		try {
			final Tuple<String, Boolean> result = P2PInputStreamMessage
					.parseInputStream(in);
			//read out the rest of the stram as XML ...
			LiteralFactory.setType(MapType.NOCODEMAP);


			final XMLFormatReader fr = new XMLFormatReader();
			//read in the query result
			final QueryResult qr = fr.getQueryResult(in,this.bindingsFactory);


			final long time = new Date().getTime();
			final int tripelSize = 0;
			long difference = new Date().getTime() - time;
  		    final StringBuilder b = new StringBuilder();

  		    b.append(String.format("Statistics: reading %s triples in %s h or %d min or %d sec",
					  tripelSize, TimeUnit.MILLISECONDS.toHours(difference),TimeUnit.MILLISECONDS.toMinutes(difference),TimeUnit.MILLISECONDS.toSeconds(difference)));
			if (difference == 0) {
				difference = 1;
			}
			final double triplesPerSecond = tripelSize / (difference / 1000d);
			b.append(String.format(" -> %.2f triples per second",triplesPerSecond));
			Logger.getLogger(this.getClass()).debug(b.toString());

			try {
				in.close();
			} catch (final Exception e) {
				e.printStackTrace();
			} finally {
				in = null;
			}

			/*
			 * inform the waiting thread that answer is back for the message
			 */
			this.addMessageResult(/*message id*/result.getFirst(), qr);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}


}
