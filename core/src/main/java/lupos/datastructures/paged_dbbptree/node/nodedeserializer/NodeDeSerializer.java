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
package lupos.datastructures.paged_dbbptree.node.nodedeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import lupos.datastructures.paged_dbbptree.node.DBBPTreeEntry;
import lupos.misc.Tuple;
public interface NodeDeSerializer<K, V> extends Serializable {

	/**
	 * <p>getNextInnerNodeEntry.</p>
	 *
	 * @param lastKey2 a K object.
	 * @param in2 a {@link java.io.InputStream} object.
	 * @return a {@link lupos.misc.Tuple} object.
	 */
	public Tuple<K, Integer> getNextInnerNodeEntry(final K lastKey2,
			final InputStream in2);

	/**
	 * <p>getNextLeafEntry.</p>
	 *
	 * @param in a {@link java.io.InputStream} object.
	 * @param lastKey a K object.
	 * @param lastValue a V object.
	 * @return a {@link lupos.datastructures.paged_dbbptree.node.DBBPTreeEntry} object.
	 */
	public DBBPTreeEntry<K, V> getNextLeafEntry(
			final InputStream in, final K lastKey,
			final V lastValue);

	/**
	 * <p>writeInnerNodeEntry.</p>
	 *
	 * @param fileName a int.
	 * @param key a K object.
	 * @param out a {@link java.io.OutputStream} object.
	 * @param lastKey a K object.
	 * @throws java.io.IOException if any.
	 */
	public void writeInnerNodeEntry(final int fileName, final K key,
			final OutputStream out, final K lastKey)
			throws IOException;

	/**
	 * <p>writeInnerNodeEntry.</p>
	 *
	 * @param fileName a int.
	 * @param out a {@link java.io.OutputStream} object.
	 * @throws java.io.IOException if any.
	 */
	public void writeInnerNodeEntry(final int fileName,
			final OutputStream out) throws IOException;

	/**
	 * <p>writeLeafEntry.</p>
	 *
	 * @param k a K object.
	 * @param v a V object.
	 * @param out a {@link java.io.OutputStream} object.
	 * @param lastKey a K object.
	 * @param lastValue a V object.
	 * @throws java.io.IOException if any.
	 */
	public void writeLeafEntry(final K k, final V v,
			final OutputStream out, final K lastKey,
			final V lastValue) throws IOException;

	/**
	 * <p>writeLeafEntryNextFileName.</p>
	 *
	 * @param filename a int.
	 * @param out a {@link java.io.OutputStream} object.
	 * @throws java.io.IOException if any.
	 */
	public void writeLeafEntryNextFileName(final int filename,
			final OutputStream out) throws IOException;

}
