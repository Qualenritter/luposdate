/* Generated By:JJTree: Do not edit this line. ASTVars.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.endpoint.client.formatreader.tsv;

public
class ASTVars extends SimpleNode {
  public ASTVars(int id) {
    super(id);
  }

  public ASTVars(TSVParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(TSVParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=291f515aa06d056b739641b4bb9d44b0 (do not edit this line) */