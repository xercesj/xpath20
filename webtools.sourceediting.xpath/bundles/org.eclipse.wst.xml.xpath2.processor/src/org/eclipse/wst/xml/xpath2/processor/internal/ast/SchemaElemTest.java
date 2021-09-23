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

import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;

/**
 * Support for Schema Element Test.
 */
public class SchemaElemTest extends KindTest {
	private QName _arg;

	/**
	 * Constructor for SchemaElemTest.
	 * 
	 * @param arg
	 *            QName argument.
	 */
	public SchemaElemTest(QName arg) {
		_arg = arg;
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
	 * Support for QName interface.
	 * 
	 * @return Result of QName operation.
	 */
	public QName name() {
		return _arg;
	}

	public AnyType createTestType(ResultSequence rs) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWild() {
		return false;
	}

	public Class getXDMClassType() {
		// TODO Auto-generated method stub
		return null;
	}

}
