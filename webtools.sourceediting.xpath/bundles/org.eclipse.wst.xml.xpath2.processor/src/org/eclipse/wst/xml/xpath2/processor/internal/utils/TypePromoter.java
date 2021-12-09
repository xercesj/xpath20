/*******************************************************************************
 * Copyright (c) 2009, 2010 Jesper Steen Moller, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jesper Steen Moller - initial API and implementation
 *     Jesper Steen Moller - bug 281028 - avg/min/max/sum work
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *     Mukul Gandhi - bug 393904 - improvements to computing typed value of element nodes
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.utils;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;

/**
 * Generic type promoter for handling subtype substitution and type promotions for functions and operators.
 */
public abstract class TypePromoter {
	private Class targetType = null;

	abstract public AnyAtomicType doPromote(AnyAtomicType value) throws DynamicError;	

	public final AnyAtomicType promote(AnyType value) throws DynamicError {
		// This is a short cut, really
		if (value.getClass() == getTargetType()) return (AnyAtomicType)value;

		return doPromote(atomize(value));
	}

	/**
	 * @param typeToConsider The 
	 * @return The supertype to treat it as (i.e. if a xs:nonNegativeInteger is treated as xs:number)
	 */
	abstract protected Class substitute(Class typeToConsider);	

	abstract protected boolean checkCombination(Class newType);
		
	public void considerType(Class typeToConsider) throws DynamicError {
		Class baseType = substitute(typeToConsider);
		String typeStrName = getTypeNameStr(typeToConsider);
		
		if (baseType == null) {
			throw DynamicError.argument_type_error(typeStrName);
		}
		
		if (targetType == null) {
			targetType = baseType;
		} else {
			if (! checkCombination(baseType)) {
				throw DynamicError.argument_type_error(typeStrName);
			}
		}
	}

	private String getTypeNameStr(Class typeClass) {
		String typeStrName = "";
		try {
			typeStrName = ((AnyType)typeClass.newInstance()).string_type();
		} catch (InstantiationException e) {
		   // no op
		} catch (IllegalAccessException e) {
		   // no op	
		}
		return typeStrName;
	}
	
	public void considerTypes(Collection typesToConsider) throws DynamicError {		
		for (Iterator iter = typesToConsider.iterator(); iter.hasNext();) {
			considerType((Class)iter.next());	
		}
	}
	
	public void considerSequence(ResultSequence sequenceToConsider) throws DynamicError {
		for (int i = 0; i < sequenceToConsider.size(); ++i) {
			AnyType item = sequenceToConsider.get(i);
			considerValue(item);
		}
	}
	
	public Class getTargetType() {
		return targetType;
	}
	
	protected void setTargetType(Class class1) {
		this.targetType = class1;
	}

	public AnyAtomicType atomize(AnyType at) throws DynamicError {
		if (at instanceof NodeType) {
			return (AnyAtomicType)((NodeType)at).typed_value().first();
		}
		else {
			return (AnyAtomicType)at;
		}
	}
	
	public void considerValue(AnyType at) throws DynamicError {
		considerType(atomize(at).getClass());
	}


}
