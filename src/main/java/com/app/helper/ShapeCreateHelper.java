package com.app.helper;

import com.app.factory.*;
import com.app.model.figure.Shape;
import com.app.model.point.Point2D;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ShapeCreateHelper {
	public static List<Shape<Point2D>> getShapesByFile(String fileName) {
		List<Shape<Point2D>> shapes = new ArrayList<>();
		iShapeFactory<Point2D> factory;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while (reader.ready()) {
				line = reader.readLine();
				List<Point2D> points = definePoints(line.split(";"));
				factory = getShapeFactory(points);
				shapes.add(factory.createShape(points));

			}
		} catch (FileNotFoundException e) {
			System.out.println("Файл не найден");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shapes;
	}


	private static List<Point2D> definePoints(String[] values) {
		List<Point2D> points = new ArrayList<>();
		double x;
		double y;
		for (String value : values) {
			value = value.replaceAll("[A-zА-я]|[\\[\\](){}]", "").trim();
			try (Scanner scanner = new Scanner(value)) {
				x = scanner.nextDouble();
				y = scanner.nextDouble();
				points.add(new Point2D(x, y));
			} catch (InputMismatchException e) {
				System.out.println("Сканер не распознал координаты");
			}
		}
		return points;
	}

	private static iShapeFactory<Point2D> getShapeFactory(@NotNull List<Point2D> points) {
		iShapeFactory<Point2D> factory;
		switch (points.size()) {
			case 0:
				System.out.println("Фигура не имеет координат!");
			case 2:
				factory = new CircleFactory();
				break;
			case 3:
				factory = new TriangleFactory();
				break;
			case 4:
				factory = new QuadrangularFactory();
				break;
			default:
				factory = new PolygonFactory();
		}
		return factory;
	}
}
