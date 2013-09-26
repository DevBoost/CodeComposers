package de.devboost.codecomposers.java;

import java.util.LinkedHashMap;
import java.util.Map;

import de.devboost.codecomposers.StringComponent;

public class ImportsPlaceholder extends StringComponent {
	
	private Map<String, String> qualifiedImports = new LinkedHashMap<String, String>();

	public ImportsPlaceholder() {
		super(null, null);
	}

	public String getClassName(String qualifiedClassName) {
		if (qualifiedImports.values().contains(qualifiedClassName)) {
			String simpleName = getSimpleName(qualifiedClassName);
			return simpleName;
		} else {
			if (isNameImported(qualifiedClassName)) {
				return qualifiedClassName;
			} else {
				String simpleName = getSimpleName(qualifiedClassName);
				qualifiedImports.put(simpleName, qualifiedClassName);
				return simpleName;
			}
		}
	}

	private boolean isNameImported(String qualifiedClassName) {
		String simpleName = getSimpleName(qualifiedClassName);
		return qualifiedImports.keySet().contains(simpleName);
	}

	private String getSimpleName(String qualifiedClassName) {
		return qualifiedClassName.substring(qualifiedClassName.lastIndexOf(".") + 1);
	}
	
	@Override
	public String getText() {
		StringBuilder text = new StringBuilder();
		for (String qualifiedImport : qualifiedImports.values()) {
			if (qualifiedImport == null) {
				continue;
			}
			text.append("import ");
			text.append(qualifiedImport);
			text.append(";");
		}
		return text.toString();
	}

	public void addImplicitImport(String simpleClassName) {
		qualifiedImports.put(simpleClassName, null);
	}
}
