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
package de.devboost.codecomposers.antlr;

import de.devboost.codecomposers.StringComposite;
import de.devboost.codecomposers.java.JavaComposite;

/**
 * A {@link ANTLRGrammarComposite} is a custom {@link StringComposite} that can be used to build grammars for ANTLR.
 */
public class ANTLRGrammarComposite extends JavaComposite {

	public ANTLRGrammarComposite() {
		super();
		addLineBreaker(":");
		addLineBreaker("]");
		addLineBreaker("(");
		addLineBreaker(")");
		addIndentationStarter(":");
		addIndentationStopper(";");
		addIndentationStarter("(");
		addIndentationStopper(")");
	}
}
