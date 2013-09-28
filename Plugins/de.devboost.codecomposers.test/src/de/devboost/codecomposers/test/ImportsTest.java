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
package de.devboost.codecomposers.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.devboost.codecomposers.java.JavaComposite;

public class ImportsTest {

	@Test
	public void testSimpleImport() {
		JavaComposite jc = new JavaComposite();
		jc.add("package com.pany;");
		jc.addImportsPlaceholder();
		jc.add("public class MyClass {");
		jc.add(jc.getClassName("com.pany.OtherClass") +  " myField;");
		jc.add("}");
		
		String result = getCleanResult(jc);
		assertEquals(result, 
			"package com.pany;" +
			"import com.pany.OtherClass;" +
			"public class MyClass {" +
			"OtherClass myField;" +
			"}"
		);
	}

	@Test
	public void testImportWithNameConflict() {
		JavaComposite jc = new JavaComposite();
		jc.add("package com.pany;");
		jc.addImportsPlaceholder();
		jc.add("public class MyClass {");
		jc.add(jc.getClassName("com.pany.OtherClass") + " myField1;");
		jc.add(jc.getClassName("third.party.OtherClass") + " myField2;");
		jc.add("}");
		
		String result = getCleanResult(jc);
		assertEquals(result, 
			"package com.pany;" +
			"import com.pany.OtherClass;" +
			"public class MyClass {" +
			"OtherClass myField1;" +
			"third.party.OtherClass myField2;" +
			"}"
		);
	}

	@Test
	public void testImplicitImport() {
		JavaComposite jc = new JavaComposite();
		jc.add("package com.pany;");
		jc.addImportsPlaceholder();
		jc.add("public class MyClass {");
		jc.addImplicitImport("MyClass");
		jc.add(jc.getClassName("third.party.MyClass") + " myField1;");
		jc.add("}");
		
		String result = getCleanResult(jc);
		assertEquals(result, 
			"package com.pany;" +
			"public class MyClass {" +
			"third.party.MyClass myField1;" +
			"}"
		);
	}

	@Test
	public void testImplicitImportFromJavaLangPackage1() {
		JavaComposite jc = new JavaComposite();
		jc.add("package com.pany;");
		jc.addImportsPlaceholder();
		jc.add("public class MyClass {");
		// We do not expect an import for java.lang.Throwable as is it imported
		// by default
		jc.add(jc.getClassName("java.lang.Throwable") + " myField1;");
		jc.add(jc.getClassName("third.party.Throwable") + " myField2;");
		jc.add("}");
		
		String result = getCleanResult(jc);
		assertEquals(result, 
			"package com.pany;" +
			"public class MyClass {" +
			"Throwable myField1;" +
			"third.party.Throwable myField2;" +
			"}"
		);
	}

	@Test
	public void testImplicitImportFromJavaLangPackage2() {
		JavaComposite jc = new JavaComposite();
		jc.add("package com.pany;");
		jc.addImportsPlaceholder();
		jc.add("public class MyClass {");
		// We do expect qualified references to java.lang.Throwable there is an
		// import of another class with the same simple name
		jc.add(jc.getClassName("third.party.Throwable") + " myField1;");
		jc.add(jc.getClassName("java.lang.Throwable") + " myField2;");
		jc.add("}");
		
		String result = getCleanResult(jc);
		assertEquals(result, 
			"package com.pany;" +
			"import third.party.Throwable;" +
			"public class MyClass {" +
			"Throwable myField1;" +
			"java.lang.Throwable myField2;" +
			"}"
		);
	}

	@Test
	public void testMultipleImports() {
		JavaComposite jc = new JavaComposite();
		jc.add("package com.pany;");
		jc.addImportsPlaceholder();
		jc.add("public class MyClass {");
		jc.addImplicitImport("MyClass");
		jc.add(jc.getClassName("third.party.OtherClass1") + " myField1;");
		jc.add(jc.getClassName("third.party.OtherClass2") + " myField2;");
		jc.add("}");
		
		String result = getCleanResult(jc, false);
		print(result);
		
		String import1 = "import third.party.OtherClass1;";
		String import2 = "import third.party.OtherClass2;";
		assertTrue("Line break between imports expected.", result.contains(import1 + "\n" + import2));
	}

	private String getCleanResult(JavaComposite jc) {
		return getCleanResult(jc, true);
	}

	private String getCleanResult(JavaComposite jc, boolean removeBreaks) {
		String result = jc.toString();
		if (removeBreaks) {
			result = result.replace("\n", "");
		}
		result = result.replace("\t", "");
		print(result);
		return result;
	}

	private void print(String result) {
		System.out.println(">>>" + result + "<<<");
	}
}
