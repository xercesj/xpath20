/*******************************************************************************
 * Copyright (c) 2010 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - initial API and implementation
 *     Mukul Gandhi - bug 323900 - improving computing the typed value of element and attribute nodes, where the 
 *                                 schema type of nodes are simple, with varieties 'list' and 'union'.
 *     Mukul Gandhi - bug 343224 - allow user defined simpleType definitions to be available in in-scope schema types 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.xs.TypeValidatorHelper;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

/*
 * An XPath 2.0 engine helper class, providing common method implementations for 
 * performing XML Schema evaluation tasks.  
 */
public class XPath20TypeHelper {
	
	// XPath engine specific constants to support new built-in types, 
	// introduced in XML Schema 1.1.
	public static short DAYTIMEDURATION_DT = -100;
	public static short YEARMONTHDURATION_DT = -101;
	
	
	/* 
	 * Get Xerces "schema type" short code, given a type definition instance object. 
	 * Eclipse XPath 2.0 engine uses few custom type 'short codes', to support 
	 * XML Schema 1.1.
	 */
	public static short getXSDTypeShortCode(XSTypeDefinition typeDef) {
		
		// xsd type "short code" initializer
		short typeCode = -100;
		boolean xs11Types = false;
		
		if ("dayTimeDuration".equals(typeDef.getName())) {
			typeCode = DAYTIMEDURATION_DT;
			xs11Types = true;
		}
		else if ("yearMonthDuration".equals(typeDef.getName())) {
			typeCode = YEARMONTHDURATION_DT;
			xs11Types = true;
		}
		
		return (xs11Types ? typeCode : ((XSSimpleTypeDefinition) typeDef).getBuiltInKind());
		
	} // getXSDTypeShortCode
	
	
	/*
	 * Determine if a "string value" is valid for a given simpleType definition.
	 */
	public static boolean isValueValidForSimpleType(String value, XSSimpleType simplType) {
		
		boolean isValueValid = true;
		
		try {
			ValidatedInfo validatedInfo = new ValidatedInfo();
	        ValidationState validationState = new ValidationState();
	        validationState.setTypeValidatorHelper(TypeValidatorHelper.getInstance(Constants.SCHEMA_VERSION_1_1));    		
			// attempt to validate the value with the simpleType
			simplType.validate(value, validationState, validatedInfo);
	    } 
		catch(InvalidDatatypeValueException ex){
			isValueValid = false;
	    }
		
		return isValueValid;
		
	} // isValueValidForSimpleType

} // class XPath20TypeHelper
