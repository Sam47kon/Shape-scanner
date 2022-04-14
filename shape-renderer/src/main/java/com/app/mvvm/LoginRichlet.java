package com.app.mvvm;

import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;

public class LoginRichlet extends GenericRichlet {

	private Binder binder;

	@Override
	public void service(Page page) throws Exception {
		page.setTitle("Авторизация");
		//1.Create root component which is to be associated with binder
		final Window window = new Window("Добро пожаловать", "normal", false);
		window.setWidth("600px");
		window.setHeight("400px");
		window.setSizable(true);
		window.setPage(page);

		//2.Instantiate a binder instance. Use DefaultBinder
		binder = new DefaultBinder();
		//3. Initialize it with View model and root component
		binder.init(window, new LoginViewModel(), null);

		//4. Set binder as an attribute on the root component
		window.setAttribute("lg", binder.getViewModel());

		window.appendChild(buildLoginForm(binder));
	}

	private Vbox buildLoginForm(Binder binder) {
		Vbox vbox = new Vbox();
		vbox.setHflex("true");
		vbox.setAlign("center");

		Hbox hbox = new Hbox();
		hbox.setHflex("true");
		hbox.setAlign("center");
		vbox.appendChild(hbox);

		Div divUser = new Div();
		divUser.setAlign("right");
		vbox.appendChild(divUser);
		Label labelUser = new Label("Имя пользователя:");
		labelUser.setWidth("40%");
		divUser.appendChild(labelUser);
		Textbox textboxName = new Textbox();
		textboxName.setPlaceholder("Введите имя пользователя...");
		textboxName.setWidth("60%");
		divUser.appendChild(textboxName);

		Div divPassword = new Div();
		divPassword.setAlign("right");
		vbox.appendChild(divPassword);
		Label labelPassword = new Label("Пароль:");
		labelPassword.setWidth("40%");
		divPassword.appendChild(labelPassword);
		Textbox textBoxPassword = new Textbox();
		textBoxPassword.setPlaceholder("Введите пароль...");
		textBoxPassword.setWidth("60%");
		divPassword.appendChild(textBoxPassword);

		Div divButton = new Div();
		divButton.setAlign("center");
		vbox.appendChild(divButton);
		Button buttonLogin = new Button("Войти");
		divButton.appendChild(buttonLogin);

		binder.addPropertySaveBindings(textboxName, "value", "lg.name", null, null, null, null, null, null, null);
		binder.addPropertySaveBindings(textBoxPassword, "value", "lg.password", null, null, null, null, null, null, null);
		binder.addPropertyLoadBindings(textboxName, "value", "lg.name", null, null, null, null, null);
		binder.addPropertyLoadBindings(textBoxPassword, "value", "lg.password", null, null, null, null, null);
		binder.addCommandBinding(buttonLogin, Events.ON_CLICK, "'authorize'", null);
		return vbox;
	}
}
