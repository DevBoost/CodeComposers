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
		List<String> imports = new ArrayList<String>();
		for (Import qualifiedImport : qualifiedImports) {
			String qualifiedName = qualifiedImport.getQualifiedName();
			int endIndex = qualifiedName.lastIndexOf('.');
			if (endIndex > 0) {
				String packageImport = qualifiedName.substring(0, endIndex);
				if (!imports.contains(packageImport)) {
					imports.add(packageImport);
				}
			}
		}

		Collections.sort(imports);

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
