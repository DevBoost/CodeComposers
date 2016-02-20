/*******************************************************************************
 * Copyright (c) 2006-2016
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Dresden, Amtsgericht Dresden, HRB 34001
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Dresden, Germany
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
public interface IClassNameConstants {

	public String ARRAY_LIST = ArrayList.class.getName();
	public String LINKED_HASH_MAP = LinkedHashMap.class.getName();
	public String LINKED_HASH_SET = LinkedHashSet.class.getName();
	public String LIST = List.class.getName();
	public String MAP = Map.class.getName();
	public String MAP_ENTRY = Map.Entry.class.getCanonicalName();
	public String SET = Set.class.getName();
}
