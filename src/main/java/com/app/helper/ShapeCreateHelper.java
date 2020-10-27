package com.app.helper;

import com.app.factory.*;
import com.app.model.point.Point2D;
import com.app.model.shape.Shape;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShapeCreateHelper {

	public static List<Shape<Point2D>> getShapesByFile(String fileName) {
		List<Shape<Point2D>> shapes = new ArrayList<>();
		iShapeFactory<Point2D> factory;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			int lineNum = 1;
			while (reader.ready()) {
				line = reader.readLine();
				List<Point2D> points = definePoints(line, lineNum);
				lineNum++;
				if (points.size() < 2) {
					continue;
				}
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

	public static List<Point2D> createPointFromInput() {
		List<Point2D> points = new ArrayList<>();
		boolean itContinues = true;
		double x;
		double y;
		while (itContinues || points.size() < 2) {
			x = ScannerHelper.getDoubleFromInput("Введите x: ");
			y = ScannerHelper.getDoubleFromInput("Введите y: ");
			points.add(new Point2D(x, y));
			itContinues = ScannerHelper.isYes("Ввести еще координату? ");
			if (points.size() < 2 && !itContinues) {
				System.out.println("Нужно ввести минимум 2 координаты");
			}
		}
		return points;
	}


	private static List<Point2D> definePoints(String line, int lineNum) {
		String[] values = line.split(";");
		List<Point2D> points = new ArrayList<>();
		double x;
		double y;
		for (String value : values) {
			value = value.replaceAll("[A-zА-я]|[\\[\\](){}]", "").trim();
			try (Scanner scanner = new Scanner(value)) {
				if (scanner.hasNextDouble()) {
					x = scanner.nextDouble();
					y = scanner.nextDouble();
					points.add(new Point2D(x, y));
				}
			} catch (InputMismatchException e) {
				System.out.println("Сканер не распознал координаты");
			} catch (NoSuchElementException e) {
				System.out.println("В строке №" + lineNum + " нет пар координат!");
			}
		}
		int pointSize = points.size();
		if (pointSize < 2) {
			System.out.println("В строке №" + lineNum + " - " + pointSize
					+ (pointSize == 0 ? " координат" : " координата")
					+ ". Строка пропускается");
		}
		return points;
	}

	public static iShapeFactory<Point2D> getShapeFactory(@NotNull List<Point2D> points) {
		iShapeFactory<Point2D> factory;
		switch (points.size()) {
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
