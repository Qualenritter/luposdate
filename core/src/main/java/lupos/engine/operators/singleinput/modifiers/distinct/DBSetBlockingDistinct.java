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
package lupos.engine.operators.singleinput.modifiers.distinct;

import java.util.Iterator;

import lupos.datastructures.bindings.Bindings;
import lupos.datastructures.dbmergesortedds.DBMergeSortedSet;
import lupos.datastructures.dbmergesortedds.SortConfiguration;
import lupos.datastructures.queryresult.ParallelIterator;
public class DBSetBlockingDistinct extends BlockingDistinct {

	/**
	 * <p>Constructor for DBSetBlockingDistinct.</p>
	 */
	public DBSetBlockingDistinct() {
		super(new DBMergeSortedSet<Bindings>(new SortConfiguration(), Bindings.class));
	}

	/** {@inheritDoc} */
	@Override
	protected ParallelIterator<Bindings> getIterator() {
		final Iterator<Bindings> itb = this.bindings.iterator();
		return new ParallelIterator<Bindings>() {

			@SuppressWarnings("rawtypes")
			@Override
			public void close() {
				((DBMergeSortedSet) DBSetBlockingDistinct.this.bindings).release();
				if (itb instanceof ParallelIterator)
					((ParallelIterator) itb).close();
			}

			@Override
			public boolean hasNext() {
				return itb.hasNext();
			}

			@Override
			public Bindings next() {
				return itb.next();
			}

			@Override
			public void remove() {
				itb.remove();
			}

			@Override
			public void finalize() {
				close();
			}
		};
	}
}
