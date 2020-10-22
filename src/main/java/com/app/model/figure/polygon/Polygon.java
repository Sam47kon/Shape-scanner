package com.app.model.figure.polygon;

import com.app.model.figure.Shape;
import com.app.model.point.Point2D;

import java.util.List;

import static com.app.util.Utils.*;

public class Polygon extends Shape<Point2D> {
	protected double side1;
	protected double side2;
	protected double side3;
	protected double angleA;
	protected double angleB;
	protected double angleC;
	protected Point2D pointA;
	protected Point2D pointB;
	protected Point2D pointC;

	protected Polygon() {
	}

	public Polygon(List<Point2D> points) {
		copyPoints(this.points, points);
		this.pointA = this.points.get(0);
		this.pointB = this.points.get(1);
		this.pointC = this.points.get(2);

		this.side1 = calcDistance(pointA, pointB);
		this.side2 = calcDistance(pointB, pointC);
	}

	// TODO Доделать площадь для многоугольника
	@Override
	protected double calcArea() {
		System.out.println("TODO Доделать площадь для многоугольника");
		return 0;
	}

	@Override
	public void move(Point2D point) {
		movePoints(points, point);
		movePointByPoint(center, point);
	}

	@Override
	public void rotate(double angle) {
		rotateShape(points, center, angle);
	}

	@Override
	public void increase(double factor) {
		scaleShape(points, center, factor);
		area = calcArea();
		side1 *= factor;
		side2 *= factor;
		side3 *= factor;
	}

	@Override
	public void reduce(double factor) {
		increase(1 / factor);
	}
}
