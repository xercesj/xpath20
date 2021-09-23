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

package org.eclipse.wst.xml.xpath2.processor.internal;

/**
 * Error caused by JFlex
 */
public class JFlexError extends XPathError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7154784482417833297L;

	/**
	 * Constructor for JFlex error.
	 * 
	 * @param reason
	 *            is the reason for the error.
	 */
	public JFlexError(String reason) {
		super(reason);
	}
}
