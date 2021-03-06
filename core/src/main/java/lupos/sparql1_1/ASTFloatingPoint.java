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
/* Generated By:JJTree: Do not edit this line. ASTFloatingPoint.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.sparql1_1;

import lupos.datastructures.bindings.Bindings;
import lupos.datastructures.items.literal.LazyLiteral;
import lupos.datastructures.items.literal.Literal;
import lupos.engine.operators.singleinput.NotBoundException;
import lupos.engine.operators.singleinput.TypeErrorException;
import lupos.engine.operators.singleinput.filter.expressionevaluation.EvaluationVisitor;
public class ASTFloatingPoint extends SimpleNode {

  private String valueFloatingPoint;
  private Literal literal = null;

  /**
   * <p>Constructor for ASTFloatingPoint.</p>
   *
   * @param id a int.
   */
  public ASTFloatingPoint(final int id) {
    super(id);
  }

  /**
   * <p>Constructor for ASTFloatingPoint.</p>
   *
   * @param p a {@link lupos.sparql1_1.SPARQL1_1Parser} object.
   * @param id a int.
   */
  public ASTFloatingPoint(final SPARQL1_1Parser p, final int id) {
    super(p, id);
  }


  /**
   * <p>getValue.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getValue() {
	return this.valueFloatingPoint;
  }

  /**
   * <p>setValue.</p>
   *
   * @param value a {@link java.lang.String} object.
   */
  public void setValue(final String value) {
	this.valueFloatingPoint = value;
  }

  /**
   * <p>getValueWithoutLeadingPlus.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getValueWithoutLeadingPlus(){
	  String zValue = this.valueFloatingPoint;
	  while(zValue.startsWith("+")){
		  zValue = zValue.substring(1, zValue.length());
	  }
	  return zValue;
  }

  /**
   * <p>Getter for the field <code>literal</code>.</p>
   *
   * @param allowLazyLiteral a boolean.
   * @return a {@link lupos.datastructures.items.literal.Literal} object.
   */
  public Literal getLiteral(final boolean allowLazyLiteral){
	  if(this.literal==null){
		  this.literal = LazyLiteral.getLiteral(this, allowLazyLiteral);
	  }
	  return this.literal;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
	return super.toString() + "  " + this.valueFloatingPoint;
  }

	/** {@inheritDoc} */
	@Override
	public void init(final SimpleNode node){
		this.setValue(((ASTFloatingPoint)node).getValue());
	}


  /**
   * {@inheritDoc}
   *
   * Accept the visitor. *
   */
  @Override
    public String accept(final lupos.optimizations.sparql2core_sparql.SPARQL1_1ParserVisitorStringGenerator visitor) {
    return visitor.visit(this);
  }

    /** {@inheritDoc} */
    @Override
  public Object jjtAccept(final SPARQL1_1ParserVisitor visitor, final Object data) {
    return visitor.visit(this, data);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Object accept(final EvaluationVisitor visitor, final Bindings b, final Object data) throws NotBoundException, TypeErrorException {
	    return visitor.evaluate(this, b, data);
  }
}
/* JavaCC - OriginalChecksum=bf18c743e56aed471d8117e069281a37 (do not edit this line) */
