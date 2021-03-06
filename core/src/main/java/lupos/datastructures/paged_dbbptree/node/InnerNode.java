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
package lupos.datastructures.paged_dbbptree.node;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lupos.datastructures.buffermanager.PageManager;
import lupos.datastructures.buffermanager.PageOutputStream;
import lupos.datastructures.paged_dbbptree.node.nodedeserializer.NodeDeSerializer;
import lupos.io.helper.OutHelper;
import lupos.misc.Tuple;
public class InnerNode<K extends Serializable, V extends Serializable> extends Node<K, V> {

	public List<Integer> readReferences = new LinkedList<Integer>();
	protected final PageManager pageManager;
	protected final NodeDeSerializer<K, V> nodeDeSerializer;

	/**
	 * <p>Constructor for InnerNode.</p>
	 *
	 * @param keyClass a {@link java.lang.Class} object.
	 * @param valueClass a {@link java.lang.Class} object.
	 * @param k a int.
	 * @param pageManager a {@link lupos.datastructures.buffermanager.PageManager} object.
	 * @param nodeDeSerializer a {@link lupos.datastructures.paged_dbbptree.node.nodedeserializer.NodeDeSerializer} object.
	 */
	public InnerNode(final Class<? super K> keyClass,
			final Class<? super V> valueClass, final int k,
			final PageManager pageManager,
			final NodeDeSerializer<K, V> nodeDeSerializer) {
		super(keyClass, valueClass, k);
		this.readReferences = new ArrayList<Integer>(k + 1);
		this.pageManager = pageManager;
		this.nodeDeSerializer = nodeDeSerializer;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		String result = "Node stored in " + this.filename + "\n";
		final Iterator<K> it = this.readKeys.iterator();
		for (final Integer s : this.readReferences) {
			result += "-" + s + "- ";
			if (it.hasNext()) {
				result += "[" + it.next() + "]";
			}
		}
		return result;
	}

	/**
	 * <p>readNextEntry.</p>
	 */
	public void readNextEntry() {
		final Tuple<K, Integer> result = this.nodeDeSerializer
				.getNextInnerNodeEntry(this.readKeys.isEmpty() ? null : this.readKeys
						.get(this.readKeys.size() - 1), this.in);
		if (result == null) {
			try {
				this.in.close();
			} catch (final IOException e1) {
			}
			return;
		}

		final int nextFilename = result.getSecond();
		this.readReferences.add(nextFilename);

		final K nextKey = result.getFirst();

		if (nextKey == null) {
			try {
				this.in.close();
			} catch (final IOException e1) {
			}
			return;
		}

		this.readKeys.add(nextKey);
	}

	/**
	 * <p>readFullInnerNode.</p>
	 */
	public void readFullInnerNode() {
		final int pos=this.readKeys.size();
		K lastKey=(pos==0)?null:this.readKeys.get(pos-1);
		while (true) {
			final Tuple<K, Integer> nextEntry = this.nodeDeSerializer
					.getNextInnerNodeEntry(lastKey, this.in);
			if (nextEntry == null || nextEntry.getSecond() == null
					|| nextEntry.getSecond() < 0) {
				return;
			}
			this.readReferences.add(nextEntry.getSecond());
			if (nextEntry.getFirst() == null) {
				return;
			}
			lastKey = nextEntry.getFirst();
			this.readKeys.add(nextEntry.getFirst());
		}
	}

	/**
	 * <p>getReferences.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Integer> getReferences() {
		return this.readReferences;
	}

	/**
	 * <p>setReferences.</p>
	 *
	 * @param readReferences a {@link java.util.List} object.
	 */
	public void setReferences(final List<Integer> readReferences) {
		this.readReferences = readReferences;
	}

	/**
	 * <p>writeInnerNode.</p>
	 *
	 * @param overwrite a boolean.
	 */
	public void writeInnerNode(final boolean overwrite) {
		try {
			final OutputStream out = new PageOutputStream(this.filename, this.pageManager, !overwrite);
			OutHelper.writeLuposBoolean(false, out);

			K lastKey = null;
			final Iterator<Integer> it = this.readReferences.iterator();
			for (final K k : this.readKeys) {
				this.nodeDeSerializer
						.writeInnerNodeEntry(it.next(), k, out, lastKey);
				lastKey = k;
			}
			if (it.hasNext()) {
				this.nodeDeSerializer.writeInnerNodeEntry(it.next(), out);
			}
			out.close();
		} catch (final IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
