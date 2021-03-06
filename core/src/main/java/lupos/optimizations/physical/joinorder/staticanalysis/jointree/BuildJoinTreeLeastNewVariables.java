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
package lupos.optimizations.physical.joinorder.staticanalysis.jointree;

import lupos.engine.operators.index.BasicIndexScan;
import lupos.engine.operators.index.Root;
import lupos.optimizations.physical.joinorder.staticanalysis.scoring.subgraph.ScoringSubGraphsLeastNewVariables;

/**
 * Join ordering according to least new variables for the next best chosen combination of subgraphs...
 *
 * @author groppe
 * @version $Id: $Id
 */
public class BuildJoinTreeLeastNewVariables extends BuildJoinTreeByScoring {
	
	/**
	 * <p>Constructor for BuildJoinTreeLeastNewVariables.</p>
	 */
	public BuildJoinTreeLeastNewVariables(){
		this.scorings.add(new ScoringSubGraphsLeastNewVariables());
	}

	/**
	 * Static method to call the optimizer for join ordering according to least new variables for the next best chosen combination of subgraphs
	 *
	 * @param indexScan the IndexScan operator with at least two triple patterns to join....
	 * @return the root operator under which the subgraph with the reordered joins are inserted
	 */
	public static Root rearrangeJoinOrder(final BasicIndexScan indexScan){
		Root root = indexScan.getRoot().newInstance(indexScan.getRoot().dataset);
		BuildJoinTreeLeastNewVariables optimizer = new BuildJoinTreeLeastNewVariables();
		optimizer.rearrangeJoinOrder(root, indexScan);
		return root;
	}
}
