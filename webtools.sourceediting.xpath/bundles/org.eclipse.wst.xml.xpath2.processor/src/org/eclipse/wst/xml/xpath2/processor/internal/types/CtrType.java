/*******************************************************************************
 * Copyright (c) 2005, 2009 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.types;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;

/**
 * A representation of the CtrType datatype
 */
public abstract class CtrType extends AnyAtomicType {
	// used for constructor functions
	// arg is either empty sequence, or 1 anyatomictype
	/**
	 * Used for constructor function.
	 * 
	 * @param arg
	 *            Either an empty sequence or 1 atomic type
	 * @return The resulting ResultSequence
	 */
	public abstract ResultSequence constructor(ResultSequence arg)
			throws DynamicError;

	/**
	 * Retrieves the datatype's name
	 * 
	 * @return String representation of the datatype's name
	 */
	public abstract String type_name();
}
