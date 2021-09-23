/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.ast;

import org.eclipse.wst.xml.xpath2.processor.internal.AncestorAxis;
import org.eclipse.wst.xml.xpath2.processor.internal.AncestorOrSelfAxis;
import org.eclipse.wst.xml.xpath2.processor.internal.ParentAxis;
import org.eclipse.wst.xml.xpath2.processor.internal.PrecedingAxis;
import org.eclipse.wst.xml.xpath2.processor.internal.PrecedingSiblingAxis;
import org.eclipse.wst.xml.xpath2.processor.internal.ReverseAxis;

/**
 * Class for Reverse stepping support for Step operations.
 */
public class ReverseStep extends Step {
	/**
	 * Set internal value for PARENT.
	 */
	public static final int PARENT = 0;
	/**
	 * Set internal value for ANCESTOR.
	 */
	public static final int ANCESTOR = 1;
	/**
	 * Set internal value for PRECEDING_SIBLING.
	 */
	public static final int PRECEDING_SIBLING = 2;
	/**
	 * Set internal value for PRECEDING.
	 */
	public static final int PRECEDING = 3;
	/**
	 * Set internal value for ANCESTOR_OR_SELF.
	 */
	public static final int ANCESTOR_OR_SELF = 4;
	/**
	 * Set internal value for DOTDOT.
	 */
	public static final int DOTDOT = 5;

	private int _axis;
	private ReverseAxis _iterator;

	private void update_iterator() {
		switch (_axis) {
		case PARENT:
			_iterator = new ParentAxis();
			break;
		case ANCESTOR:
			_iterator = new AncestorAxis();
			break;

		case PRECEDING_SIBLING:
			_iterator = new PrecedingSiblingAxis();
			break;

		case PRECEDING:
			_iterator = new PrecedingAxis();
			break;

		case ANCESTOR_OR_SELF:
			_iterator = new AncestorOrSelfAxis();
			break;

		case DOTDOT:
			_iterator = null;
			break;

		default:
			assert false;
		}
	}

	/**
	 * Constructor for ReverseStep.
	 * 
	 * @param axis
	 *            Axis number.
	 * @param node_test
	 *            Node test.
	 */
	public ReverseStep(int axis, NodeTest node_test) {
		super(node_test);

		_axis = axis;
		update_iterator();
	}

	/**
	 * Support for Visitor interface.
	 * 
	 * @return Result of Visitor operation.
	 */
	public Object accept(XPathVisitor v) {
		return v.visit(this);
	}

	/**
	 * Support for Axis interface.
	 * 
	 * @return Result of Axis operation.
	 */
	public int axis() {
		return _axis;
	}

	/**
	 * Support for Iterator interface.
	 * 
	 * @return Result of Iterator operation.
	 */
	public ReverseAxis iterator() {
		return _iterator;
	}
}
