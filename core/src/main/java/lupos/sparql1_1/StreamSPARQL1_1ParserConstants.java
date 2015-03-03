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
/* Generated By:JJTree&JavaCC: Do not edit this line. StreamSPARQL1_1ParserConstants.java */
package lupos.sparql1_1;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 *
 * @author groppe
 * @version $Id: $Id
 */
public interface StreamSPARQL1_1ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int IRI_REF = 10;
  /** RegularExpression Id. */
  int VARNAME = 11;
  /** RegularExpression Id. */
  int PNAME_NS = 12;
  /** RegularExpression Id. */
  int PNAME_LN = 13;
  /** RegularExpression Id. */
  int BLANK_NODE_LABEL = 14;
  /** RegularExpression Id. */
  int VAR = 15;
  /** RegularExpression Id. */
  int VAR1 = 16;
  /** RegularExpression Id. */
  int VAR2 = 17;
  /** RegularExpression Id. */
  int LANGTAG = 18;
  /** RegularExpression Id. */
  int INTEGER = 19;
  /** RegularExpression Id. */
  int DECIMAL = 20;
  /** RegularExpression Id. */
  int DOUBLE = 21;
  /** RegularExpression Id. */
  int EXPONENT = 22;
  /** RegularExpression Id. */
  int STRING_LITERAL1 = 23;
  /** RegularExpression Id. */
  int STRING_LITERAL2 = 24;
  /** RegularExpression Id. */
  int STRING_LITERAL_LONG1 = 25;
  /** RegularExpression Id. */
  int STRING_LITERAL_LONG2 = 26;
  /** RegularExpression Id. */
  int NIL = 27;
  /** RegularExpression Id. */
  int WS = 28;
  /** RegularExpression Id. */
  int ANON = 29;
  /** RegularExpression Id. */
  int PN_CHARS_BASE = 30;
  /** RegularExpression Id. */
  int PN_CHARS_U = 31;
  /** RegularExpression Id. */
  int PN_CHARS = 32;
  /** RegularExpression Id. */
  int PN_PREFIX = 33;
  /** RegularExpression Id. */
  int PN_LOCAL = 34;
  /** RegularExpression Id. */
  int PLX = 35;
  /** RegularExpression Id. */
  int PERCENT = 36;
  /** RegularExpression Id. */
  int HEX = 37;
  /** RegularExpression Id. */
  int PN_LOCAL_ESC = 38;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\"{\"",
    "\".\"",
    "\"}\"",
    "\"a\"",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<token of kind 9>",
    "<IRI_REF>",
    "<VARNAME>",
    "<PNAME_NS>",
    "<PNAME_LN>",
    "<BLANK_NODE_LABEL>",
    "<VAR>",
    "<VAR1>",
    "<VAR2>",
    "<LANGTAG>",
    "<INTEGER>",
    "<DECIMAL>",
    "<DOUBLE>",
    "<EXPONENT>",
    "<STRING_LITERAL1>",
    "<STRING_LITERAL2>",
    "<STRING_LITERAL_LONG1>",
    "<STRING_LITERAL_LONG2>",
    "<NIL>",
    "<WS>",
    "<ANON>",
    "<PN_CHARS_BASE>",
    "<PN_CHARS_U>",
    "<PN_CHARS>",
    "<PN_PREFIX>",
    "<PN_LOCAL>",
    "<PLX>",
    "<PERCENT>",
    "<HEX>",
    "<PN_LOCAL_ESC>",
    "\"BASE\"",
    "\"PREFIX\"",
    "\"SELECT\"",
    "\"DISTINCT\"",
    "\"REDUCED\"",
    "\"*\"",
    "\"(\"",
    "\"AS\"",
    "\")\"",
    "\"CONSTRUCT\"",
    "\"WHERE\"",
    "\"DESCRIBE\"",
    "\"ASK\"",
    "\"STREAM\"",
    "\"INTERMEDIATERESULT\"",
    "\"DURATION\"",
    "\"TRIPLES\"",
    "\"WINDOW\"",
    "\"START\"",
    "\"END\"",
    "\"TYPE\"",
    "\"INSTANCE\"",
    "\"SLIDINGDURATION\"",
    "\"SLIDINGINSTANCES\"",
    "\"SLIDINGTRIPLES\"",
    "\"FROM\"",
    "\"NAMED\"",
    "\"GROUP\"",
    "\"BY\"",
    "\"HAVING\"",
    "\"ORDER\"",
    "\"ASC\"",
    "\"DESC\"",
    "\"LIMIT\"",
    "\"OFFSET\"",
    "\"VALUES\"",
    "\"UNDEF\"",
    "\";\"",
    "\"INSERT\"",
    "\"DATA\"",
    "\"DELETE\"",
    "\"LOAD\"",
    "\"SILENT\"",
    "\"INTO\"",
    "\"CLEAR\"",
    "\"DROP\"",
    "\"CREATE\"",
    "\"ADD\"",
    "\"TO\"",
    "\"MOVE\"",
    "\"COPY\"",
    "\"WITH\"",
    "\"USING\"",
    "\"DEFAULT\"",
    "\"GRAPH\"",
    "\"ALL\"",
    "\"OPTIONAL\"",
    "\"SERVICE\"",
    "\"BIND\"",
    "\"MINUS\"",
    "\"UNION\"",
    "\"FILTER\"",
    "\",\"",
    "\"[\"",
    "\"|\"",
    "\"/\"",
    "\"^\"",
    "\"?\"",
    "\"+\"",
    "\"!\"",
    "\"]\"",
    "\"||\"",
    "\"&&\"",
    "\"=\"",
    "\"!=\"",
    "\"<\"",
    "\">\"",
    "\"<=\"",
    "\">=\"",
    "\"IN\"",
    "\"NOT\"",
    "\"-\"",
    "\"STR\"",
    "\"LANG\"",
    "\"LANGMATCHES\"",
    "\"DATATYPE\"",
    "\"BOUND\"",
    "\"IRI\"",
    "\"URI\"",
    "\"BNODE\"",
    "\"RAND\"",
    "\"ABS\"",
    "\"CEIL\"",
    "\"FLOOR\"",
    "\"ROUND\"",
    "\"CONCAT\"",
    "\"STRLEN\"",
    "\"UCASE\"",
    "\"LCASE\"",
    "\"ENCODE_FOR_URI\"",
    "\"CONTAINS\"",
    "\"STRSTARTS\"",
    "\"STRENDS\"",
    "\"STRBEFORE\"",
    "\"STRAFTER\"",
    "\"YEAR\"",
    "\"MONTH\"",
    "\"DAY\"",
    "\"HOURS\"",
    "\"MINUTES\"",
    "\"SECONDS\"",
    "\"TIMEZONE\"",
    "\"TZ\"",
    "\"NOW\"",
    "\"UUID\"",
    "\"STRUUID\"",
    "\"MD5\"",
    "\"SHA1\"",
    "\"SHA256\"",
    "\"SHA384\"",
    "\"SHA512\"",
    "\"COALESCE\"",
    "\"IF\"",
    "\"STRLANG\"",
    "\"STRDT\"",
    "\"sameTerm\"",
    "\"isIRI\"",
    "\"isURI\"",
    "\"isBLANK\"",
    "\"isLITERAL\"",
    "\"isNUMERIC\"",
    "\"REGEX\"",
    "\"SUBSTR\"",
    "\"REPLACE\"",
    "\"EXISTS\"",
    "\"COUNT\"",
    "\"SUM\"",
    "\"MIN\"",
    "\"MAX\"",
    "\"AVG\"",
    "\"SAMPLE\"",
    "\"GROUP_CONCAT\"",
    "\"SEPARATOR\"",
    "\"^^\"",
    "\"true\"",
    "\"false\"",
  };

}
