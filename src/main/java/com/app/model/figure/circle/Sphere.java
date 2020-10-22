package com.app.model.figure.circle;

import com.app.model.figure.ShapeType;
import com.app.model.point.Point3D;

import java.util.List;

import static com.app.util.Utils.*;

public final class Sphere extends RoundShape<Point3D> {

	public Sphere(List<Point3D> points, double radius) {
		super(points, radius);
		this.shapeType = ShapeType.SPHERE;
	}

	public Sphere(List<Point3D> points) {
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
	protected Point3D createCenter(List<Point3D> points) {
		return calcMidPoint3D(points);
	}

	@Override
	protected double calcRadius(Point3D pointA, Point3D pointB) {
		return calcDistance3D(pointA, pointB);
	}

	@Override
	public void move(Point3D point) {
		movePointByPoint3D(center, point);
	}
}
