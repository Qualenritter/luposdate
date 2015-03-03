
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
/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package lupos.sparql1_1;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import lupos.datastructures.bindings.Bindings;
import lupos.datastructures.items.Item;
import lupos.datastructures.items.Variable;
import lupos.engine.operators.BasicOperator;
import lupos.engine.operators.singleinput.NotBoundException;
import lupos.engine.operators.singleinput.TypeErrorException;
import lupos.engine.operators.singleinput.filter.expressionevaluation.EvaluationVisitor;
import lupos.optimizations.sparql2core_sparql.SPARQL1_1ParserPathVisitorStringGenerator;
import lupos.sparql1_1.operatorgraph.helper.OperatorConnection;
public
class SimpleNode implements Node {

  protected Node parent;
  protected Node[] children;
  protected int id;
  protected Object value;
  protected SPARQL1_1Parser parser;

  /**
   * <p>Constructor for SimpleNode.</p>
   *
   * @param i a int.
   */
  public SimpleNode(final int i) {
    this.id = i;
  }

  /**
   * <p>Constructor for SimpleNode.</p>
   *
   * @param p a {@link lupos.sparql1_1.SPARQL1_1Parser} object.
   * @param i a int.
   */
  public SimpleNode(final SPARQL1_1Parser p, final int i) {
    this(i);
    this.parser = p;
  }

  /** {@inheritDoc} */
  @Override
public void jjtOpen() {
  }

  /** {@inheritDoc} */
  @Override
public void jjtClose() {
  }

  /** {@inheritDoc} */
  @Override
public void jjtSetParent(final Node n) { this.parent = n; }
  /** {@inheritDoc} */
  @Override
public Node jjtGetParent() { return this.parent; }

  /** {@inheritDoc} */
  @Override
public void jjtAddChild(final Node n, final int i) {
    if (this.children == null) {
      this.children = new Node[i + 1];
    } else if (i >= this.children.length) {
      final Node c[] = new Node[i + 1];
      System.arraycopy(this.children, 0, c, 0, this.children.length);
      this.children = c;
    }
    this.children[i] = n;
  }

  /** {@inheritDoc} */
  @Override
public Node jjtGetChild(final int i) {
    return this.children[i];
  }

  /** {@inheritDoc} */
  @Override
public int jjtGetNumChildren() {
    return (this.children == null) ? 0 : this.children.length;
  }

  /**
   * <p>jjtSetValue.</p>
   *
   * @param value a {@link java.lang.Object} object.
   */
  public void jjtSetValue(final Object value) { this.value = value; }
  /**
   * <p>jjtGetValue.</p>
   *
   * @return a {@link java.lang.Object} object.
   */
  public Object jjtGetValue() { return this.value; }

  /**
   * {@inheritDoc}
   *
   * Accept the visitor. *
   */
  @Override
public String accept(final lupos.optimizations.sparql2core_sparql.SPARQL1_1ParserVisitorStringGenerator visitor){
	    return visitor.visit(this);
  }

  /** {@inheritDoc} */
  @Override
public void accept(final lupos.sparql1_1.operatorgraph.SPARQL1_1OperatorgraphGeneratorVisitor visitor, final OperatorConnection connection){
	    throw new UnsupportedOperationException("This node of type "+this.getClass()+" of the abstract syntax tree does not support an SPARQL1_1OperatorgraphGeneratorVisitor!");
  }

  /** {@inheritDoc} */
  @Override
public void accept(final lupos.sparql1_1.operatorgraph.SPARQL1_1OperatorgraphGeneratorVisitor visitor, final OperatorConnection connection, final Item graphConstraint){
	    throw new UnsupportedOperationException("This node of type "+this.getClass()+" of the abstract syntax tree does not support an SPARQL1_1OperatorgraphGeneratorVisitor with graphConstraint as additional parameter!");
  }

  /** {@inheritDoc} */
  @Override
public BasicOperator accept(final lupos.sparql1_1.operatorgraph.SPARQL1_1OperatorgraphGeneratorVisitor visitor, final OperatorConnection connection, final Item graphConstraint, final Variable subject, final Variable object, final Node subjectNode, final Node objectNode){
	    throw new UnsupportedOperationException("This node of type "+this.getClass()+" of the abstract syntax tree does not support an SPARQL1_1OperatorgraphGeneratorVisitor with parameters SPARQL1_1OperatorgraphGeneratorVisitor visitor, OperatorConnection connection, Item graphConstraint, Variable subject, Variable object, Node subjectNode, Node objectNode!");
}

  /** {@inheritDoc} */
  @Override
public String accept(final SPARQL1_1ParserPathVisitorStringGenerator visitor, final String subject, final String object){
	    throw new UnsupportedOperationException("This node of type "+this.getClass()+" of the abstract syntax tree does not support an SPARQL1_1ParserPathVisitorStringGenerator!");
  }

  /** {@inheritDoc} */
  @Override
public Object jjtAccept(final SPARQL1_1ParserVisitor visitor, final Object data){
	    return visitor.visit(this, data);
  }

  /** {@inheritDoc} */
  @Override
@SuppressWarnings({ "rawtypes" })
  public Object accept(final EvaluationVisitor visitor, final Bindings b, final Object data) throws NotBoundException, TypeErrorException {
	    throw new UnsupportedOperationException("This node of type "+this.getClass()+" of the abstract syntax tree does not support an EvaluationVisitor!");
  }

  /**
   * Accept the visitor. *
   *
   * @param visitor a {@link lupos.sparql1_1.SPARQL1_1ParserVisitor} object.
   * @param data a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object childrenAccept(final SPARQL1_1ParserVisitor visitor, final Object data)
{
    if (this.children != null) {
      for (int i = 0; i < this.children.length; ++i) {
        this.children[i].jjtAccept(visitor, data);
      }
    }
    return data;
  }

  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  /** {@inheritDoc} */
  @Override
public String toString() { return this.getClass().getSimpleName().substring(3); }
  /**
   * <p>toString.</p>
   *
   * @param prefix a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public String toString(final String prefix) { return prefix + this.toString(); }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  /**
   * <p>dump.</p>
   *
   * @param prefix a {@link java.lang.String} object.
   */
  public void dump(final String prefix) {
    System.out.println(this.toString(prefix));
    if (this.children != null) {
      for (int i = 0; i < this.children.length; ++i) {
        final SimpleNode n = (SimpleNode)this.children[i];
        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }

	/** {@inheritDoc} */
	@Override
	public void clearChildren() {
		this.children = null;
	}

	/** {@inheritDoc} */
	@Override
	public Node[] getChildren() {
		return this.children;
	}

	/** {@inheritDoc} */
	@Override
	public boolean replaceChild2(final Node replace, final Node by) {
		for (int i = 0; i < this.children.length; i++) {
			if (this.children[i].equals(replace)) {
				this.children[i] = by;
				return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void addChild(final Node n) {
		if (n != null) {
			final Node parent = n.jjtGetParent();
			if (parent != null && !parent.equals(this)) {
				parent.removeChild(n);
			}
			if (this.children == null) {
				this.jjtAddChild(n, 0);
			} else {
				this.jjtAddChild(n, this.children.length);
			}
			n.jjtSetParent(this);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Adds a node in children list at position i. If i>children.length the node
	 * will not be appended
	 */
	@Override
	public void addChild(final Node n, final int i) {
		final Node parent = n.jjtGetParent();
		if (parent != null && !parent.equals(this)) {
			parent.removeChild(n);
		}
		if (this.children == null) {
			this.jjtAddChild(n, 0);
		} else if (i <= this.children.length) {
			final Node c[] = new Node[this.children.length + 1];
			for (int j = 0; j < c.length; j++) {
				if (j < i) {
					c[j] = this.children[j];
				} else if (j == i) {
					c[j] = n;
				} else {
					c[j] = this.children[j - 1];
				}
			}
			this.children = c;
		} else {
			this.jjtAddChild(n, this.children.length);
		}
		n.jjtSetParent(this);
	}

	/**
	 * This method must be overridden by all abstract syntax tree nodes, which store additional information...
	 *
	 * @param node The node from which the initialization should be taken over!
	 */
	public void init(final SimpleNode node){
	}

	/** {@inheritDoc} */
	@Override
	public SimpleNode clone(final boolean clean) {
		SimpleNode ret = null;
		try {
			ret = this.getClass().getConstructor(Integer.TYPE).newInstance(
					new Random().nextInt(Integer.MAX_VALUE));
			if (!clean && this.children != null) {
				for (int i = 0; i < this.children.length; i++) {
					ret.addChild(this.children[i].clone(false), i);
				}
			}
			ret.init(this);
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public SimpleNode cloneStillChild(final boolean clean) {
		final SimpleNode ret = this.clone(clean);
		this.parent.addChild(ret, this.parent.getChildNumber(this) + 1);
		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public void removeChild(final int i) {
		if (this.children.length - 1 != 0) {
			final Node[] newChildren = new Node[this.children.length - 1];
			if (i > 0 && i < this.children.length - 1) {
				System.arraycopy(this.children, 0, newChildren, 0, i);
				System.arraycopy(this.children, i + 1, newChildren, i,
						this.children.length - i - 1);
			} else {
				if (i == 0) {
					if (this.children.length > 0) {
						System.arraycopy(this.children, 1, newChildren, 0,
								this.children.length - 1);
					} else {
						this.children = null;
					}
				} else {
					System.arraycopy(this.children, 0, newChildren, 0,
							this.children.length - 1);
				}
			}
			this.children[i].jjtSetParent(null);
			this.children = newChildren;
		} else {
			this.children[0].jjtSetParent(null);
			this.children = null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeChild(final Node n) {
		for (int i = 0; i < this.children.length; i++) {
			if (n.equals(this.children[i])) {
				this.removeChild(i);
				break;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getChildNumber(final Node node) {
		for (int i = 0; i < this.children.length; i++) {
			if (node.equals(this.children[i])) {
				return i;
			}
		}
		return -1;
	}
}

/* JavaCC - OriginalChecksum=5aa022caa8a5b902f357f7622f4ba0b8 (do not edit this line) */
