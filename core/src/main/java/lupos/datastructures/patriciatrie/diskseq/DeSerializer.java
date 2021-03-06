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
package lupos.datastructures.patriciatrie.diskseq;

import java.io.IOException;

import lupos.datastructures.patriciatrie.disk.nodemanager.NodeInputStream;
import lupos.datastructures.patriciatrie.diskseq.nodemanager.SeqNodeManager;
import lupos.datastructures.patriciatrie.node.Node;

/**
 * Interface for providing methods for (de-) serializing DBSeqNode and DBSeqNodeWithValue
 *
 * @author groppe
 * @version $Id: $Id
 */
public interface DeSerializer {
	
	/**
	 * <p>deserialize.</p>
	 *
	 * @param nodeManager a {@link lupos.datastructures.patriciatrie.diskseq.nodemanager.SeqNodeManager} object.
	 * @param inputStream a {@link lupos.datastructures.patriciatrie.disk.nodemanager.NodeInputStream} object.
	 * @return a {@link lupos.datastructures.patriciatrie.node.Node} object.
	 * @throws java.io.IOException if any.
	 */
	public Node deserialize(final SeqNodeManager nodeManager, final NodeInputStream inputStream) throws IOException;
	
	/**
	 * <p>serialize.</p>
	 *
	 * @param node a {@link lupos.datastructures.patriciatrie.node.Node} object.
	 * @param writer a {@link lupos.datastructures.patriciatrie.diskseq.DeSerializer.Writer} object.
	 * @throws java.io.IOException if any.
	 */
	public void serialize(final Node node, final Writer writer) throws IOException;
	
	public interface Writer{
	    public void write(int b) throws IOException;
	    public void writeInt(int v) throws IOException;
		public void write(byte[] output) throws IOException;
	}
	
}
