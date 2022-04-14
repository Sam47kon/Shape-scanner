package com.app.mvvm;

import lombok.Data;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

@Data
public class LoginViewModel {

	private String name;
	private String password;

	private LoginService loginService;

	@Init
	public void init() {
		loginService = new LoginService();
	}

	@Command
	@NotifyChange({"name", "password"})
	public void authorize() {
		if (loginService.authorize(name, password)) {
			Executions.sendRedirect("zk/test/shapes");
		} else {
			Messagebox.show("Неверный логин или пароль", "Ошибка авторизации", 0, Messagebox.ERROR);
		}
	}
}
