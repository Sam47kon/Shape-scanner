package com.app.main;

import com.app.helper.ShapeCreateHelper;
import com.app.model.figure.Shape;
import com.app.model.point.Point2D;

import java.util.List;

public class Main {
	private static final String FILE_NAME = "template1.txt";

	public static void main(String[] args) {
		System.out.println("Добро пожаловать в приложение!");

		List<Shape<Point2D>> shapes = ShapeCreateHelper.getShapesByFile(FILE_NAME);
		System.out.println("Текстовый файл с координатами фигур успешно загружен." +
				"\n\tДоступно " + shapes.size() + " фигур");
		Application.start(shapes);
	}
}
