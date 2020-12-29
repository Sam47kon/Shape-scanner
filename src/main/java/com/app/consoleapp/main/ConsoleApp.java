package com.app.consoleapp.main;

import com.app.consoleapp.helper.ShapeCreateHelper;

public class ConsoleApp {

	public static final String FILE_NAME = "template.txt";

	private static void startMenu() {
		Menu menu = new Menu(ShapeCreateHelper.getShapesByFile(FILE_NAME), FILE_NAME);
		menu.start();
	}
}
