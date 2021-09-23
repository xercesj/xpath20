/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Mukul Gandhi - bug274731 - implementation of fn:document-uri function
 *     Jesper Moller- bug 281159 - fix document loading and resolving URIs 
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.*;
import org.eclipse.wst.xml.xpath2.processor.internal.types.*;

import java.util.*;

/**
 * Returns the value of the document-uri property for $arg as defined by the
 * dm:document-uri accessor function defined in Section 6.1.2 AccessorsDM. If
 * $arg is the empty sequence, the empty sequence is returned. Returns the empty
 * sequence if the node is not a document node or if its document-uri property
 * is a relative URI. Otherwise, returns an absolute URI expressed as an
 * xs:string.
 */
public class FnDocumentUri extends Function {
	private static Collection _expected_args = null;

	/**
	 * Constructor for FnDocumentUri.
	 */
	public FnDocumentUri() {
		super(new QName("document-uri"), 1);
	}

	/**
	 * Evaluate arguments.
	 * 
	 * @param args
	 *            argument expressions.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of evaluation.
	 */
	public ResultSequence evaluate(Collection args) throws DynamicError {
		return document_uri(args);
	}

	/**
	 * Document-Uri operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:document-uri operation.
	 */
	public static ResultSequence document_uri(Collection args)
			throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence arg1 = (ResultSequence) cargs.iterator().next();

		ResultSequence rs = ResultSequenceFactory.create_new();
		if (arg1.empty())
		  return rs;

		NodeType nt = (NodeType) arg1.first();

		if (!(nt instanceof DocType))
		  return rs;

		DocType dt = (DocType) nt;
		String documentURI = dt.value().getDocumentURI();
		
		if (documentURI != null) {
			XSAnyURI docUri = new XSAnyURI(documentURI);
			rs.add(docUri);
		}
		return rs;
	}

	/**
	 * Obtain a list of expected arguments.
	 * 
	 * @return Result of operation.
	 */
	public synchronized static Collection expected_args() {
		if (_expected_args == null) {
			_expected_args = new ArrayList();
			_expected_args.add(new SeqType(SeqType.OCC_QMARK));
		}

		return _expected_args;
	}
}
