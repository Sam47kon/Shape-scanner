package com.app.model.figure.circle;

import com.app.model.figure.ShapeType;
import com.app.model.point.Point2D;

import java.util.List;

import static com.app.util.Utils.*;

public final class Circle extends RoundShape<Point2D> {

	public Circle(List<Point2D> points, double radius) {
		super(points, radius);
		this.shapeType = ShapeType.CIRCLE;
	}

	public Circle(List<Point2D> points) {
		super(points);
		this.shapeType = ShapeType.CIRCLE;
	}

	/**
	 * Площадь окружности
	 * S = π * r^2
	 * где r - радиус
	 */
	@Override
	protected double calcArea() {
		area = Math.PI * Math.pow(radius, 2);
		return area;
	}

	@Override
	protected Point2D createCenter(List<Point2D> points) {
		return calcMidPoint(points);
	}

	@Override
	protected double calcRadius(Point2D pointA, Point2D pointB) {
		return calcDistance(pointA, pointB);
	}

	@Override
	public void move(Point2D point) {
		movePointByPoint(center, point);
	}
}
