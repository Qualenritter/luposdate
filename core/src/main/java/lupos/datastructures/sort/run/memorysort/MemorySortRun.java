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
package lupos.datastructures.sort.run.memorysort;

import java.util.Iterator;

import lupos.datastructures.dbmergesortedds.tosort.ToSort;
import lupos.datastructures.dbmergesortedds.tosort.ToSort.TOSORT;
import lupos.datastructures.sort.run.Run;
public class MemorySortRun extends Run {
	
	protected final ToSort<String> tosort;
	protected final boolean set;
	
	/**
	 * <p>Constructor for MemorySortRun.</p>
	 *
	 * @param sortType a {@link lupos.datastructures.dbmergesortedds.tosort.ToSort.TOSORT} object.
	 * @param numberOfElements a int.
	 * @param set a boolean.
	 */
	public MemorySortRun(final TOSORT sortType, final int numberOfElements, final boolean set){
		this.tosort = ToSort.createInstanceWithGivenNumberOfElements(sortType, numberOfElements);
		this.set = set;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(String toBeAdded) {		
		this.tosort.add(toBeAdded);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public Run sort() {
		return new MemorySortSortedRun(this.tosort.emptyDatastructure(), this.tosort.size(), this.set);
	}

	/** {@inheritDoc} */
	@Override
	public Run swapRun() {
		// this is only allowed for already sorted runs (see MemorySortSortedRun)
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty() {
		return this.tosort.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<String> iterator() {
		// this is only allowed for already sorted runs (see MemorySortSortedRun)
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public int size() {		
		return this.tosort.size();
	}
}
