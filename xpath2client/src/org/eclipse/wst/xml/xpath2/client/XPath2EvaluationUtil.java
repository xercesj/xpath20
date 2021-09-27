/*******************************************************************************
 * Copyright (c) 2021 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.xerces.xs.XSModel;
import org.eclipse.wst.xml.xpath2.processor.DOMLoader;
import org.eclipse.wst.xml.xpath2.processor.DOMLoaderException;
import org.eclipse.wst.xml.xpath2.processor.DefaultDynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.JFlexCupParser;
import org.eclipse.wst.xml.xpath2.processor.StaticChecker;
import org.eclipse.wst.xml.xpath2.processor.StaticError;
import org.eclipse.wst.xml.xpath2.processor.StaticNameResolver;
import org.eclipse.wst.xml.xpath2.processor.XPathParser;
import org.eclipse.wst.xml.xpath2.processor.XercesLoader;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.function.FnFunctionLibrary;
import org.eclipse.wst.xml.xpath2.processor.function.XSCtrLibrary;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * Common utility methods, for XPath 2.0 expression evaluations.
 */
public class XPath2EvaluationUtil {
	
	public static Document loadDOMDocument(URL xmlDocUrl) throws IOException,
	                                                             DOMLoaderException {
       InputStream is = xmlDocUrl.openStream();
       DOMLoader domloader = new XercesLoader();
       domloader.set_validating(false);
       Document domDoc = domloader.load(is);
       domDoc.setDocumentURI(xmlDocUrl.toString());
       
       return domDoc; 
    }
	
	public static Document loadDOMDocument(URL xmlDocUrl, URL schemaDocUrl)
			                                  throws IOException, DOMLoaderException, 
			                                         SAXException {
		InputStream is = xmlDocUrl.openStream();
		InputStream schemaIs = schemaDocUrl.openStream();
		Schema jaxpSchema = getSchema(schemaIs);
		DOMLoader domloader = new XercesLoader(jaxpSchema);
		domloader.set_validating(false);
		Document domDoc = domloader.load(is);
		
		return domDoc;
	}
	
	public static DynamicContext setupDynamicContext(XSModel schema, 
			                                         Document document) {
		DynamicContext dc = new DefaultDynamicContext(schema, document);
		
		dc.add_namespace("xs", "http://www.w3.org/2001/XMLSchema");
		dc.add_namespace("xsd", "http://www.w3.org/2001/XMLSchema");
		dc.add_namespace("fn", "http://www.w3.org/2005/xpath-functions");
		dc.add_namespace("xml", "http://www.w3.org/XML/1998/namespace");

		dc.add_function_library(new FnFunctionLibrary());
		dc.add_function_library(new XSCtrLibrary());
		
		return dc;
	}

	public static XPath compileXPath(DynamicContext dc, String xpath) 
			                                               throws StaticError {
		XPathParser xpp = new JFlexCupParser();
		XPath path = xpp.parse(xpath);

		StaticChecker name_check = new StaticNameResolver(dc);
		name_check.check(path);
		
		return path;
	}
	
	private static Schema getSchema(InputStream schemaIs) throws SAXException {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new StreamSource(schemaIs));
		return schema;
	}

}
