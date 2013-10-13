/*******************************************************************************
 * Copyright (c) 2006-2013
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
		return jc.getClassName(ArrayList.class.getName());
	}

	public static String LINKED_HASH_MAP(JavaComposite jc) {
		return jc.getClassName(LinkedHashMap.class.getName());
	}

	public static String LINKED_HASH_SET(JavaComposite jc) {
		return jc.getClassName(LinkedHashSet.class.getName());
	}

	public static String LIST(JavaComposite jc) {
		return jc.getClassName(List.class.getName());
	}

	public static String MAP(JavaComposite jc) {
		return jc.getClassName(Map.class.getName());
	}

	public static String MAP_ENTRY(JavaComposite jc) {
		return jc.getClassName(Map.Entry.class.getCanonicalName());
	}

	public static String SET(JavaComposite jc) {
		return jc.getClassName(Set.class.getName());
	}
}
