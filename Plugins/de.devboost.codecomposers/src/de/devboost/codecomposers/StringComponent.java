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
package de.devboost.codecomposers;

/**
 * A {@link StringComponent} is a part of a {@link StringComposite}. By default all {@link StringComponent}s are
 * disabled and do thus not appear when the containing {@link StringComposite} is converted to a String.
 */
public class StringComponent implements Component {

	private final String text;
	private boolean enabled = false;
	private String enabler;

	/**
	 * Creates a new {@link StringComponent} containing the given text. The {@link StringComponent} is only enabled if
	 * the given 'enabler' is passed to {@link #enable(String)} or if 'enabler' is <code>null</code>.
	 * 
	 * @param text
	 *            the text to print
	 * @param enabler
	 *            a text that is required to enable this component
	 */
	public StringComponent(String text, String enabler) {
		super();
		this.text = text;
		this.enabler = enabler;
		if (enabler == null) {
			enabled = true;
		}
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void enable(String text) {
		if (enabler != null && text != null && text.contains(enabler)) {
			enabled = true;
		}
	}

	@Override
	public String toString() {
		return toString(0);
	}

	@Override
	public String toString(int tabs) {
		String textValue = getText();
		if (tabs == 0) {
			return textValue;
		} else {
			return StringComposite.getTabText(tabs) + textValue;
		}
	}

	public String getEnabler() {
		return enabler;
	}
}
