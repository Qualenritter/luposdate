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
package lupos.optimizations.logical.rules.rdfs;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import lupos.datastructures.items.Variable;
import lupos.engine.operators.BasicOperator;
import lupos.engine.operators.OperatorIDTuple;
import lupos.engine.operators.messages.BoundVariablesMessage;
import lupos.engine.operators.singleinput.Projection;
import lupos.misc.Tuple;
import lupos.optimizations.logical.rules.Rule;
public class RuleMergeTwoProjection extends Rule {

	/** {@inheritDoc} */
	@Override
	protected void init() {
		final Projection proj1 = new Projection();
		final Projection proj2 = new Projection();

		proj1.setSucceedingOperator(new OperatorIDTuple(proj2, -1));
		proj2.setPrecedingOperator(proj1);

		subGraphMap = new HashMap<BasicOperator, String>();
		subGraphMap.put(proj1, "proj1");
		subGraphMap.put(proj2, "proj2");

		startNode = proj1;
	}

	/** {@inheritDoc} */
	@Override
	protected boolean checkPrecondition(final Map<String, BasicOperator> mso) {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public Tuple<Collection<BasicOperator>, Collection<BasicOperator>> transformOperatorGraph(
			final Map<String, BasicOperator> mso,
			final BasicOperator rootOperator) {
		final Collection<BasicOperator> deleted = new LinkedList<BasicOperator>();
		final Collection<BasicOperator> added = new LinkedList<BasicOperator>();
		final Projection proj1 = (Projection) mso.get("proj1");
		final Projection proj2 = (Projection) mso.get("proj2");

		final Object[] proj1Vars = proj1.getProjectedVariables().toArray();
		final Object[] proj2Vars = proj2.getProjectedVariables().toArray();

		final Projection proj_new = new Projection();
		for (int i = 0; i < proj2Vars.length; i++) {
			// Only intersection variables go into the new projection
			if (arrayContains(proj1Vars, (Variable) proj2Vars[i])) {
				proj_new.addProjectionElement((Variable) proj2Vars[i]);
			}
		}

		final LinkedList<BasicOperator> pres = (LinkedList<BasicOperator>) proj1
				.getPrecedingOperators();
		BasicOperator pre;
		for (int i = 0; i < pres.size(); i++) {
			pre = pres.get(i);
			pre.addSucceedingOperator(new OperatorIDTuple(proj_new, pre
					.getOperatorIDTuple(proj1).getId()));
			pre.removeSucceedingOperator(proj1);
		}

		proj_new.setPrecedingOperators(proj1.getPrecedingOperators());
		proj_new.setSucceedingOperators(proj2.getSucceedingOperators());

		final LinkedList<OperatorIDTuple> succs = (LinkedList<OperatorIDTuple>) proj2
				.getSucceedingOperators();
		for (int i = 0; i < succs.size(); i++) {
			succs.get(i).getOperator().addPrecedingOperator(proj_new);
		}

		proj2.removePrecedingOperator(proj1);

		rootOperator.deleteParents();
		rootOperator.setParents();
		rootOperator.detectCycles();
		rootOperator.sendMessage(new BoundVariablesMessage());
		added.add(proj_new);
		deleted.add(proj1);
		deleted.add(proj2);
		if (!deleted.isEmpty() || !added.isEmpty())
			return new Tuple<Collection<BasicOperator>, Collection<BasicOperator>>(
					added, deleted);
		else
			return null;
	}

	private boolean arrayContains(final Object[] vars, final Variable var) {
		for (int i = 0; i < vars.length; i++) {
			if (vars[i].equals(var))
				return true;
		}
		return false;
	}

}
