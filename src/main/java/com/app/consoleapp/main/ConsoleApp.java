package com.app.consoleapp.main;

import com.app.consoleapp.helper.ShapeCreateHelper;

public class ConsoleApp {

	private static void startMenu() {
		final String FILE_NAME = "template.txt";
		Menu menu = new Menu(ShapeCreateHelper.getShapesByFile(FILE_NAME), FILE_NAME);
		menu.start();
	}
}
