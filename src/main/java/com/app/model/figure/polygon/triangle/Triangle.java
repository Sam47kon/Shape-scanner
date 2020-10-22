package com.app.model.figure.polygon.triangle;

import com.app.model.figure.ShapeType;
import com.app.model.figure.polygon.Polygon;
import com.app.model.point.Point2D;

import java.util.List;

import static com.app.util.Utils.calcAngle;
import static com.app.util.Utils.calcMidPoint;

/**
 * Треугольник
 */
public final class Triangle extends Polygon {

	public Triangle(List<Point2D> points) {
		super(points);
		center = calcMidPoint(this.points);
		shapeType = ShapeType.TRIANGLE;
		angleA = calcAngle(pointA, pointB, pointC);
		angleB = calcAngle(pointB, pointA, pointC);
		angleC = 180 - angleA - angleB;
	}

	@Override
	protected double calcArea() {
		Point2D a = points.get(0);
		Point2D b = points.get(1);
		Point2D c = points.get(2);
		area = 0.5 * Math.abs((b.getX() - a.getX()) * (c.getY() - a.getY())
				- (c.getX() - a.getX()) * (b.getY() - a.getY()));
		return area;
	}
}
