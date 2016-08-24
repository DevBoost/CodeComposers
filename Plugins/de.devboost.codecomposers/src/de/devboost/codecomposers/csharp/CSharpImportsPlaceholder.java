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
package de.devboost.codecomposers.csharp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.devboost.codecomposers.java.Import;
import de.devboost.codecomposers.java.ImportsPlaceholder;

public class CSharpImportsPlaceholder extends ImportsPlaceholder {
	
	public CSharpImportsPlaceholder(String lineBreak) {
		super(lineBreak);
	}

	@Override
	public String getText() {
		List<String> imports = getImports();
		Collections.sort(imports);
		return toString(imports);
	}

	private List<String> getImports() {
		List<String> imports = new ArrayList<String>();
		for (Import qualifiedImport : qualifiedImports) {
			String qualifiedName = qualifiedImport.getQualifiedName();
			int endIndex = qualifiedName.lastIndexOf('.');
			if (endIndex <= 0) {
				continue;
			}

			String packageImport = qualifiedName.substring(0, endIndex);
			if (imports.contains(packageImport)) {
				continue;
			}
			
			imports.add(packageImport);
		}
		return imports;
	}

	private String toString(List<String> imports) {
		StringBuilder text = new StringBuilder();
		for (String importToAdd : imports) {
			text.append("using");
			text.append(" ");
			text.append(importToAdd);
			text.append(";");
			text.append(lineBreak);
		}
		return text.toString();
	}
}
