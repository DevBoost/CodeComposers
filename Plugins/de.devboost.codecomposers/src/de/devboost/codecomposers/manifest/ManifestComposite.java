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
package de.devboost.codecomposers.manifest;

import de.devboost.codecomposers.Component;
import de.devboost.codecomposers.StringComposite;

/**
 * A {@link ManifestComposite} is a custom {@link StringComposite} that can be used to build manifest files for Eclipse
 * plug-ins.
 */
public class ManifestComposite extends StringComposite {

	public ManifestComposite() {
		super(true);
	}

	@Override
	protected boolean isLineBreaker(Component component) {
		return true;
	}
}
