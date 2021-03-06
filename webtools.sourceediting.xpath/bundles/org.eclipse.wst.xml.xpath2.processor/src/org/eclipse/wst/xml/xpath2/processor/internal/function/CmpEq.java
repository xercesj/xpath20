/*******************************************************************************
 * Copyright (c) 2005, 2009 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper Moller - bug 280555 - Add pluggable collation support
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;

/**
 * Class for compare for equal function.
 */
public interface CmpEq {
	/**
	 * Constructor for CmpEq.
	 * 
	 * @param arg
	 *            argument of any type.
	 * @param context TODO
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of operation, true/false.
	 */
	public boolean eq(AnyType arg, DynamicContext context) throws DynamicError;
}
