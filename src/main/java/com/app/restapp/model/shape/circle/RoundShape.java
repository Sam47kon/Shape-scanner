package com.app.restapp.model.shape.circle;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.Shape;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Locale;

public abstract class RoundShape extends Shape {
	@Field("radius")
	protected double radius;

	/**
	 * Построить круглую фигуру с помощью центра и радиуса
	 *
	 * @param points - лист, в котором точка - центральная координата
	 * @param radius - радиус
	 */
	public RoundShape(List<Point> points, double radius) {
		this.center = createCenter(points);
		this.points.add(this.center);
		this.radius = radius;
	}

	/**
	 * Построить Окружность с помощью двух координат
	 *
	 * @param points - лист с двумя координатами
	 */
	public RoundShape(List<Point> points) {
		this.center = createCenter(points);
		copyPoints(points);
		this.radius = calcRadius(points.get(0), points.get(1));
	}

	public RoundShape() {
	}

	public double getRadius() {
		return radius;
	}

	protected abstract Point createCenter(List<Point> points);

	protected abstract double calcRadius(Point pointA, Point pointB);

	protected abstract void copyPoints(List<Point> fromPoints);

	@Override
	public void rotate(double angle) {
		System.out.println("Seriously?");
	}

	@Override
	public void increase(double factor) {
		radius *= factor;
	}

	@Override
	public void reduce(double factor) {
		radius /= factor;
	}

	@Override
	public String toString() {
		return shapeType.toString() + ". Центральная точка: " + center + ", радиус=" + String.format(Locale.ENGLISH, "%1$,.2f", radius);
	}
}
