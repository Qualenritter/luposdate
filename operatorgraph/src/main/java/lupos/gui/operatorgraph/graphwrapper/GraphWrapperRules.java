package lupos.gui.operatorgraph.graphwrapper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import lupos.gui.operatorgraph.AbstractSuperGuiComponent;
import lupos.gui.operatorgraph.DrawObject;
import lupos.gui.operatorgraph.GraphWrapperIDTuple;
import lupos.gui.operatorgraph.OperatorGraph;
import lupos.gui.operatorgraph.prefix.Prefix;
import lupos.gui.operatorgraph.viewer.ElementPanel;
import lupos.rif.IRuleNode;
import lupos.rif.model.Constant;
import lupos.rif.model.RuleVariable;
import xpref.datatypes.BooleanDatatype;

public class GraphWrapperRules extends GraphWrapper {
	public GraphWrapperRules(final IRuleNode element) {
		super(element);
	}

	@Override
	public AbstractSuperGuiComponent createObject(final OperatorGraph parent) {
		return new ElementPanel(parent, this);
	}

	@Override
	public void drawAnnotationsBackground(final Graphics2D g2d, final Dimension size) {}

	@Override
	@SuppressWarnings("unchecked")
	public void drawBackground(final Graphics2D g2d, final Dimension size) {
		try {
			if(!BooleanDatatype.getValues("ast_useStyledBoxes").get(0).booleanValue()) {
				DrawObject.drawSimpleBoxOuterLines(g2d, 0, 0, size.width - 1, size.height - 1, Color.WHITE, Color.BLACK);
			}
			else {
				DrawObject drawObject = null;

				if(instanceOf(this.element, Arrays.asList(Constant.class, RuleVariable.class))) {
					drawObject = this.getOperatorStyle("ast_style_terminalnode");
				}
				else {
					drawObject = this.getOperatorStyle("ast_style_nonterminalnode");
				}

				if(drawObject != null) {
					drawObject.draw(g2d, 0, 0, size.width, size.height);
				}
				else {
					DrawObject.drawGradientPaintRoundBox(g2d, 0, 0, size.width, size.height, Color.LIGHT_GRAY, Color.WHITE);
				}
			}
		}
		catch(final Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public Hashtable<GraphWrapper, AbstractSuperGuiComponent> drawLineAnnotations(final OperatorGraph parent) {
		return new Hashtable<GraphWrapper, AbstractSuperGuiComponent>();
	}

	@Override
	public LinkedList<GraphWrapper> getContainerElements() {
		return new LinkedList<GraphWrapper>();
	}

	@Override
	public LinkedList<GraphWrapper> getPrecedingElements() {
		final LinkedList<GraphWrapper> precedingElements = new LinkedList<GraphWrapper>();

		if(((IRuleNode) this.element).getParent() != null) {
			precedingElements.add(new GraphWrapperRules(((IRuleNode) this.element).getParent()));
		}

		return precedingElements;
	}

	@Override
	public LinkedList<GraphWrapperIDTuple> getSucceedingElements() {
		final List<IRuleNode> children = ((IRuleNode) element).getChildren();
		final LinkedList<GraphWrapperIDTuple> succedingElements = new LinkedList<GraphWrapperIDTuple>();

		if(children != null) {
			for(int i = 0; i < children.size(); ++i) {
				if(children.get(i) != null) {
					succedingElements.add(new GraphWrapperIDTuple(new GraphWrapperRules(children.get(i)), i));
				}
			}
		}

		return succedingElements;
	}

	@Override
	public boolean isContainer() {
		return false;
	}

	@Override
	public StringBuffer serializeObjectAndTree() {
		return new StringBuffer();
	}

	@Override
	public String toString(final Prefix prefixInstance) {
		return ((IRuleNode) this.element).getLabel();
	}

	@Override
	public IRuleNode getElement() {
		return (IRuleNode) this.element;
	}

	@Override
	public boolean usePrefixesActive() {
		return false;
	}

	@Override
	public String getWantedPreferencesID() {
		return "ast_useStyledBoxes";
	}
}