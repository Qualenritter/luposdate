/* Generated By:JJTree: Do not edit this line. ASTFloatingPoint.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.endpoint.client.formatreader.tsv;

public class ASTFloatingPoint extends SimpleNode {

	private String value;

	public ASTFloatingPoint(int id) {
		super(id);
	}

	public ASTFloatingPoint(TSVParser p, int id) {
		super(p, id);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		while(value.startsWith("+"))
			value=value.substring(1);
		this.value = value;
	}

	/** Accept the visitor. **/
	public Object jjtAccept(TSVParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
/* JavaCC - OriginalChecksum=249a1c41e42ac4c1e257b8174332ae5e (do not edit this line) */