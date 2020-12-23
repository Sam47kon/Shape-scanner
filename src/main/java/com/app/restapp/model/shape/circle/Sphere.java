package com.app.restapp.model.shape.circle;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point3D;
import com.app.restapp.model.shape.ShapeType;

import java.util.List;

import static com.app.util.Utils.*;

public final class Sphere extends RoundShape {

	public Sphere(List<Point> points, double radius) {
		super(points, radius);
		this.shapeType = ShapeType.SPHERE;
	}

	public Sphere(List<Point> points) {
		super(points);
		this.shapeType = ShapeType.SPHERE;
	}

	/**
	 * Площадь поверхности сферы
	 * S = π * d^2
	 * где d - диаметр
	 */
	@Override
	protected double calcArea() {
		area = Math.PI * Math.pow(radius, 4);
		return area;
	}

	@Override
	protected Point3D createCenter(List<Point> points) {
		return calcMidPoint3D(points);
	}

	@Override
	protected double calcRadius(Point pointA, Point pointB) {
		return calcDistance3D(pointA, pointB);
	}

	@Override
	protected void copyPoints(List<Point> fromPoints) {
		copyPoints3D(this.points, fromPoints);
	}

	@Override
	public void move(Point point) {
		movePointByPoint3D(center, point);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
