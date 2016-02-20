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

public class Import {

	private final String simpleName;
	private final String qualifiedName;
	private final boolean isStatic;

	public Import(String simpleName, String qualifiedName, boolean isStatic) {
		super();
		this.simpleName = simpleName;
		this.qualifiedName = qualifiedName;
		this.isStatic = isStatic;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public boolean isStatic() {
		return isStatic;
	}
}
