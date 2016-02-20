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

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link StringComposite} can be used to compose text fragments. In contrast to a {@link StringBuilder} or
 * {@link StringBuffer}, this class can enable and disable text fragments. This is useful when text is composed, but
 * unnecessary parts need to be removed later.
 * <p>
 * This class can be used by code generators to insert variable declarations only if they are referenced.
 */
public class StringComposite {

	/**
	 * A Node is the atomic part of a tree.
	 */
	public interface Node {
		public Tree getParent();
	}

	/**
	 * A CompositeNode is part of a tree and contains exactly one {@link StringComponent}.
	 */
	public class ComponentNode implements Node {

		private final Tree parent;
		private final StringComponent component;

		public ComponentNode(Tree parent, StringComponent component) {
			super();
			this.parent = parent;
			this.component = component;
		}

		public Tree getParent() {
			return parent;
		}

		public StringComponent getComponent() {
			return component;
		}
	}

	/**
	 * A Tree is a container for Nodes. Since trees are nodes as well, they can contain further trees.
	 */
	public class Tree implements Node {

		private final List<Node> children = new ArrayList<Node>();
		private final Tree parent;

		public Tree(Tree parent) {
			super();
			this.parent = parent;
			if (parent != null) {
				parent.addChildNode(this);
			}
		}

		public List<Node> getChildNodes() {
			return children;
		}

		public void addChildNode(Node node) {
			children.add(node);
		}

		public Tree getParent() {
			return parent;
		}
	}

	/**
	 * We do intentionally not use the platform specific line separation character, because the files that are generated
	 * using this {@link StringComposite} shall look the same on all platforms. Eclipse does understand the different
	 * kinds of line separators anyway and treats them correctly on all platforms.
	 */
	public static final String UNIX_LINE_BREAK = "\n";

	private static final int MAX_TABS = 20;
	private static final String[] TAB_STRINGS = new String[20];

	private final List<StringComponent> components = new ArrayList<StringComponent>();

	private final List<String> lineBreakers = new ArrayList<String>();
	private final List<String> indentationStarters = new ArrayList<String>();
	private final List<String> indentationStoppers = new ArrayList<String>();

	private boolean enabled;

	private String lineBreak = UNIX_LINE_BREAK;

	public StringComposite() {
		this(true);
	}

	public StringComposite(boolean enabled) {
		super();
		this.enabled = enabled;
	}

	public void addIndentationStarter(String starter) {
		if (starter == null) {
			throw new IllegalArgumentException("Can't use null as indentation starter.");
		}
		indentationStarters.add(starter);
	}

	public void addIndentationStopper(String stopper) {
		if (stopper == null) {
			throw new IllegalArgumentException("Can't use null as indentation starter.");
		}
		indentationStoppers.add(stopper);
	}

	public void addLineBreaker(String lineBreaker) {
		lineBreakers.add(lineBreaker);
	}

	public StringComposite addLineBreak() {
		add(getLineBreak());
		return this;
	}

	public StringComposite add(String text) {
		StringComponent component = new StringComponent(text, null);
		add(component);
		return this;
	}

	public StringComposite add(StringBuilder text) {
		return add(text.toString());
	}

	public StringComposite add(StringComponent component) {
		components.add(component);
		return this;
	}

	public void add(StringComposite other) {
		components.addAll(other.components);
	}

	@Override
	public String toString() {
		return toString(0, true);
	}

	public String toString(int tabs, boolean doLineBreaks) {
		StringBuilder builder = null;

		enableComponents();

		// then add enabled components to the builder
		for (Component component : components) {
			if (isIndendationStopper(component)) {
				tabs--;
			}
			if (component.isEnabled()) {
				String text = component.toString(tabs);
				if (builder == null) {
					builder = new StringBuilder();
				}
				builder.append(text);
				if (doLineBreaks && isLineBreaker(component)) {
					builder.append(getLineBreak());
				}
			}
			if (isIndendationStarter(component)) {
				tabs++;
			}
		}
		if (builder == null) {
			return "";
		}
		return builder.toString();
	}

	public String getLineBreak() {
		return lineBreak;
	}

	public void setLineBreak(String lineBreak) {
		this.lineBreak = lineBreak;
	}

	private void enableComponents() {
		List<ComponentNode> disabledComponents = new ArrayList<ComponentNode>();

		// find the scoping depth for the disabled components
		Tree subTree = new Tree(null);
		for (int i = 0; i < components.size(); i++) {
			StringComponent component = components.get(i);
			final ComponentNode node = new ComponentNode(subTree, component);

			final boolean isStarter = isIndendationStarter(component);
			final boolean isStopper = isIndendationStopper(component);
			final Tree parent = subTree.getParent();
			if (isStarter) {
				if (isStopper) {
					if (parent != null) {
						subTree = parent;
					}
					subTree.addChildNode(node);
					subTree = new Tree(subTree);
				} else {
					subTree.addChildNode(node);
					subTree = new Tree(subTree);
				}
			} else {
				if (isStopper) {
					if (parent != null) {
						subTree = parent;
					}
					subTree.addChildNode(node);
				} else {
					subTree.addChildNode(node);
				}
			}
			if (!component.isEnabled()) {
				disabledComponents.add(node);
			}
		}

		for (ComponentNode disabledComponent : disabledComponents) {
			// deep search right siblings
			List<Node> siblings = disabledComponent.getParent().getChildNodes();
			boolean right = false;
			for (Node sibling : siblings) {
				if (sibling == disabledComponent) {
					right = true;
					continue;
				}
				if (!right) {
					continue;
				}
				enable(disabledComponent.getComponent(), sibling);
			}
		}
	}

	private void enable(StringComponent component, Node node) {
		if (node instanceof Tree) {
			List<Node> children = ((Tree) node).getChildNodes();
			for (Node child : children) {
				enable(component, child);
			}
		} else {
			StringComponent nodeComponent = ((ComponentNode) node).getComponent();
			if (nodeComponent.isEnabled()) {
				String text = nodeComponent.getText();
				component.enable(text);
			}
		}
	}

	protected boolean isLineBreaker(Component component) {
		String componentString = component.toString();
		for (String starter : lineBreakers) {
			if (componentString.endsWith(starter)) {
				return true;
			}
		}
		return false;
	}

	protected boolean isIndendationStarter(Component component) {
		String componentString = component.toString();
		for (String starter : indentationStarters) {
			if (componentString.endsWith(starter)) {
				return true;
			}
		}
		return false;
	}

	protected boolean isIndendationStopper(Component component) {
		String componentString = component.toString();
		for (String stopper : indentationStoppers) {
			if (componentString.startsWith(stopper)) {
				return true;
			}
		}
		return false;
	}

	public static String getTabText(int tabs) {
		if (tabs < 0) {
			return "";
		}
		if (tabs >= MAX_TABS) {
			return createTabString(tabs);
		}
		String tabString = TAB_STRINGS[tabs];
		if (tabString == null) {
			TAB_STRINGS[tabs] = createTabString(tabs);
		}
		return TAB_STRINGS[tabs];
	}

	private static String createTabString(int tabs) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tabs; i++) {
			builder.append('\t');
		}
		return builder.toString();
	}

	public boolean isEnabled() {
		return enabled;
	}
}
