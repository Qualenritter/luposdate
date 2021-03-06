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
package lupos.misc.debug;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lupos.engine.operators.BasicOperator;
import lupos.engine.operators.SimpleOperatorGraphVisitor;
import lupos.engine.operators.index.BasicIndexScan;
import lupos.engine.operators.index.Root;
import lupos.engine.operators.multiinput.Union;
import lupos.engine.operators.multiinput.join.Join;
import lupos.engine.operators.multiinput.optional.Optional;
import lupos.engine.operators.singleinput.Projection;
import lupos.engine.operators.singleinput.Result;
import lupos.engine.operators.singleinput.filter.Filter;
import lupos.engine.operators.singleinput.modifiers.Limit;
import lupos.engine.operators.singleinput.modifiers.Offset;
import lupos.engine.operators.singleinput.sort.Sort;
import lupos.misc.util.OperatorIDTuple;
import lupos.rdf.Prefix;
public class BasicOperatorByteArray {

	public enum OPERATORTYPE {
		JOIN, OPTIONAL, UNION, BASICINDEXSCAN, ROOT, SORT, RESULT, FILTER, PROJECTION, LIMIT, OFFSET, DEFAULT
	};

	/**
	 * the index of this operator node in the bytearray of the graph
	 */
	private final int index;
	/**
	 * The graph of operators. Each node has the following format:
	 *
	 * Byte 0: Type of operator according to enum OPERATORTYPE
	 *
	 * Bytes 1+2: Length ls of String
	 *
	 * Bytes 3 to 3+ls: String
	 *
	 * Bytes 4+ls and 5+ls: Length lp of prefixed String
	 *
	 * Bytes 6+ls to 6+ls+lp: prefixed String
	 *
	 * Bytes 7+ls+lp and 8+ls+lp: number npo of preceding operators
	 *
	 * Bytes 9+ls+lp to 9+ls+lp+ npo*2: preceding operators as index in
	 * indexOperators
	 *
	 * Bytes 10+ls+lp+ npo*2 and 11+ls+lp+npo*2: number nso of succeeding
	 * operators
	 *
	 * Bytes 12+ls+lp+ npo*2 to 12+ls+lp+ npo*2 +nso*3: succeeding operators
	 * with the information of the id (first byte) and index in indexOperators
	 * (second and third byte)
	 *
	 * Bytes 13+ls+lp+ npo*2 +nso*3 and 14+ls+lp+ npo*2 +nso*3: root operator of contained graph (e.g. in case of a SubgraphContainer) as index in indexOperators (both are 0 if there is no contained graph)
	 */
	private final byte[] graph;
	/**
	 * maps operator id to index in graph
	 */
	private final int[] indexOperators;

	/**
	 * <p>Constructor for BasicOperatorByteArray.</p>
	 *
	 * @param index a int.
	 * @param graph an array of byte.
	 * @param indexOperators an array of int.
	 */
	public BasicOperatorByteArray(final int index, final byte[] graph, final int[] indexOperators) {
		this.index = index;
		this.graph = graph;
		this.indexOperators = indexOperators;
	}

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link lupos.misc.debug.BasicOperatorByteArray.OPERATORTYPE} object.
	 */
	public OPERATORTYPE getType() {
		return OPERATORTYPE.values()[this.graph[this.index]];
	}

	/**
	 * <p>getTypeASByte.</p>
	 *
	 * @return a byte.
	 */
	public byte getTypeASByte() {
		return this.graph[this.index];
	}

	/**
	 * <p>getSucceedingOperators.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<OperatorIDTuple<BasicOperatorByteArray>> getSucceedingOperators() {
		final int indexSO = this.getIndexSucceedingOperators();
		final int number = this.get2Bytes(indexSO);
		final List<OperatorIDTuple<BasicOperatorByteArray>> result = new LinkedList<OperatorIDTuple<BasicOperatorByteArray>>();
		for (int i = 0; i < number; i++) {
			int currentID = this.graph[indexSO + 2 + i * 3];
			if (currentID < 0) {
				currentID = 256 - currentID;
			}
			final int currentOp = this.get2Bytes(indexSO + 3 + i * 3);
			result.add(new OperatorIDTuple<BasicOperatorByteArray>(
					new BasicOperatorByteArray(this.indexOperators[currentOp],
							this.graph, this.indexOperators), currentID));
		}
		return result;
	}

	private int getIndexSucceedingOperators() {
		final int indexPO = this.getIndexPreceedingOperators();
		return indexPO + 2 + this.get2Bytes(indexPO) * 2;
	}

	private int getIndexContainedGraph() {
		final int indexSO = this.getIndexSucceedingOperators();
		return indexSO + 2 + this.get2Bytes(indexSO) * 3;
	}


	private int getIndexPreceedingOperators() {
		final int indexPS = this.getIndexPrefixedString();
		return indexPS + 2 + this.get2Bytes(indexPS);
	}

	private int getIndexPrefixedString() {
		return this.index + 3 + this.get2Bytes(this.index + 1);
	}

	private int get2Bytes(final int index2) {
		final byte hb = this.graph[index2];
		final byte lb = this.graph[index2 + 1];
		if (hb < 0) {
			if (lb < 0) {
				return (256 + hb) * 256 + (256 + lb);
			} else {
				return (256 + hb) * 256 + lb;
			}
		} else {
			if (lb < 0) {
				return hb * 256 + (256 + lb);
			} else {
				return hb * 256 + lb;
			}
		}
	}

	/**
	 * <p>isMultiInputOperator.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isMultiInputOperator() {
		for (final BasicOperatorByteArray prec : this.getPrecedingOperators()) {
			for (final OperatorIDTuple<BasicOperatorByteArray> opIDt : prec.getSucceedingOperators()) {
				if (opIDt.getOperator().equals(this) && opIDt.getId() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>getPrecedingOperators.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<BasicOperatorByteArray> getPrecedingOperators() {
		final int indexPO = this.getIndexPreceedingOperators();
		final int number = this.get2Bytes(indexPO);
		final List<BasicOperatorByteArray> result = new LinkedList<BasicOperatorByteArray>();
		for (int i = 0; i < number; i++) {
			final int currentOp = this.get2Bytes(indexPO + 2 + i * 2);
			result.add(new BasicOperatorByteArray(
					this.indexOperators[currentOp], this.graph,
					this.indexOperators));
		}
		return result;
	}

	/**
	 * <p>getContainedGraph.</p>
	 *
	 * @return a {@link lupos.misc.debug.BasicOperatorByteArray} object.
	 */
	public BasicOperatorByteArray getContainedGraph(){
		final int indexCG = this.getIndexContainedGraph();
		final int reference = this.get2Bytes(indexCG);
		if(reference==0){
			return null;
		} else {
			return new BasicOperatorByteArray(this.indexOperators[reference], this.graph, this.indexOperators);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final int length = this.get2Bytes(this.index + 1);
		return new String(this.graph, this.index + 3, length);
	}

	/**
	 * <p>toString.</p>
	 *
	 * @param prefixInstance a {@link lupos.rdf.Prefix} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String toString(final Prefix prefixInstance) {
		if (prefixInstance.isActive()) {
			final int indexPS = this.getIndexPrefixedString();
			final int length = this.get2Bytes(indexPS);
			return new String(this.graph, indexPS + 2, length);
		} else {
			return this.toString();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return this.index;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object element) {
		if (element instanceof BasicOperatorByteArray) {
			final BasicOperatorByteArray theOther = (BasicOperatorByteArray) element;
			return this.index == theOther.index && this.graph == theOther.graph
					&& this.indexOperators == theOther.indexOperators;
		}
		return false;
	}

	@SuppressWarnings("serial")
	private static class Visitor implements SimpleOperatorGraphVisitor {

		final LinkedList<BasicOperator> indexOperatorsList;

		public Visitor(final LinkedList<BasicOperator> indexOperatorsList){
			this.indexOperatorsList = indexOperatorsList;
		}

		@Override
		public Object visit(final BasicOperator basicOperator) {
			this.indexOperatorsList.add(basicOperator);
			if(basicOperator.getContainedGraph()!=null){
				basicOperator.getContainedGraph().visit(this);
			}
			return null;
		}
	}


	/**
	 * <p>getBasicOperatorByteArray.</p>
	 *
	 * @param root a {@link lupos.engine.operators.BasicOperator} object.
	 * @param prefixInstance a {@link lupos.rdf.Prefix} object.
	 * @return a {@link lupos.misc.debug.BasicOperatorByteArray} object.
	 */
	public static BasicOperatorByteArray getBasicOperatorByteArray(
			final BasicOperator root, final Prefix prefixInstance) {
		final LinkedList<BasicOperator> indexOperatorsList = new LinkedList<BasicOperator>();
		root.visit(new Visitor(indexOperatorsList));
		final HashMap<BasicOperator, Integer> mapBO_to_indexOperators = new HashMap<BasicOperator, Integer>();
		int size = 0;
		int index = 0;
		for (final BasicOperator bo : indexOperatorsList) {
			size += 11 + bo.toString().getBytes().length
					+ bo.toString(prefixInstance).getBytes().length
					+ bo.getPrecedingOperators().size() * 2
					+ bo.getSucceedingOperators().size() * 3;
			mapBO_to_indexOperators.put(bo, index);
			index++;
		}
		final byte[] graph = new byte[size];
		final int[] indexOperators = new int[indexOperatorsList.size()];
		int indexOperatorsIndex = 0;
		int currentIndex = 0;
		for (final BasicOperator bo : indexOperatorsList) {
			indexOperators[indexOperatorsIndex] = currentIndex;
			graph[currentIndex] = getType(bo);
			currentIndex++;
			currentIndex = storeString(bo.toString().getBytes(), graph, currentIndex);
			currentIndex = storeString(bo.toString(prefixInstance).getBytes(), graph, currentIndex);
			currentIndex = storePrecedingOperators(bo, graph, currentIndex, mapBO_to_indexOperators);
			currentIndex = storeSucceedingOperators(bo, graph, currentIndex, mapBO_to_indexOperators);
			currentIndex = storeContainedOperator(bo, graph, currentIndex, mapBO_to_indexOperators);
			indexOperatorsIndex++;
		}
		return new BasicOperatorByteArray(0, graph, indexOperators);
	}

	private static int storeSucceedingOperators(final BasicOperator bo,
			final byte[] graph, int currentIndex,
			final HashMap<BasicOperator, Integer> mapBO_to_indexOperators) {
		currentIndex = storeInt(bo.getSucceedingOperators().size(), graph, currentIndex);
		for (final OperatorIDTuple<BasicOperator> succ : bo.getSucceedingOperators()) {
			graph[currentIndex] = (byte) succ.getId();
			currentIndex++;
			currentIndex = storeInt(mapBO_to_indexOperators.get(succ.getOperator()), graph, currentIndex);
		}
		return currentIndex;
	}

	private static int storePrecedingOperators(final BasicOperator bo,
			final byte[] graph, int currentIndex,
			final HashMap<BasicOperator, Integer> mapBO_to_indexOperators) {
		currentIndex = storeInt(bo.getPrecedingOperators().size(), graph, currentIndex);
		for (final BasicOperator prec_bo : bo.getPrecedingOperators()) {
			currentIndex = storeInt(mapBO_to_indexOperators.get(prec_bo), graph, currentIndex);
		}
		return currentIndex;
	}

	private static int storeContainedOperator(final BasicOperator bo,
			final byte[] graph, int currentIndex,
			final HashMap<BasicOperator, Integer> mapBO_to_indexOperators) {
		if(bo.getContainedGraph()!=null){
			currentIndex = storeInt(mapBO_to_indexOperators.get(bo.getContainedGraph()), graph, currentIndex);
		} else {
			currentIndex = storeInt(0, graph, currentIndex);
		}
		return currentIndex;
	}

	private static int storeString(final byte[] bytes, final byte[] graph,
			int currentIndex) {
		currentIndex = storeInt(bytes.length, graph, currentIndex);
		System.arraycopy(bytes, 0, graph, currentIndex, bytes.length);
		return currentIndex + bytes.length;
	}

	private static int storeInt(final int length, final byte[] bytes, final int currentIndex) {
		bytes[currentIndex] = (byte) (length / 256);
		bytes[currentIndex + 1] = (byte) (length % 256);
		return currentIndex + 2;
	}

	private static byte getType(final BasicOperator bo) {
		if (bo instanceof Join) {
			return (byte) OPERATORTYPE.JOIN.ordinal();
		} else if (bo instanceof Optional) {
			return (byte) OPERATORTYPE.OPTIONAL.ordinal();
		} else if (bo instanceof Union) {
			return (byte) OPERATORTYPE.UNION.ordinal();
		} else if (bo instanceof BasicIndexScan) {
			return (byte) OPERATORTYPE.BASICINDEXSCAN.ordinal();
		} else if (bo instanceof Root) {
			return (byte) OPERATORTYPE.ROOT.ordinal();
		} else if (bo instanceof Sort) {
			return (byte) OPERATORTYPE.SORT.ordinal();
		} else if (bo instanceof Result) {
			return (byte) OPERATORTYPE.RESULT.ordinal();
		} else if (bo instanceof Filter) {
			return (byte) OPERATORTYPE.FILTER.ordinal();
		} else if (bo instanceof Projection) {
			return (byte) OPERATORTYPE.PROJECTION.ordinal();
		} else if (bo instanceof Limit) {
			return (byte) OPERATORTYPE.LIMIT.ordinal();
		} else if (bo instanceof Offset) {
			return (byte) OPERATORTYPE.OFFSET.ordinal();
		} else {
			return (byte) OPERATORTYPE.DEFAULT.ordinal();
		}
	}
}
