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
package lupos.gui.operatorgraph.visualeditor.visualrif.guielements;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lupos.gui.operatorgraph.visualeditor.visualrif.VisualRifEditor;
import lupos.gui.operatorgraph.visualeditor.visualrif.util.ScrollPane;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * Ein DocumentPanel ist die Visualisierung eines Dokumentes.
 * Jedes Dokument enthaelt eine DocumentEditorPane, welches den Dokumentengraphen enthaelt
 *
 * @author groppe
 * @version $Id: $Id
 */
public class DocumentPanel extends JTabbedPane {

	private static final long serialVersionUID = -525542825181366616L;


	private DocumentPanel that = this;
	private VisualRifEditor visualRifEditor;
	private String documentName;
	private LinkedList<String> listOfRules;
	private String tabTitle;

	//visual representation
	private DocumentEditorPane documentEditorPane = null;


	// Constructor
	/**
	 * <p>Constructor for DocumentPanel.</p>
	 *
	 * @param visualRifEditor a {@link lupos.gui.operatorgraph.visualeditor.visualrif.VisualRifEditor} object.
	 * @param name a {@link java.lang.String} object.
	 */
	public DocumentPanel(final VisualRifEditor visualRifEditor, final String name) {
		this(visualRifEditor, name, null);
	}

	// Constructor
	/**
	 * <p>Constructor for DocumentPanel.</p>
	 *
	 * @param visualRifEditor a {@link lupos.gui.operatorgraph.visualeditor.visualrif.VisualRifEditor} object.
	 * @param name a {@link java.lang.String} object.
	 * @param loadObject a {@link org.json.JSONObject} object.
	 */
	public DocumentPanel(final VisualRifEditor visualRifEditor, final String name,
			final JSONObject loadObject) {

		super();

		this.setDocumentName(name);

		this.listOfRules = new LinkedList<String>();
		this.setVisualRifEditor(visualRifEditor);
		this.setDocumentName(name);

		if (loadObject == null) {
			this.add("visual representation",
					this.getVisualRepresentationTab(null));
		} else {
			try {
				this.add("visual representation",
						this.getVisualRepresentationTab(loadObject.getJSONObject("VISUAL REPRESENTATION")));
			} catch (final JSONException e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}

		this.addChangeListener(new ChangeListener() {


			@Override
			public void stateChanged(final ChangeEvent ce) {
				final JTabbedPane tabSource = (JTabbedPane) ce.getSource();
				DocumentPanel.this.setTabTitle(tabSource.getTitleAt(tabSource
						.getSelectedIndex()));

			}
		});
	}

	//visual representation
	private JPanel getVisualRepresentationTab(final JSONObject loadObject) {

			this.documentEditorPane = new DocumentEditorPane(this.visualRifEditor);
			this.documentEditorPane.setDocumentName(this.documentName);


			final JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.add(new ScrollPane(this.documentEditorPane.getVisualGraphs().get(0)));

			final JPanel bottomPanel = new JPanel(new BorderLayout());
			bottomPanel.add(new ScrollPane(new JTextField()));

			final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setContinuousLayout(true);
			splitPane.setOneTouchExpandable(true);
			splitPane.setResizeWeight(0.8);
			splitPane.setTopComponent(topPanel);
			splitPane.setBottomComponent(this.documentEditorPane.buildBottomPane());

			final JPanel visualPanel = new JPanel(new BorderLayout());
			visualPanel.add(this.documentEditorPane.buildMenuBar(), BorderLayout.NORTH);

			JPanel topToolbar = null;

			if(loadObject != null) {
				try {
					final JSONObject toolBar = loadObject.getJSONObject("TOPTOOLBAR");
					topToolbar = this.documentEditorPane.createTopToolBar(toolBar);
				}
				catch(final JSONException e) {
					e.printStackTrace();
				}
			}else
			{
				topToolbar = this.documentEditorPane.createTopToolBar(null);
			}

			final JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.add(topToolbar, BorderLayout.NORTH);
			innerPanel.add(splitPane, BorderLayout.CENTER);

			visualPanel.add(innerPanel, BorderLayout.CENTER);

			this.documentEditorPane.fromJSON(loadObject);

			return visualPanel;
		}

	/**
	 * <p>cancelModi.</p>
	 */
	public void cancelModi() {
			if(this.documentEditorPane != null) {
				this.documentEditorPane.cancelModi();
			}
		}

	/** {@inheritDoc} */
	@Override
	public String toString(){
		return this.getDocumentName();
	}

	/**
	 * <p>toJSON.</p>
	 *
	 * @return a {@link org.json.JSONObject} object.
	 * @throws org.json.JSONException if any.
	 */
	public JSONObject toJSON() throws JSONException {
		final JSONObject saveObject = new JSONObject();

		saveObject.put("VISUAL REPRESENTATION", this.documentEditorPane.toJSON());


		return saveObject;
	}

	/* *************** **
	 * Getter + Setter **
	 * *************** */


	/**
	 * <p>Getter for the field <code>visualRifEditor</code>.</p>
	 *
	 * @return a {@link lupos.gui.operatorgraph.visualeditor.visualrif.VisualRifEditor} object.
	 */
	public VisualRifEditor getVisualRifEditor() {
		return this.visualRifEditor;
	}

	/**
	 * <p>Setter for the field <code>visualRifEditor</code>.</p>
	 *
	 * @param visualRifEditor a {@link lupos.gui.operatorgraph.visualeditor.visualrif.VisualRifEditor} object.
	 */
	public void setVisualRifEditor(final VisualRifEditor visualRifEditor) {
		this.visualRifEditor = visualRifEditor;
	}

	/**
	 * <p>Getter for the field <code>documentName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDocumentName() {
		return this.documentName;
	}

	/**
	 * <p>Setter for the field <code>documentName</code>.</p>
	 *
	 * @param documentName a {@link java.lang.String} object.
	 */
	public void setDocumentName(final String documentName) {
		this.documentName = documentName;
	}

	/**
	 * <p>Getter for the field <code>that</code>.</p>
	 *
	 * @return a {@link lupos.gui.operatorgraph.visualeditor.visualrif.guielements.DocumentPanel} object.
	 */
	public DocumentPanel getThat() {
		return this.that;
	}

	/**
	 * <p>Setter for the field <code>that</code>.</p>
	 *
	 * @param that a {@link lupos.gui.operatorgraph.visualeditor.visualrif.guielements.DocumentPanel} object.
	 */
	public void setThat(final DocumentPanel that) {
		this.that = that;
	}

	/**
	 * <p>Getter for the field <code>documentEditorPane</code>.</p>
	 *
	 * @return a {@link lupos.gui.operatorgraph.visualeditor.visualrif.guielements.DocumentEditorPane} object.
	 */
	public DocumentEditorPane getDocumentEditorPane() {
		return this.documentEditorPane;
	}

	/**
	 * <p>Setter for the field <code>documentEditorPane</code>.</p>
	 *
	 * @param documentEditorPane a {@link lupos.gui.operatorgraph.visualeditor.visualrif.guielements.DocumentEditorPane} object.
	 */
	public void setDocumentEditorPane(final DocumentEditorPane documentEditorPane) {
		this.documentEditorPane = documentEditorPane;
	}

	/**
	 * <p>Getter for the field <code>listOfRules</code>.</p>
	 *
	 * @return a {@link java.util.LinkedList} object.
	 */
	public LinkedList<String> getListOfRules() {
		return this.listOfRules;
	}

	/**
	 * <p>Setter for the field <code>listOfRules</code>.</p>
	 *
	 * @param listOfRules a {@link java.util.LinkedList} object.
	 */
	public void setListOfRules(final LinkedList<String> listOfRules) {
		this.listOfRules = listOfRules;
	}

	/**
	 * <p>updateRuleName.</p>
	 *
	 * @param oldName a {@link java.lang.String} object.
	 * @param newName a {@link java.lang.String} object.
	 */
	public void updateRuleName(final String oldName, final String newName) {
		for (int i = 0; i < this.listOfRules.size(); i++) {
			if(this.listOfRules.get(i).equals(oldName)){
				this.listOfRules.remove(i);
				this.listOfRules.add(newName);
			}
		}

	}

	/**
	 * <p>Getter for the field <code>tabTitle</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTabTitle() {
		return this.tabTitle;
	}

	/**
	 * <p>Setter for the field <code>tabTitle</code>.</p>
	 *
	 * @param tabTitle a {@link java.lang.String} object.
	 */
	public void setTabTitle(final String tabTitle) {
		this.tabTitle = tabTitle;
	}






}
