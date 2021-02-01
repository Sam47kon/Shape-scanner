package com.app;

import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import java.awt.*;
import java.util.Map;


public class ShapeListTemplate implements Template {
	@Override
	public Component[] create(Component parent, Component insertBefore, VariableResolver resolver, Composer composer) {

		Shape shape = (Shape) resolver.resolveVariable("each");
		Listitem listitem = new Listitem();
		listitem.appendChild(new Listcell(shape.getShapeType()));
		listitem.appendChild(new Listcell(shape.getCenter().toString()));

		Canvas canvas = new Canvas();


		return new Component[0];
	}

	@Override
	public Map<String, Object> getParameters() {
		return null;
	}
}
