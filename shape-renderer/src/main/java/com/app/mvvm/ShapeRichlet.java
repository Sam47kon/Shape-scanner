package com.app.mvvm;

import lombok.extern.slf4j.Slf4j;
import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.RichletConfig;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.XulElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ShapeRichlet extends GenericRichlet {

	private Binder binder;

	@Override
	public void service(Page page) {
		page.setTitle("Richlet Shape");

		//1.Create root component which is to be associated with binder
		final Window window = new Window("Richlet Test", "normal", false);
		window.setWidth("1000px");
		window.setPage(page);

		//2.Instantiate a binder instance. Use DefaultBinder
		binder = new DefaultBinder();
		//3. Initialize it with View model and root component
		binder.init(window, new ShapeViewModel(), null);

		//4. Set binder as an attribute on the root component
		window.setAttribute("vm", binder.getViewModel());

		Vbox vbox = new Vbox();
		vbox.setHflex("true");
		window.appendChild(vbox);

		vbox.appendChild(buildSearchBar(binder));
		vbox.appendChild(buildShapeListBox(binder));
		vbox.appendChild(buildFormArea(binder));
		vbox.appendChild(buildCreateFormArea(binder));

		binder.loadComponent(window, true);
	}

	private Groupbox buildCreateFormArea(Binder binder) {
		Groupbox form = new Groupbox();
		form.setHflex("true");

		Grid grid = new Grid();
		grid.setHflex("true");
		grid.setParent(form);

		Columns columns = new Columns();
		columns.setSizable(true);
		Column labelCol = new Column("Добавление фигуры");
		labelCol.setWidth("250px");
		columns.appendChild(labelCol);
		columns.appendChild(new Column());
		grid.appendChild(columns);

		Rows rows = new Rows();
		grid.appendChild(rows);

		Row rowX = new Row();
		rowX.appendChild(new Label("Введите X:"));
		Doublebox boxX = new Doublebox();
		binder.addPropertyLoadBindings(boxX, "value", "vm.x", null, null, null, null, null);
		binder.addPropertySaveBindings(boxX, "value", "vm.x", null, null, null, null, null, null, null);
		rowX.appendChild(boxX);
		rows.appendChild(rowX);

		Row rowY = new Row();
		rowY.appendChild(new Label("Введите Y:"));
		Doublebox boxY = new Doublebox();
		binder.addPropertyLoadBindings(boxY, "value", "vm.y", null, null, null, null, null);
		binder.addPropertySaveBindings(boxY, "value", "vm.y", null, null, null, null, null, null, null);
		rowY.appendChild(boxY);
		rows.appendChild(rowY);

		Row pointsRow = new Row();
		binder.addPropertyLoadBindings(pointsRow, "visible", "vm.points.size() != 0", null, null, null, null, null);
		pointsRow.appendChild(new Label("Точки:"));
		Label points = new Label();
		binder.addPropertyLoadBindings(points, "value", "vm.points", null, null, null, null, null);
		binder.addPropertySaveBindings(points, "value", "vm.points", null, null, null, null, null, null, null);
		pointsRow.appendChild(points);
		rows.appendChild(pointsRow);

		Row rowSavePoint = new Row();
		Button savePointBtn = new Button("Добавить координату");
		binder.addCommandBinding(savePointBtn, Events.ON_CLICK, "'addPoint'", null);
		binder.addPropertyLoadBindings(savePointBtn, "disabled", "empty vm.x || empty vm.y", null, null, null, null, null);
		rowSavePoint.appendChild(savePointBtn);
		Button createShape = new Button("Создать фигуру");
		binder.addCommandBinding(createShape, Events.ON_CLICK, "'createShape'", null);
		binder.addPropertyLoadBindings(createShape, "disabled", "vm.points.size() < 2", null, null, null, null, null);
		rowSavePoint.appendChild(createShape);
		rows.appendChild(rowSavePoint);

		return form;
	}

	private Groupbox buildFormArea(Binder binder) {
		Style messageStyle = new Style();
		messageStyle.setContent(".z-label.red{ color:red;}");
		Groupbox form = new Groupbox();
		form.setMold("3d");
		form.setHflex("true");
		form.setVisible(false);
		form.appendChild(messageStyle);
		binder.addPropertyLoadBindings(form, "visible", "not empty vm.selectedShape", null, null, null, null, null);

		Grid grid = new Grid();
		grid.setHflex("true");
		grid.setParent(form);

		Columns columns = new Columns();
		Column labelCol = new Column("Параметр");
		labelCol.setWidth("120px");
		columns.appendChild(labelCol);
		columns.appendChild(new Column("Значение"));
		grid.appendChild(columns);

		Rows rows = new Rows();
		grid.appendChild(rows);

		Row idRow = new Row();
		idRow.appendChild(new Label("Id"));
		Label idLabel = new Label();
		idRow.appendChild(idLabel);
		rows.appendChild(idRow);
		binder.addPropertyLoadBindings(idLabel, "value", "vm.selectedShape.id", null, null, null, null, null);

		Row shapeTypeRow = new Row();
		shapeTypeRow.appendChild(new Label("Тип фигуры"));
		Label shapeTypeLabel = new Label();
		shapeTypeRow.appendChild(shapeTypeLabel);
		rows.appendChild(shapeTypeRow);
		binder.addPropertyLoadBindings(shapeTypeLabel, "value", "vm.selectedShape.shapeType", null, null, null, null, null);

		Row pointsRow = new Row();
		pointsRow.appendChild(new Label("Координаты"));
		Label pointsLabel = new Label();
		pointsRow.appendChild(pointsLabel);
		rows.appendChild(pointsRow);
		binder.addPropertyLoadBindings(pointsLabel, "value", "vm.selectedShape.points", null, null, null, null, null);

//		String[] beforeCommand = {"create"};

		Div div = new Div();
		div.setHflex("true");
		Image image = new Image("../resources/image/image1.png");
		image.setStyle("margin: 20px");
		image.setHeight("800");
		image.setWidth("800");
		div.appendChild(image);
		form.appendChild(div);

		form.appendChild(buildToolbar(binder));
		return form;
	}

	@Override
	public void init(RichletConfig config) {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	private Component buildSearchBar(Binder binder) {
		Div div = new Div();

		Html tittle = new Html("Поиск фигур:");
		div.appendChild(tittle);

		Textbox textbox = new Textbox();
		textbox.setPlaceholder("Введите текст...");
		div.appendChild(textbox);

		Button searchButton = new Button("Поиск");

		binder.addPropertySaveBindings(textbox, "value", "vm.keyword", null, null, null, null, null, null, null);
		binder.addCommandBinding(searchButton, Events.ON_CLICK, "'search'", null);
		binder.addCommandBinding(textbox, Events.ON_OK, "'search'", null);

		div.appendChild(searchButton);
		setStyleForAll("margin: 10px", div, tittle, textbox, searchButton);
		return div;
	}

	private Toolbar buildToolbar(Binder binder) {
		Toolbar toolbar = new Toolbar();

		toolbar.appendChild(buildDiv(binder, "Повернуть на:", "Повернуть", "vm.rotateAngle", "'rotateShape'", "empty vm.rotateAngle"));
		toolbar.appendChild(buildDiv(binder, "Увеличить в:", "Увеличить", "vm.increaseScale", "'increaseShape'", "empty vm.increaseScale"));
		toolbar.appendChild(buildDiv(binder, "Уменьшить в:", "Уменьшить", "vm.reduceScale", "'reduceShape'", "empty vm.reduceScale"));

		Div moveShapeDiv = new Div();
		Label label = new Label("Переместить");
		moveShapeDiv.appendChild(label);
		Doublebox moveX = new Doublebox();
		moveShapeDiv.appendChild(moveX);
		Doublebox moveY = new Doublebox();
		moveShapeDiv.appendChild(moveY);
		Button moveBtn = new Button("Переместить");
		moveShapeDiv.appendChild(moveBtn);
		toolbar.appendChild(moveShapeDiv);

		binder.addPropertyLoadBindings(moveX, "value", "vm.moveX", null, null, null, null, null);
		binder.addPropertySaveBindings(moveX, "value", "vm.moveX", null, null, null, null, null, null, null);
		binder.addPropertyLoadBindings(moveY, "value", "vm.moveY", null, null, null, null, null);
		binder.addPropertySaveBindings(moveY, "value", "vm.moveY", null, null, null, null, null, null, null);
		binder.addCommandBinding(moveBtn, Events.ON_CLICK, "'moveShape'", null);
		binder.addPropertyLoadBindings(moveBtn, "disabled", "empty vm.moveX || empty vm.moveY", null, null, null, null, null);

		// TODO кнопка удалить добавить (зачем удалил? делал же...)
		setStyleForAll("margin: 10px", moveShapeDiv, label, moveX, moveY, moveBtn);
		return toolbar;
	}

	private Div buildDiv(Binder binder, String textLabel, String textBtn, String saveExpr, String commandExpr, String loadExpr) {
		Div div = new Div();
		Label label = new Label(textLabel);
		label.setWidth("150px");
		div.appendChild(label);
		Doublebox doublebox = new Doublebox();
		doublebox.setWidth("100px");
		div.appendChild(doublebox);
		Button button = new Button(textBtn);
		button.setWidth("150px");
		div.appendChild(button);

		setStyleForAll("margin: 10px", div, label, doublebox, button);

		binder.addPropertyLoadBindings(doublebox, "value", saveExpr, null, null, null, null, null);
		binder.addPropertySaveBindings(doublebox, "value", saveExpr, null, null, null, null, null, null, null);
		binder.addCommandBinding(button, Events.ON_CLICK, commandExpr, null);
		binder.addPropertyLoadBindings(button, "disabled", loadExpr, null, null, null, null, null);
		return div;
	}

	private void setStyleForAll(String style, XulElement... elements) {
		Arrays.stream(elements).forEach(element -> element.setStyle(style));
	}

	private Component buildShapeListBox(Binder binder) {
		Listbox listbox = new Listbox();
		listbox.setHflex("true");
		listbox.setRows(10);
		listbox.setEmptyMessage("Нет фигур по запросу");

		Listhead head = new Listhead();
		listbox.appendChild(head);
		head.setSizable(true);
		head.appendChild(new Listheader("ID"));
		head.appendChild(new Listheader("Фигура"));
		head.appendChild(new Listheader("Центр"));

		binder.addPropertyInitBinding(listbox, "model", "vm.shapes", null, null, null);
		binder.addPropertyLoadBindings(listbox, "selectedItem", "vm.selectedShape", null, null, null, null, null);
		binder.addPropertySaveBindings(listbox, "selectedItem", "vm.selectedShape", null, null, null, null, null, null, null);

		listbox.setTemplate("model", new ListboxTemplate());
		return listbox;
	}

	private class ListboxTemplate implements Template {

		@Override
		public Component[] create(Component parent, Component insertBefore, VariableResolver resolver, Composer composer) {
			Listitem listitem = new Listitem();

			Listcell idCell = new Listcell();
			listitem.appendChild(idCell);
			binder.addPropertyLoadBindings(idCell, "label", "item.id", null, null, null, null, null);

			Listcell shapeType = new Listcell();
			listitem.appendChild(shapeType);
			binder.addPropertyLoadBindings(shapeType, "label", "item.shapeType", null, null, null, null, null);

			Listcell center = new Listcell();
			listitem.appendChild(center);
			binder.addPropertyLoadBindings(center, "label", "item.center", null, null, null, null, null);

			if (insertBefore == null) {
				parent.appendChild(listitem);
			} else {
				parent.insertBefore(listitem, insertBefore);
			}

			Component[] components = new Component[1];
			components[0] = listitem;

			return components;
		}

		@Override
		public Map<String, Object> getParameters() {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("var", "item");
			return parameters;
		}
	}
}
