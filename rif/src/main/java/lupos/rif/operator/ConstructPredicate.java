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
package lupos.rif.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lupos.datastructures.bindings.Bindings;
import lupos.datastructures.items.Item;
import lupos.datastructures.items.Variable;
import lupos.datastructures.items.literal.Literal;
import lupos.datastructures.items.literal.URILiteral;
import lupos.datastructures.queryresult.ParallelIterator;
import lupos.datastructures.queryresult.QueryResult;
import lupos.datastructures.queryresult.QueryResultDebug;
import lupos.engine.operators.Operator;
import lupos.engine.operators.OperatorIDTuple;
import lupos.misc.Tuple;
import lupos.misc.debug.DebugStep;
import lupos.misc.debug.DebugStepRIF;
import lupos.rdf.Prefix;
import lupos.rif.datatypes.Predicate;
import lupos.rif.datatypes.RuleResult;
public class ConstructPredicate extends Operator {
	final private List<Tuple<URILiteral, List<Item>>> patternList = new ArrayList<Tuple<URILiteral, List<Item>>>();

	/**
	 * <p>Constructor for ConstructPredicate.</p>
	 */
	public ConstructPredicate() {
		super();
	}

	/**
	 * <p>Constructor for ConstructPredicate.</p>
	 *
	 * @param name a {@link lupos.datastructures.items.literal.URILiteral} object.
	 * @param params a {@link lupos.datastructures.items.Item} object.
	 */
	public ConstructPredicate(final URILiteral name, final Item... params) {
		this();
		addPattern(name, params);
	}

	/**
	 * <p>setPredicatePattern.</p>
	 *
	 * @param patternList a {@link java.util.List} object.
	 */
	public void setPredicatePattern(
			final List<Tuple<URILiteral, List<Item>>> patternList) {
		this.patternList.clear();
		this.patternList.addAll(patternList);
	}

	/**
	 * <p>getPredicatePattern.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Tuple<URILiteral, List<Item>>> getPredicatePattern() {
		return this.patternList;
	}

	/**
	 * <p>addPattern.</p>
	 *
	 * @param name a {@link lupos.datastructures.items.literal.URILiteral} object.
	 * @param params a {@link lupos.datastructures.items.Item} object.
	 */
	public void addPattern(final URILiteral name, final Item... params) {
		final Tuple<URILiteral, List<Item>> item = new Tuple<URILiteral, List<Item>>(
				name, Arrays.asList(params));
		this.patternList.add(item);
	}

	/** {@inheritDoc} */
	@Override
	public QueryResult process(final QueryResult queryResult,
			final int operandID) {
		final RuleResult result = new RuleResult();
		final Iterator<Bindings> pib = queryResult.oneTimeIterator();
		// Create predicate
		while (pib.hasNext()) {
			final Bindings bind = pib.next();
			result.add(bind);
			for (final Tuple<URILiteral, List<Item>> item : this.patternList) {
				final Predicate pred = new Predicate();
				pred.setName(item.getFirst());
				for (int idx = 0; idx < item.getSecond().size(); idx++)
					if (item.getSecond().get(idx).isVariable())
						pred.getParameters().add(
								bind.get((Variable) item.getSecond().get(idx)));
					else
						pred.getParameters().add(
								(Literal) item.getSecond().get(idx));
				result.getPredicateResults().add(pred);
			}
		}
		if (pib instanceof ParallelIterator)
			((ParallelIterator<Bindings>) pib).close();
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void processAllDebug(final QueryResult queryResult,
			final int operandID, final DebugStep debugstep) {
		final QueryResult opp = process(queryResult, operandID);
		final RuleResult rr = (RuleResult) opp;
		if (opp == null)
			return;
		if (this.succeedingOperators.size() > 1) {
			opp.materialize();
		}
		for (final OperatorIDTuple opId : this.succeedingOperators) {
			((DebugStepRIF)debugstep).step(this, opId.getOperator(), rr);
			final QueryResultDebug qrDebug = new QueryResultDebug(opp,
					debugstep, this, opId.getOperator(), true);
			((Operator) opId.getOperator()).processAllDebug(qrDebug,
					opId.getId(), debugstep);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuffer str = new StringBuffer();
		str.append("ConstructPredicate: ").append("\n");
		for (final Tuple<URILiteral, List<Item>> item : this.patternList) {
			str.append(item.getFirst().toString()).append("(");
			for (int idx = 0; idx < item.getSecond().size(); idx++) {
				str.append(item.getSecond().get(idx).toString());
				if (idx < item.getSecond().size() - 1)
					str.append(", ");
				else
					str.append(")");
			}
			if (str.substring(str.length() - 1) != ")")
				str.append(")");
			str.append("\n");
		}
		return str.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String toString(final Prefix prefixInstance) {
		final StringBuffer str = new StringBuffer();
		str.append("ConstructPredicate: ").append("\n");
		for (final Tuple<URILiteral, List<Item>> item : this.patternList) {
			str.append(item.getFirst().toString(prefixInstance)).append("(");
			for (int idx = 0; idx < item.getSecond().size(); idx++) {
				if (item.getSecond().get(idx).isVariable())
					str.append(item.getSecond().get(idx).toString());
				else
					str.append(((Literal) item.getSecond().get(idx))
							.toString(prefixInstance));
				if (idx < item.getSecond().size() - 1)
					str.append(", ");
				else
					str.append(")");
			}
			if (str.substring(str.length() - 1) != ")")
				str.append(")");
			str.append("\n");
		}
		return str.toString();
	}
}
