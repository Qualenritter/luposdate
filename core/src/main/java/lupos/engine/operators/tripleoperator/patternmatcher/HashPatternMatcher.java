
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
 *
 * @author groppe
 * @version $Id: $Id
 */
package lupos.engine.operators.tripleoperator.patternmatcher;

import java.util.HashMap;
import java.util.Vector;

import lupos.datastructures.items.Triple;
import lupos.engine.operators.tripleoperator.TripleConsumer;
import lupos.engine.operators.tripleoperator.TriplePattern;
public class HashPatternMatcher extends PatternMatcher {
	protected HashMap<String, Vector<Integer>> keysOfTriplePatterns;

	/** {@inheritDoc} */
	@Override
	public void set(final TripleConsumer[] operators) {
		super.set(operators);
		keysOfTriplePatterns = new HashMap<String, Vector<Integer>>();
		for (int i = 0; i < operators.length; i++) {
			final String key = ((TriplePattern) operators[i]).getLiteralKey();

			// System.out.println("Key of TP:"+key);

			Vector<Integer> vtp = keysOfTriplePatterns.get(key);
			if (vtp == null) {
				vtp = new Vector<Integer>();
			}
			vtp.add(new Integer(i));
			keysOfTriplePatterns.put(key, vtp);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void consume(final Triple triple) {

		final Vector<Integer> MatchingTriplePatterns = new Vector<Integer>();
		// consider all 8 possibilities:
		final String[] combinations = {
				"||",
				triple.getSubject().toString() + "||",
				"|" + triple.getPredicate().toString() + "|",
				triple.getSubject().toString() + "|"
						+ triple.getPredicate().toString() + "|",
				"||" + triple.getObject().toString(),
				triple.getSubject().toString() + "||"
						+ triple.getObject().toString(),
				"|" + triple.getPredicate().toString() + "|"
						+ triple.getObject().toString(),
				triple.getSubject().toString() + "|"
						+ triple.getPredicate().toString() + "|"
						+ triple.getObject().toString() };
		Vector<Integer> inter;
		for (final String combination : combinations) {
			inter = keysOfTriplePatterns.get(combination);
			if (inter != null)
				MatchingTriplePatterns.addAll(inter);
		}
		final TriplePattern[] tp = getTriplePatterns();
		for (int i = 0; i < MatchingTriplePatterns.size(); i++) {
			final int num = MatchingTriplePatterns.get(i).intValue();
			tp[num].consume(triple);
		}
	}
}
