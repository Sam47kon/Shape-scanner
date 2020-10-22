package com.app.model.figure.polygon.quadrangular;

import com.app.model.figure.ShapeType;
import com.app.model.point.Point2D;

import java.util.List;
import java.util.Locale;

import static com.app.util.Utils.*;

public final class Square extends Quadrangular {

	public Square(List<Point2D> points) {
		copyPoints(this.points, points);
		center = calcMidPoint(this.points);
		shapeType = ShapeType.SQUARE;
		side1 = side2 = side3 = side4 = calcDistance(pointA, pointB);
		diagonal1_3 = diagonal2_4 = calcDistance(pointA, pointC);
		angleA = angleB = angleC = angleD = 90D;
	}

	@Override
	protected double calcArea() {
		area = Math.pow(side1, 2);
		return area;
	}

	@Override
	public String toString() {
		return shapeType.toString() + "\n\tКоординаты: " + points + " Центральная точка: " + center
				+ String.format(Locale.ENGLISH, "\n\tСтороны=%1$.2f; Диагонали=%2$.2f", side1, diagonal1_3);
	}
}
