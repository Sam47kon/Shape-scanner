package com.app.model.figure.polygon.quadrangular;

import com.app.model.figure.ShapeType;
import com.app.model.figure.polygon.Polygon;
import com.app.model.point.Point2D;

import java.util.List;
import java.util.Locale;

import static com.app.util.Utils.*;

/**
 * Четырехугольная несамопересекающаяся фигура
 */
public class Quadrangular extends Polygon {
	protected double side4;
	protected double diagonal1_3; // Диагональ1
	protected double diagonal2_4; // Диагональ2
	protected double angleD;
	protected Point2D pointD;

	protected Quadrangular() {
	}

	public Quadrangular(List<Point2D> points) {
		super(points);
		this.pointD = this.points.get(3);
		center = calcMidPoint(this.points);

		side3 = calcDistance(pointC, pointD);
		side4 = calcDistance(pointD, pointA);
		diagonal1_3 = calcDistance(pointA, pointC);
		diagonal2_4 = calcDistance(pointB, pointD);

		angleA = calcAngle(pointA, pointB, pointD);
		angleB = calcAngle(pointB, pointA, pointC);
		angleC = calcAngle(pointC, pointB, pointD);
		angleD = 360 - angleA - angleB - angleC;
		shapeType = define(side1, side2, side3, side4, angleA);
	}

	private static ShapeType define(double side1, double side2, double side3, double side4, double angle1) {
		if (side1 == side2 && side2 == side3) {
			if (angle1 == 90) {
				return ShapeType.SQUARE;
			} else return ShapeType.RHOMBUS;
		}
		if (side1 == side3 && side2 == side4) {
			if (angle1 == 90) {
				return ShapeType.RECTANGLE;
			} else return ShapeType.PARALLELOGRAM;
		}
		return ShapeType.QUADRANGULAR;
	}

	/**
	 * Площадь произвольного несамопересекающегося четырёхугольника, заданного на плоскости координатами своих вершин
	 * (x1,y1),(x2,y2),(x3,y3),(x4,y4) в порядке обхода, равна:
	 * S = |(x1-x2)(y1+y2) + (x2-x3)(y2+y3) + (x3-x4)(y3+y4) + (x4-x1)(y4+y1)| * 1/2
	 * https://ru.wikipedia.org/wiki/%D0%A7%D0%B5%D1%82%D1%8B%D1%80%D1%91%D1%85%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA#%D0%9F%D0%BB%D0%BE%D1%89%D0%B0%D0%B4%D1%8C
	 */
	@Override
	protected double calcArea() {
		Point2D a = points.get(0);
		Point2D b = points.get(1);
		Point2D c = points.get(2);
		Point2D d = points.get(3);
		area = 0.5 * Math.abs((a.getX() - b.getX()) * (a.getY() + b.getY())
				+ (b.getX() - c.getX()) * (b.getY() + c.getY())
				+ (c.getX() - d.getX()) * (c.getY() + d.getY())
				+ (d.getX() - a.getX()) * (d.getY() + a.getY()));
		return area;
	}


	@Override
	public void increase(double factor) {
		super.increase(factor);
		side4 *= factor;
		diagonal1_3 *= factor;
		diagonal2_4 *= factor;
	}

	@Override
	public String toString() {
		return super.toString() + String.format(Locale.ENGLISH,
				"\n\tSideAB=%1$.2f, SideBC=%2$.2f, SideCD=%3$.2f, SideDA=%4$.2f; diagonalAC=%5$.2f, diagonalBD=%6$.2f" +
						"\n\tAngleA=%7$.2f°, AngleB=%8$.2f°, AngleC=%9$.2f°, AngleD=%10$.2f°",
				side1, side2, side3, side4, diagonal1_3, diagonal2_4, angleA, angleB, angleC, angleD);
	}
}
