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
package de.devboost.codecomposers;

/**
 * A {@link Component} is a piece of text that can be used to compose text documents. A {@link Component} can be atomic
 * (a string) or composed of other {@link Component}s. {@link Component}s can be enabled and converted to strings.
 */
public interface Component {

	/**
	 * Returns <code>true</code> if this {@link Component} is enabled (i.e., if it shall appear in the generated code).
	 */
	public boolean isEnabled();

	/**
	 * Converts this {@link Component} to a String using the given number of tabs as indentation.
	 * 
	 * @param tabs
	 *            the number of tabs to put before each line
	 * @return a String representation of this {@link Component}
	 */
	public String toString(int tabs);
}
