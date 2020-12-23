package com.app.util;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.point.Point3D;
import com.app.restapp.model.shape.ShapeType;
import com.sun.istack.internal.NotNull;

import java.util.List;

public final class Utils {

	private Utils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Рассчитывает улог в градусах по трем точкам (вершинам) с помощью Формулы вычисления угла между векторами
	 *
	 * @param pointAngle - вершина 1, угол которой необходимо найти;
	 * @param pointB     - вершина 2;
	 * @param pointC     - вершина 3.
	 *                   где: vectorAB - вектор, начинающийся с вершины 1, заканчивающийся в вершине 2;
	 *                   vectorAD - вектор, начинающийся с вершины 1, заканчивающийся в вершине 3.
	 * @return - угол в градусах
	 */
	public static double calcAngle(@NotNull Point pointAngle, @NotNull Point pointB, Point pointC) {
		Point vectorAB = calcVector(pointAngle, pointB);
		Point vectorAD = calcVector(pointAngle, pointC);
		return Math.toDegrees(Math.acos(
				(vectorAB.getX() * vectorAD.getX() + vectorAB.getY() * vectorAD.getY()) / (
						Math.sqrt(vectorAB.getX() * vectorAB.getX() + vectorAB.getY() * vectorAB.getY())
								* Math.sqrt(vectorAD.getX() * vectorAD.getX() + vectorAD.getY() * vectorAD.getY()))
		));
	}

	/**
	 * Рассчитывает вектор по двум точкам (вершинам)
	 *
	 * @param pointA - вершина 1
	 * @param pointB - вершина 2
	 * @return - вектор в виде координат
	 */
	public static Point calcVector(@NotNull Point pointA, @NotNull Point pointB) {
		return new Point2D(pointB.getX() - pointA.getX(), pointB.getY() - pointA.getY());
	}

	/**
	 * Рассчитывает длину отрезка AB, где:
	 *
	 * @param pointA - вершина A
	 * @param pointB - вершина B
	 * @return - длина отрезка
	 */
	public static double calcDistance(@NotNull Point pointA, @NotNull Point pointB) {
		return Math.sqrt(Math.pow(pointB.getX() - pointA.getX(), 2) + Math.pow(pointB.getY() - pointA.getY(), 2));
	}

	/**
	 * Рассчитывает длину отрезка AB в трехмерном пространстве, где:
	 *
	 * @param pointA - вершина A
	 * @param pointB - вершина B
	 * @return - длина отрезка
	 */
	public static double calcDistance3D(@NotNull Point pointA, @NotNull Point pointB) {
		return Math.sqrt(Math.pow(pointB.getX() - pointA.getX(), 2) + Math.pow(pointB.getY() - pointA.getY(), 2)
				+ Math.pow(((Point3D) pointB).getZ() - ((Point3D) pointA).getZ(), 2));
	}

	/**
	 * Рассчитывает середину фигуры, вершины которой соеденины последовательно между собой
	 *
	 * @param points - лист с вершинами
	 * @return - точка, значение которой есть центр тяжести фигуры
	 */
	public static Point2D calcMidPoint(List<Point> points) {
		double x = 0;
		double y = 0;
		for (Point point : points) {
			x += point.getX();
			y += point.getY();
		}
		return new Point2D(x / points.size(), y / points.size());
	}

	/**
	 * Рассчитывает середину фигуры, вершины которой соеденины последовательно между собой в трехмерном пространстве
	 *
	 * @param points - лист с вершинами
	 * @return - точка, значение которой есть центр тяжести фигуры
	 */
	public static Point3D calcMidPoint3D(List<Point> points) {
		double x = 0;
		double y = 0;
		double z = 0;
		for (Point point : points) {
			x += point.getX();
			y += point.getY();
			z += ((Point3D) point).getZ();
		}
		return new Point3D(x / points.size(), y / points.size(), z / points.size());
	}

	/**
	 * Смещяет каждую точку относительно себя листа на указанные значения координаты
	 *
	 * @param points  - лист с координатами
	 * @param byPoint - координата со значениями, на которые нужно сдвинуть
	 */
	public static void movePoints(List<Point> points, Point byPoint) {
		for (Point point : points) {
			movePointByPoint(point, byPoint);
		}
	}

	public static void movePointByPoint(Point point, Point byPoint) {
		point.setX(point.getX() + byPoint.getX());
		point.setY(point.getY() + byPoint.getY());
	}

	/**
	 * Перемещает каждую точку листа относительно себя на указанную координату в трехмерном пространстве
	 *
	 * @param points  - лист с координатами
	 * @param point3D - координата, на которую нужно сдвинуть
	 */
	public static void movePoints3D(List<Point3D> points, Point3D point3D) {
		for (Point3D point : points) {
			point.setX(point.getX() + point3D.getX());
			point.setY(point.getY() + point3D.getY());
			point.setZ(point.getZ() + point3D.getZ());
		}
	}

	public static void movePointByPoint3D(Point point, Point byPoint) {
		point.setX(point.getX() + byPoint.getX());
		point.setY(point.getY() + byPoint.getY());
		((Point3D) point).setZ(((Point3D) point).getZ() + ((Point3D) byPoint).getZ());
	}

	/**
	 * Поворачивает фигуру на указанный угол. Афинные преобразования
	 * X = x0 + (x - x0) * cos(a) - (y - y0) * sin(a);
	 * Y = y0 + (y - y0) * cos(a) + (x - x0) * sin(a);
	 *
	 * @param points - лист с вершинами фигуры
	 * @param angle  - угол в градусах, на который нужно повернуть
	 */
	public static void rotateShape(List<Point> points, Point center, double angle) {
		if (angle == 360 || angle == -360) {
			return;
		}
		if (points.size() == 4 && (angle == 180 || angle == -180)) {
			swapPoints(points.get(0), points.get(2));
			swapPoints(points.get(1), points.get(3));
			return;
		}
		double x;
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));
		for (Point point : points) {
			x = (point.getX() - center.getX()) * cos - (point.getY() - center.getY()) * sin + center.getX();
			point.setY((point.getX() - center.getX()) * sin + (point.getY() - center.getY()) * cos + center.getY());
			point.setX(x);
		}
	}

	private static void swapPoints(Point point1, Point point2) {
		Point2D tmp = new Point2D(point1.getX(), point1.getY());
		point1.setX(point2.getX());
		point1.setY(point2.getY());
		point2.setX(tmp.getX());
		point2.setY(tmp.getY());
	}

	/**
	 * Масштабирует фигуру, относительно её центра
	 *
	 * @param points - лист с вершинами фигуры
	 * @param center - центр фигуры
	 * @param scale  - множитель
	 */
	public static void scaleShape(List<Point> points, Point center, double scale) {
		for (Point point : points) {
			point.setX((point.getX() - center.getX()) * scale + center.getX());
			point.setY((point.getY() - center.getY()) * scale + center.getY());
		}
	}

	public static void copyPoints(List<Point> original, List<Point> source) {
		for (Point point : source) {
			original.add(new Point2D(point.getX(), point.getY()));
		}
	}

	public static void copyPoints3D(List<Point> original, List<Point> source) {
		for (Point point : source) {
			original.add(new Point3D(point.getX(), point.getY(), ((Point3D) point).getZ()));
		}
	}

	/**
	 * Определяет тип четырехугольника
	 *
	 * @param points - лист с точками
	 * @return тип четырехугольника
	 */
	public static ShapeType define(List<Point> points) {
		if (points.size() != 4) {
			throw new RuntimeException("Это не четырех угольник! Всего точек:" + points.size());
		}
		Point pointA = points.get(0);
		Point pointB = points.get(1);
		Point pointC = points.get(2);
		Point pointD = points.get(3);
		double angleA = calcAngle(pointA, pointB, pointD);
		double side1 = calcDistance(pointA, pointB);
		double side2 = calcDistance(pointB, pointC);
		double side3 = calcDistance(pointC, pointD);
		double side4 = calcDistance(pointD, pointA);

		if (side1 == side2 && side2 == side3) {
			if (angleA == 90) {
				return ShapeType.SQUARE;
			} else return ShapeType.RHOMBUS;
		}
		if (side1 == side3 && side2 == side4) {
			if (angleA == 90) {
				return ShapeType.RECTANGLE;
			} else return ShapeType.PARALLELOGRAM;
		}
		return ShapeType.QUADRANGULAR;
	}

	/**
	 * Метод позволяет получить время выполнения метода/блока кода
	 *
	 * @param runnable - необходимый метод/блок кода
	 * @return время выполнения в мс
	 */
	public static double getRuntime(Runnable runnable) {
		double startTime;
		double stopTime;
		startTime = System.nanoTime();
		runnable.run();
		stopTime = System.nanoTime();
		return (stopTime - startTime) / 1_000_000;
	}
}
