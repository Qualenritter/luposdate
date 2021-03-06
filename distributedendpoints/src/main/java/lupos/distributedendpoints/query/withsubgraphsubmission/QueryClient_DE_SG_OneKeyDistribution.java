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
package lupos.distributedendpoints.query.withsubgraphsubmission;

import lupos.distributed.storage.distributionstrategy.tripleproperties.TriplePropertiesDistributionStrategyEnum;

/**
 * This class is the query evaluator for querying distributed SPARQL endpoints based on the one key distribution strategy.
 * Complete subgraphs are submitted for evaluation to the storage nodes.
 *
 * It uses the super and helper classes of the distributed module for a first and simple example of a distributed scenario.
 *
 * @author groppe
 * @version $Id: $Id
 */
public class QueryClient_DE_SG_OneKeyDistribution extends QueryClient_DE_SG_DistributionStrategy<String> {

	/**
	 * <p>Constructor for QueryClient_DE_SG_OneKeyDistribution.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	public QueryClient_DE_SG_OneKeyDistribution() throws Exception {
		super(TriplePropertiesDistributionStrategyEnum.OneKeyDistribution.createInstance());
	}

	/**
	 * <p>Constructor for QueryClient_DE_SG_OneKeyDistribution.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 * @throws java.lang.Exception if any.
	 */
	public QueryClient_DE_SG_OneKeyDistribution(final String[] args) throws Exception {
		super(TriplePropertiesDistributionStrategyEnum.OneKeyDistribution.createInstance(), args);
	}

}
