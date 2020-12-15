package com.app.model.shape.polygon.quadrangular;

import com.app.model.point.Point2D;
import com.app.model.shape.ShapeType;
import com.app.model.shape.polygon.Polygon;

import java.util.List;
import java.util.Locale;

import static com.app.util.Utils.calcAngle;
import static com.app.util.Utils.calcDistance;

/**
 * Четырехугольная несамопересекающаяся фигура
 */
public final class Quadrangular extends Polygon {
	protected double side4;
	protected double diagonal1_3; // Диагональ1
	protected double diagonal2_4; // Диагональ2
	protected double angleD;
	protected Point2D pointD;

	public Quadrangular(List<Point2D> points) {
		super(points);
		this.pointD = this.points.get(3);

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

	public Quadrangular() {
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
				"\n\tСторона1=%1$.2f, Сторона2=%2$.2f, Сторона3=%3$.2f, Сторона4=%4$.2f; Диагональ1=%5$.2f, Диагональ2=%6$.2f" +
						"\n\tУгол1=%7$.2f°, Угол2=%8$.2f°, Угол3=%9$.2f°, Угол4=%10$.2f°",
				side1, side2, side3, side4, diagonal1_3, diagonal2_4, angleA, angleB, angleC, angleD);
	}
}
