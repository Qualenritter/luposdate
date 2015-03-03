
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
 *
 * @author groppe
 * @version $Id: $Id
 */
package lupos.gui.operatorgraph.visualeditor.visualrif.util;

import lupos.gui.operatorgraph.visualeditor.VisualEditor;
import lupos.gui.operatorgraph.visualeditor.operators.Operator;
import lupos.gui.operatorgraph.visualeditor.visualrif.guielements.graphs.VisualRIFGraph;
import lupos.gui.operatorgraph.visualeditor.visualrif.operators.AndContainer;
import lupos.gui.operatorgraph.visualeditor.visualrif.operators.FrameOperator;
public class ClassificationConnection  extends ConnectionRIF<Operator>{

	/**
	 * <p>Constructor for ClassificationConnection.</p>
	 *
	 * @param visualEditor a {@link lupos.gui.operatorgraph.visualeditor.VisualEditor} object.
	 */
	public ClassificationConnection(final VisualEditor<Operator> visualEditor) {
		super(visualEditor);
	}

	/**
	 * <p>Constructor for ClassificationConnection.</p>
	 *
	 * @param visualEditor a {@link lupos.gui.operatorgraph.visualeditor.VisualEditor} object.
	 * @param ruleGraph a {@link lupos.gui.operatorgraph.visualeditor.visualrif.guielements.graphs.VisualRIFGraph} object.
	 */
	public ClassificationConnection(final VisualEditor<Operator> visualEditor, final VisualRIFGraph<Operator> ruleGraph) {
		super(visualEditor);
		this.queryGraph = ruleGraph;
	}

	/** {@inheritDoc} */
	@Override
	protected String validateConnection() {
		String errorString = "";

		// no connection to itself
		if ( this.firstOp.getElement() == this.secondOp.getElement() ) {
			errorString = "You can't connect an operator with itself!";
		}

		if( this.firstOp.getElement() instanceof FrameOperator ){
			errorString = "Not Possible";
		}

		if ( (this.firstOp.getElement() instanceof AndContainer) ){
			errorString = "Not Possible"; // TODO
		} else {
			if ( this.secondOp.getElement() instanceof AndContainer ){
				errorString = "Not Possible"; // TODO
			}
		}

		return errorString;
	}
}
