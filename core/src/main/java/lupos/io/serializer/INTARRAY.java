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
package lupos.io.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import lupos.io.Registration.DeSerializerConsideringSubClasses;
import lupos.io.helper.InputHelper;
import lupos.io.helper.LengthHelper;
import lupos.io.helper.OutHelper;
public class INTARRAY extends DeSerializerConsideringSubClasses<int[]> {
	/** {@inheritDoc} */
	@Override
	public boolean instanceofTest(final Object o) {
		return o instanceof int[];
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public Class<int[]>[] getRegisteredClasses() {
		return new Class[] { int[].class };
	}

	/** {@inheritDoc} */
	@Override
	public int length(final int[] t) {
		return (t.length+1)*LengthHelper.lengthLuposInt();
	}

	/** {@inheritDoc} */
	@Override
	public void serialize(final int[] t, final OutputStream out) throws IOException {
		OutHelper.writeLuposInt(t.length, out);
		for(final int c: t){
			OutHelper.writeLuposInt(c, out);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int[] deserialize(final InputStream in) throws IOException, URISyntaxException, ClassNotFoundException {
		final int[] result = new int[InputHelper.readLuposInteger(in)];
		for(int i=0; i<result.length; i++){
			result[i] = InputHelper.readLuposInteger(in);
		}
		return result;
	}

}
