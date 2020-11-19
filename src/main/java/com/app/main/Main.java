package com.app.main;

import com.app.helper.ShapeCreateHelper;

// TODO апгрейд до rest + firebase + отрисовка фигур
public class Main {
	private static final String FILE_NAME = "template.txt";

	public static void main(String[] args) {
		Menu menu = new Menu(ShapeCreateHelper.getShapesByFile(FILE_NAME), FILE_NAME);
		menu.start();
	}
}
