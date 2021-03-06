package com.app.consoleapp.helper;

import com.app.consoleapp.factory.*;
import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.ShapeType;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShapeCreateHelper {

	private static int LINE_NUM = 1;

	public static int moveToNextLine() {
		return LINE_NUM++;
	}

	public static TreeMap<String, Shape> getShapesByFile(String fileName) {
		TreeMap<String, Shape> shapes = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));
		IShapeFactory factory;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while (reader.ready()) {
				line = reader.readLine();
				List<Point> points = definePoints(line, true);
				if (points.size() > 1) {
					factory = getShapeFactory(points);
					shapes.put(String.valueOf(LINE_NUM), factory.createShape(points));
				}
				LINE_NUM++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Файл не найден");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shapes;
	}

	public static List<Point> createPointFromInput() {
		List<Point> points = new ArrayList<>();
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


	private static List<Point> definePoints(String line, boolean displayed) {
		String[] points = line.split(";");
		List<Point> listPoints = new ArrayList<>();
		double x;
		double y;
		for (String point : points) {
			point = point.replaceAll("[A-zА-я]|[\\[\\](){}]|=", "").trim().replace(",", " ");
			try (Scanner scanner = new Scanner(point)) {
				scanner.useLocale(Locale.US);
				if (scanner.hasNextDouble()) {
					x = scanner.nextDouble();

					y = scanner.nextDouble();
					listPoints.add(new Point2D(x, y));
				}
			} catch (InputMismatchException e) {
				if (displayed) System.out.println("В строке №" + LINE_NUM + " - произошла ошибка при считывании");
				return listPoints;
			} catch (NoSuchElementException e) {
				if (displayed) System.out.println("В строке №" + LINE_NUM + " - нет пар координат!");
				return listPoints;
			}
		}
		int pointSize = listPoints.size();
		if (pointSize < 2 && displayed) {
			System.out.println("В строке №" + LINE_NUM + " - " + pointSize
					+ (pointSize == 0 ? " координат" : " координата")
					+ ". Строка пропускается");
		}
		return listPoints;
	}

	public static IShapeFactory getShapeFactory(@NotNull List<Point> points) {
		IShapeFactory factory;
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

	public static IShapeFactory getShapeFactory(ShapeType shapeType) {
		IShapeFactory factory;
		switch (shapeType) {
			case QUADRANGULAR:
			case SQUARE:
			case RHOMBUS:
			case RECTANGLE:
			case PARALLELOGRAM:
				factory = new QuadrangularFactory();
				break;
			case CIRCLE:
				factory = new CircleFactory();
				break;
			case TRIANGLE:
				factory = new TriangleFactory();
				break;
			default:
				factory = new PolygonFactory();
				break;
		}
		return factory;
	}

	public static boolean isShapeInLine(String line) {
		return definePoints(line, false).size() > 1;
	}
}
