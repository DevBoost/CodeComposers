/*******************************************************************************
 * Copyright (c) 2006-2014
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package de.devboost.codecomposers.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Constants for class names used in the generated code.
 */
public class ClassNameConstants {

	public static String ARRAY_LIST(JavaComposite jc) {
		return getClassName(jc, ArrayList.class.getName());
	}

	public static String LINKED_HASH_MAP(JavaComposite jc) {
		return getClassName(jc, LinkedHashMap.class.getName());
	}

	public static String LINKED_HASH_SET(JavaComposite jc) {
		return getClassName(jc, LinkedHashSet.class.getName());
	}

	public static String LIST(JavaComposite jc) {
		return getClassName(jc, List.class.getName());
	}

	public static String ITERATOR(JavaComposite jc) {
		return getClassName(jc, Iterator.class.getName());
	}

	public static String MAP(JavaComposite jc) {
		return getClassName(jc, Map.class.getName());
	}

	public static String MAP_ENTRY(JavaComposite jc) {
		return getClassName(jc, Map.Entry.class.getCanonicalName());
	}

	public static String SET(JavaComposite jc) {
		return getClassName(jc, Set.class.getName());
	}

	public static String getClassName(JavaComposite jc,
			String qualifiedClassName) {
		
		if (jc == null) {
			return qualifiedClassName;
		}
		return jc.getClassName(qualifiedClassName);
	}
}
