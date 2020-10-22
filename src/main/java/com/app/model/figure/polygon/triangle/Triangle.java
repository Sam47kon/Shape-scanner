package com.app.model.figure.polygon.triangle;

import com.app.model.figure.ShapeType;
import com.app.model.figure.polygon.Polygon;
import com.app.model.point.Point2D;

import java.util.List;
import java.util.Locale;

import static com.app.util.Utils.calcAngle;

/**
 * Треугольник
 */
public final class Triangle extends Polygon {

	public Triangle(List<Point2D> points) {
		super(points);
		shapeType = ShapeType.TRIANGLE;
		angleA = calcAngle(pointA, pointB, pointC);
		angleB = calcAngle(pointB, pointA, pointC);
		angleC = 180 - angleA - angleB;
	}

	@Override
	public String toString() {
		return super.toString() + String.format(Locale.ENGLISH,
				"\n\tСторона1=%1$.2f, Сторона2=%2$.2f, Сторона3=%3$.2f\n\tУгол1=%4$.2f°, Угол2=%5$.2f°, Угол3=%6$.2f°",
				side1, side2, side3, angleA, angleB, angleC);
	}
}
