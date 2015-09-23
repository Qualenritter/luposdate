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
/* Generated By:JavaCC: Do not edit this line. TSVParserVisitor.java Version 5.0 */
package lupos.endpoint.client.formatreader.tsv;
public interface TSVParserVisitor
{
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.SimpleNode} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(SimpleNode node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTResult} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTResult node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTVars} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTVars node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTOneResult} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTOneResult node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTVar} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTVar node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTValue} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTValue node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTNIL} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTNIL node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTRDFLiteral} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTRDFLiteral node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTDoubleCircumflex} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTDoubleCircumflex node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTLangTag} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTLangTag node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTInteger} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTInteger node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTFloatingPoint} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTFloatingPoint node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTBooleanLiteral} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTBooleanLiteral node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTStringLiteral} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTStringLiteral node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTQuotedURIRef} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTQuotedURIRef node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTBlankNode} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTBlankNode node, Object data);
  /**
   * <p>visit.</p>
   *
   * @param node a {@link lupos.endpoint.client.formatreader.tsv.ASTEmptyNode} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object visit(ASTEmptyNode node, Object data);
}
/* JavaCC - OriginalChecksum=bcb0eb7250c1fe64087cbac7740c6dff (do not edit this line) */
