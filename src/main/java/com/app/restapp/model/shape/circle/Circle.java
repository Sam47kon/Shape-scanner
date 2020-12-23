package com.app.restapp.model.shape.circle;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.ShapeType;
import com.app.util.Utils;

import java.util.List;

import static com.app.util.Utils.*;

public final class Circle extends RoundShape {

	public Circle(List<Point> points, double radius) {
		super(points, radius);
		this.shapeType = ShapeType.CIRCLE;
	}

	public Circle(List<Point> points) {
		super(points);
		this.shapeType = ShapeType.CIRCLE;
	}

	public Circle() {
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
	protected Point createCenter(List<Point> points) {
		return calcMidPoint(points);
	}

	@Override
	protected double calcRadius(Point pointA, Point pointB) {
		return calcDistance(pointA, pointB);
	}

	@Override
	protected void copyPoints(List<Point> fromPoints) {
		Utils.copyPoints(this.points, fromPoints);
	}

	@Override
	public void move(Point point) {
		movePointByPoint(center, point);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
