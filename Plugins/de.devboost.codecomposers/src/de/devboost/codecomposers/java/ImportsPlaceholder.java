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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.devboost.codecomposers.StringComponent;

public class ImportsPlaceholder extends StringComponent {
	
	private Set<Import> qualifiedImports = new LinkedHashSet<Import>();
	private Set<Import> implicitImports = new LinkedHashSet<Import>();
	
	private String lineBreak;

	public ImportsPlaceholder(String lineBreak) {
		super(null, null);
		this.lineBreak = lineBreak;
		
		for (Class<?> nextClass : JavaLangClasses.CLASSES_IN_JAVA_LANG_PACKAGE) {
			addImplicitImport(nextClass.getName());
		}
	}

	public String getClassName(String qualifiedClassName) {
		if (isPrimitiveType(qualifiedClassName)) {
			return qualifiedClassName;
		}
		
		if (isGeneric(qualifiedClassName)) {
			List<String> types = getTypeArguments(qualifiedClassName);
			String typeParameters = types.get(1);
			String[] typeParameterArray = typeParameters.split(",");
			StringBuilder result = new StringBuilder();
			result.append(getClassName(types.get(0)));
			result.append("<");
			for (int i = 0; i < typeParameterArray.length; i++) {
				String typeParameter = typeParameterArray[i];				
				if ("?".equals(typeParameter.trim())) {
					result.append(typeParameter);
				} else {
					result.append(getClassName(typeParameter));
				}
				
				boolean isNotLast = i < typeParameterArray.length - 1;
				if (isNotLast) {
					result.append(",");
				}
			}
			result.append(">");
			return result.toString();
		}
		
		return getName(qualifiedClassName, false);
	}

	public String getStaticMemberName(String qualifiedMemberName) {
		return getName(qualifiedMemberName, true);
	}

	private String getName(String qualifiedName, boolean isStatic) {
		if (contains(qualifiedImports, qualifiedName) ||
			contains(implicitImports, qualifiedName)) {
			String simpleName = getSimpleName(qualifiedName);
			return simpleName;
		} else {
			if (isNameImported(qualifiedName)) {
				return qualifiedName;
			} else {
				String simpleName = getSimpleName(qualifiedName);
				qualifiedImports.add(new Import(simpleName, qualifiedName, isStatic));
				return simpleName;
			}
		}
	}

	private boolean contains(Set<Import> importSet, String qualifiedClassName) {
		for (Import nextImport : importSet) {
			if (qualifiedClassName.equals(nextImport.getQualifiedName())) {
				return true;
			}
		}
		return false;
	}

	private boolean containsSimpleName(Set<Import> importSet, String simpleName) {
		for (Import nextImport : importSet) {
			if (simpleName.equals(nextImport.getSimpleName())) {
				return true;
			}
		}
		return false;
	}

	private List<String> getTypeArguments(String qualifiedClassName) {
		// TODO This is a very simplistic way to get the type arguments which
		// will neither work for multiple nor nested arguments.
		int begin = qualifiedClassName.indexOf("<");
		int end = qualifiedClassName.indexOf(">");
		String type = qualifiedClassName.substring(0, begin);
		String typeArgument = qualifiedClassName.substring(begin + 1, end);
		
		List<String> types = new ArrayList<String>(2);
		types.add(type);
		types.add(typeArgument);
		return types;
	}

	private boolean isGeneric(String qualifiedClassName) {
		return qualifiedClassName.contains("<");
	}

	private boolean isPrimitiveType(String qualifiedClassName) {
		
		// Remove array qualifiers
		qualifiedClassName = qualifiedClassName.replace("[", "");
		qualifiedClassName = qualifiedClassName.replace("]", "");
		
		Class<?>[] primitiveTypes = new Class<?>[] {boolean.class, int.class, char.class, byte.class, long.class, double.class, float.class};
		for (Class<?> primitiveType : primitiveTypes) {
			if (primitiveType.getName().equals(qualifiedClassName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isNameImported(String qualifiedClassName) {
		String simpleName = getSimpleName(qualifiedClassName);
		return containsSimpleName(qualifiedImports, simpleName) ||
				containsSimpleName(implicitImports, simpleName);
	}

	private String getSimpleName(String qualifiedClassName) {
		return qualifiedClassName.substring(qualifiedClassName.lastIndexOf(".") + 1);
	}
	
	@Override
	public String getText() {
		List<String> imports = new ArrayList<String>();
		for (Import qualifiedImport : qualifiedImports) {
			if (qualifiedImport == null) {
				continue;
			}
			// Do not import classes that are imported by default
			String prefix = "java.lang.";
			String qualifiedName = qualifiedImport.getQualifiedName();
			if (qualifiedName.startsWith(prefix) && qualifiedName.substring(prefix.length() + 1).indexOf(".") < 0) {
				continue;
			}
			boolean isStatic = qualifiedImport.isStatic();
			imports.add((isStatic ? "static " : "") + qualifiedName);
		}
		
		Collections.sort(imports);
		
		StringBuilder text = new StringBuilder();
		for (String importToAdd : imports) {
			text.append("import ");
			text.append(importToAdd);
			text.append(";");
			text.append(lineBreak);
		}
		return text.toString();
	}

	public void addImplicitImport(String qualifiedClassName) {
		String simpleName = getSimpleName(qualifiedClassName);
		implicitImports.add(new Import(simpleName, qualifiedClassName, false));
	}
}
