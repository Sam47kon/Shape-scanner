package com.app.model.shape.circle;

import com.app.model.point.Point;
import com.app.model.shape.Shape;

import java.util.List;
import java.util.Locale;

public abstract class RoundShape<T extends Point> extends Shape<T> {
	protected double radius;

	/**
	 * Построить круглую фигуру с помощью центра и радиуса
	 *
	 * @param points - лист, в котором точка - центральная координата
	 * @param radius - радиус
	 */
	public RoundShape(List<T> points, double radius) {
		this.center = createCenter(points);
		this.points.add(this.center);
		this.radius = radius;
	}

	/**
	 * Построить Окружность с помощью двух координат
	 *
	 * @param points - лист с двумя координатами
	 */
	public RoundShape(List<T> points) {
		this.center = createCenter(points);
		copyPoints(points);
		this.radius = calcRadius(points.get(0), points.get(1));
	}

	public double getRadius() {
		return radius;
	}

	protected abstract T createCenter(List<T> points);

	protected abstract double calcRadius(T pointA, T pointB);

	protected abstract void copyPoints(List<T> fromPoints);

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
