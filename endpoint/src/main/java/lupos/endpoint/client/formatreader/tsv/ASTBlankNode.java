/* Generated By:JJTree: Do not edit this line. ASTBlankNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.endpoint.client.formatreader.tsv;

public class ASTBlankNode extends SimpleNode {

	private String identifier;	

	public ASTBlankNode(int id) {
		super(id);
	}

	public ASTBlankNode(TSVParser p, int id) {
		super(p, id);
	}

	public void setIdentifier(String identifier){
		this.identifier=identifier;
	}

	public String getIdentifier(){
		return identifier;
	}

	public String toString(){
		return super.toString()+"  "+identifier;
	}

	/** Accept the visitor. **/
	public Object jjtAccept(TSVParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
/* JavaCC - OriginalChecksum=281423049831d56fb343450b46d20b45 (do not edit this line) */