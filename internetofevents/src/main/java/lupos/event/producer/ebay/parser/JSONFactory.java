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
package lupos.event.producer.ebay.parser;

/**
 * Common interface for a factory for JSON data structures.
 * Here, a factory is an object providing incremental construction of another object.
 *
 * @version $Id: $Id
 */
public abstract class JSONFactory {

	/**
	 * States, whether the construction has been completed.
	 */
	protected boolean finished = false;
	
	/**
	 * Current factory for a JSON data structure contained in the constructed.
	 */
	protected JSONFactory factory = null;
	
	/**
	 * Returns a new factory for a certain JSON data structure.
	 * The JSON data structure is defined by the first character of its string
	 * representation. If <code>c</code> doesn't match such a character,
	 * <strong>null</strong> is returned.
	 *
	 * @return a {@link lupos.event.producer.ebay.parser.JSONFactory} object.
	 */
	public static JSONFactory openWith(char c) {
		switch (c) {
		case '[' :
			return new JSArrayFactory();
		case '{' :
			return new JSMapFactory();
		case '"' :
			return new JSValueFactory();
		default :
			return null;
		}
	}
	
	/**
	 * Continues the construction of the JSON data structure by adding the next character
	 * <code>c</code> of its string representation.
	 * If <code>c</code> signals the end of the JSON data structure, the construction is
	 * terminated and no further appending operation should be possible.
	 *
	 * @return a boolean.
	 */
	public abstract boolean append(char c);
	
	/**
	 * Returns the constructed JSON data structure
	 *
	 * @return a {@link lupos.event.producer.ebay.parser.JSObject} object.
	 */
	public abstract JSObject create();
}
