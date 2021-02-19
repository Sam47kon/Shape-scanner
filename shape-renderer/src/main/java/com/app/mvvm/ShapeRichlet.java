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
		window.setWidth("800px");
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
		vbox.appendChild(buildToolbar(binder));
		vbox.appendChild(buildFormArea(binder));

//		// Фигуры
//		Div divSearch = new Div();
//		divSearch.setParent(window);
//
//		Html searchTitle = new Html("Поиск фигур");
//		searchTitle.setParent(divSearch);
//
//		Textbox textboxSearch = new Textbox();
//		textboxSearch.setPlaceholder("Введите текст...");
//		textboxSearch.setAttribute("value", "@bind(vm.keyword)");
//		textboxSearch.setParent(divSearch);
//
//		Button buttonSearch = new Button("Поиск");
//		buttonSearch.setAttribute("onClick", "@command('search')");
//		buttonSearch.setParent(divSearch);
//
//		Div divTable = new Div();
//		divTable.setParent(window);
//
//
//		Hlayout hlayoutSelItem = new Hlayout();
//		hlayoutSelItem.setStyle("margin-top:20px");
//		hlayoutSelItem.setWidth("100%");
//		hlayoutSelItem.setParent(divTable);
//
//		Vlayout vlayout = new Vlayout();
////		vlayout.setHflex("true");
//		vlayout.setParent(hlayoutSelItem);
//
//		Label labelSelectedShape = new Label("@load(vm.selectedShape.points)");
//		labelSelectedShape.setParent(vlayout);
//
//		Div actionDiv = new Div();
//		actionDiv.setParent(window);
//
//		Html actionTitle = new Html("Действия с выбранной фигурой");
//		actionTitle.setParent(actionDiv);

		binder.loadComponent(window, true);
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
		Column labelCol = new Column();
		labelCol.setWidth("120px");
		columns.appendChild(labelCol);
		columns.appendChild(new Column());
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
		Textbox shapeTypeBox = new Textbox();
		shapeTypeRow.appendChild(shapeTypeBox);
		rows.appendChild(shapeTypeRow);

		String[] beforeCommand = {"save"};
		binder.addPropertyLoadBindings(shapeTypeBox, "value", "vm.selectedShape.shapeType", null, null, null, null, null);
		binder.addPropertySaveBindings(shapeTypeBox, "value", "vm.selectedShape.shapeType", beforeCommand, null, null, null, null, null, null);

		Listbox listbox = new Listbox();
		listbox.setHflex("true");
		listbox.setRows(5);
		form.appendChild(listbox);
		Listhead head = new Listhead();
		listbox.appendChild(head);
		head.appendChild(new Listheader("X"));
		head.appendChild(new Listheader("Y"));

		// FIXME не отображаются x y
		binder.addPropertyInitBinding(listbox, "model", "vm.selectedShape.points", null, null, null);
		binder.addPropertyLoadBindings(listbox, "selectedPoint", "vm.selectedPoint", null, null, null, null, null);
		binder.addPropertySaveBindings(listbox, "selectedPoint", "vm.selectedPoint", null, null, null, null, null, null, null);

		listbox.setTemplate("model", new ListboxPointsTemplate());
// TODO остановился здесь
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
		return div;
	}

	private Toolbar buildToolbar(Binder binder) {
		Button newButton = new Button("Создать");
		Button saveButton = new Button("Сохранить");
		Button updateButton = new Button("Изменить");
		Button deleteButton = new Button("Удалить");

		Toolbar toolbar = new Toolbar();
		toolbar.appendChild(newButton);
		toolbar.appendChild(saveButton);
		toolbar.appendChild(updateButton);
		toolbar.appendChild(deleteButton);

		binder.addCommandBinding(newButton, Events.ON_CLICK, "'newShape'", null);

		binder.addCommandBinding(saveButton, Events.ON_CLICK, "'saveShape'", null);
		binder.addPropertyLoadBindings(saveButton, "disabled", "empty vm.selectedShape", null, null, null, null, null);

		binder.addCommandBinding(updateButton, Events.ON_CLICK, "'updateShape'", null);
		binder.addPropertyLoadBindings(updateButton, "disabled", "empty vm.selectedShape", null, null, null, null, null);

		binder.addCommandBinding(deleteButton, Events.ON_CLICK, "empty vm.selectedShape.id?'deleteShape':'confirmDelete'", null);
		binder.addPropertyLoadBindings(deleteButton, "disabled", "empty vm.selectedShape", null, null, null, null, null);

		return toolbar;
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

	private class ListboxPointsTemplate implements Template {

		@Override
		public Component[] create(Component parent, Component insertBefore, VariableResolver resolver, Composer composer) {
			Listitem listitem = new Listitem();

			Listcell x = new Listcell();
			listitem.appendChild(x);
			binder.addPropertyLoadBindings(x, "label", "item.x", null, null, null, null, null);

			Listcell y = new Listcell();
			listitem.appendChild(y);
			binder.addPropertyLoadBindings(y, "label", "item.x", null, null, null, null, null);

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
