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
/* Generated By:JJTree: Do not edit this line. ASTAdd.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.sparql1_1;
public
class ASTAdd extends SimpleNode {

	private boolean silent = false;

	/**
	 * <p>Setter for the field <code>silent</code>.</p>
	 */
	public void setSilent(){
		silent=true;
	}

	/**
	 * <p>isSilent.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSilent(){
		return silent;
	}

	/**
	 * <p>Constructor for ASTAdd.</p>
	 *
	 * @param id a int.
	 */
	public ASTAdd(int id) {
		super(id);
	}

	/**
	 * <p>Constructor for ASTAdd.</p>
	 *
	 * @param p a {@link lupos.sparql1_1.SPARQL1_1Parser} object.
	 * @param id a int.
	 */
	public ASTAdd(SPARQL1_1Parser p, int id) {
		super(p, id);
	}

	/** {@inheritDoc} */
	public void init(final SimpleNode node){
		this.silent=((ASTAdd)node).silent;
	}

	  /**
	   * {@inheritDoc}
	   *
	   * Accept the visitor. *
	   */
	  public String accept(lupos.optimizations.sparql2core_sparql.SPARQL1_1ParserVisitorStringGenerator visitor) {
    return visitor.visit(this);
  }

  /** {@inheritDoc} */
  public Object jjtAccept(SPARQL1_1ParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
/* JavaCC - OriginalChecksum=3a4bd8d352d4b5d9b82ef2acb82f5712 (do not edit this line) */
