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
package lupos.datastructures.dbmergesortedds;

import java.io.Serializable;
import java.util.Map.Entry;

@SuppressWarnings("unchecked")
public class MapEntry<K, V> implements Entry<K, V>, Serializable,
		Comparable<Entry<K, V>> {
	private static final long serialVersionUID = -1799734392680458236L;

	/**
	 * <p>Constructor for MapEntry.</p>
	 *
	 * @param key a K object.
	 */
	public MapEntry(final K key) {
		this.k = key;
	}

	/**
	 * <p>Constructor for MapEntry.</p>
	 *
	 * @param key a K object.
	 * @param value a V object.
	 */
	public MapEntry(final K key, final V value) {
		this.k = key;
		this.v = value;
	}

	private final K k;
	private V v;

	/** {@inheritDoc} */
	@Override
	public K getKey() {
		return this.k;
	}

	/** {@inheritDoc} */
	@Override
	public V getValue() {
		return this.v;
	}

	/** {@inheritDoc} */
	@Override
	public V setValue(final V value) {
		final V res = this.v;
		this.v = value;
		return res;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object other) {
		if (other == null || this.getClass() != other.getClass()) {
			return false;
		}
		return this.k.equals(((MapEntry<K, V>) other).k);
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(final Entry<K, V> other) {
		return ((Comparable<K>) this.k).compareTo(other.getKey());
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.k + " => " + this.v;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return (int)(((long)this.k.hashCode() + this.v.hashCode())%Integer.MAX_VALUE);
	}
}
