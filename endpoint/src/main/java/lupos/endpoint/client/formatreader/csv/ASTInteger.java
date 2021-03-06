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
/* Generated By:JJTree: Do not edit this line. ASTInteger.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.endpoint.client.formatreader.csv;

import java.math.BigInteger;
public class ASTInteger extends SimpleNode {

	private BigInteger value;

	/**
	 * <p>Constructor for ASTInteger.</p>
	 *
	 * @param id a int.
	 */
	public ASTInteger(int id) {
		super(id);
	}

	/**
	 * <p>Constructor for ASTInteger.</p>
	 *
	 * @param p a {@link lupos.endpoint.client.formatreader.csv.CSVParser} object.
	 * @param id a int.
	 */
	public ASTInteger(CSVParser p, int id) {
		super(p, id);
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.math.BigInteger} object.
	 */
	public BigInteger getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.math.BigInteger} object.
	 */
	public void setValue(final BigInteger value) {
		this.value = value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a int.
	 */
	public void setValue(final int value) {
		this.value = new BigInteger("" + value);
	}

	/**
	 * <p>setStringInt.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setStringInt(String value) {
		while(value.startsWith("+"))
			value=value.substring(1);
		this.value = new BigInteger(value);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return super.toString() + "  " + value;
	}
}
/* JavaCC - OriginalChecksum=567b8cd7fff0dacfc31a23f6b04a99f3 (do not edit this line) */
