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
package lupos.event.producer;

import java.util.ArrayList;
import java.util.List;

import lupos.datastructures.items.Triple;
import lupos.datastructures.items.literal.*;
import lupos.event.communication.*;
import lupos.event.util.Literals;


/**
 * Creates an event without additional properties every second.
 *
 * @author groppe
 * @version $Id: $Id
 */
public class EmptyProducer extends ProducerBase {

	/** Constant <code>NAMESPACE="http://localhost/events/Empty/"</code> */
	public static final String NAMESPACE = "http://localhost/events/Empty/";
	private static final int INTERVAL = 1000;	

	private static final Literal TYPE = Literals.createURI(EmptyProducer.NAMESPACE, "EmptyEvent");
	
	/**
	 * <p>Constructor for EmptyProducer.</p>
	 *
	 * @param msgService a {@link lupos.event.communication.SerializingMessageService} object.
	 */
	public EmptyProducer(SerializingMessageService msgService) {
		super(msgService, INTERVAL);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<List<Triple>> produce() {
		List<Triple> triples = new ArrayList<Triple>();
		triples.add(new Triple(Literals.AnonymousLiteral.ANONYMOUS, Literals.RDF.TYPE, EmptyProducer.TYPE));
		return ProducerBase.fold(triples);
	}
	
	
	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 * @throws java.lang.Exception if any.
	 */
	public static void main(String[] args) throws Exception {
		// create communication channel
		SerializingMessageService msgService = ProducerBase.connectToMaster();
		
		// start producer
		new EmptyProducer(msgService).start();
	}
}
