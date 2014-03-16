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
package de.devboost.codecomposers.xml;

import de.devboost.codecomposers.Component;
import de.devboost.codecomposers.StringComposite;

/**
 * A {@link XMLComposite} is a custom {@link StringComposite} that can be used
 * to build XML documents.
 */
// TODO Pre-compile regular expressions
public class XMLComposite extends StringComposite {
	
	public XMLComposite() {
		super();
		addLineBreaker(">");
	}

	@Override
	public boolean isIndendationStarter(Component component) {
		boolean isStarter = component.toString().endsWith(">") &&
			!component.toString().endsWith("?>") && 
			!(isEndTag(component) || isCompactTag(component) || isTagOnOneLine(component));
		return isStarter;
	}

	@Override
	public boolean isIndendationStopper(Component component) {
		boolean isStopper = isEndTag(component) && !isCompactTag(component) && !isTagOnOneLine(component);
		return isStopper;
	}

	private boolean isEndTag(Component component) {
		return component.toString().matches("</.*>");
	}

	private boolean isCompactTag(Component component) {
		return component.toString().matches("<.*/>");
	}
	
	private boolean isTagOnOneLine(Component component) {
		return component.toString().matches("<.*>.*</.*>");
	}
}
