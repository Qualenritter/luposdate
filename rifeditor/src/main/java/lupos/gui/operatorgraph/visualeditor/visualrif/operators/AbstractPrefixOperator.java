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
package lupos.gui.operatorgraph.visualeditor.visualrif.operators;

import java.util.HashSet;
import java.util.regex.Pattern;

import lupos.gui.operatorgraph.visualeditor.operators.Operator;
import lupos.gui.operatorgraph.visualeditor.util.ModificationException;
import lupos.misc.util.OperatorIDTuple;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractPrefixOperator extends Operator {

	public static final String internal_name = "internal_node_name";
	protected String name = "";
	private static int internal_global_id = 0;
	private int internal_id = -1;
	protected boolean alsoSubClasses = false;
	protected int opID = -1;
	protected String opIDLabel = "";
	private static final HashSet<String> reservedKeyWords = new HashSet<String>();

	//Constructor
	public AbstractPrefixOperator(){
		super();
	}

	//Constructor
	public AbstractPrefixOperator(final String name){
		this.setName(name);
	}

	//Constructor
	public AbstractPrefixOperator(final String name, final JSONObject loadObject) throws JSONException {
		this.name = name;
	}

	static {
		reservedKeyWords.add("prefix");
	}

	@Override
	public void prefixAdded() {}

	@Override
	public void prefixModified(final String arg0, final String arg1) {}

	@Override
	public void prefixRemoved(final String arg0, final String arg1) {}

	protected String determineNameForDrawing() {
		if(this.name.matches(AbstractPrefixOperator.internal_name + "\\d+")) {
			return "";
		}
		else {
			return this.name;
		}
	}

	@Override
	public boolean variableInUse(final String arg0, final HashSet<Operator> arg1) {
		return false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void applyChange(final String value) throws ModificationException {
		if(!value.equals("")) {
			if(AbstractPrefixOperator.reservedKeyWords.contains(value)) {
				throw new ModificationException("Operator name can not be a java keyword!", this);
			}
			final Pattern p = Pattern.compile("^[a-z]\\w*$", Pattern.CASE_INSENSITIVE);
			if(!p.matcher(value).matches()) {
				throw new ModificationException("Invalid operator name! Operator name must match /^[a-z]\\w*$/", this);
			}
		}
		this.name = value;
	}

	public static int getInternal_global_id() {
		return internal_global_id;
	}

	public static void setInternal_global_id(final int internal_global_id) {
		AbstractPrefixOperator.internal_global_id = internal_global_id;
	}

	public int getInternal_id() {
		return this.internal_id;
	}

	public void setInternal_id(final int internal_id) {
		this.internal_id = internal_id;
	}

	@Override
	public boolean validateOperator(final boolean showErrors, final HashSet<Operator> visited, final Object data) {
		if(visited.contains(this)) {
			return true;
		}
		visited.add(this);
		if(this.panel.validateOperatorPanel(showErrors, data) == false) {
			return false;
		}
		for(final OperatorIDTuple<Operator> opIDT : this.succeedingOperators) {
			if(opIDT.getOperator().validateOperator(showErrors, visited, data) == false) {
				return false;
			}
		}
		return true;
	}
}
