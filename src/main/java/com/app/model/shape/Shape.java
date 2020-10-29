package com.app.model.shape;

import com.app.model.point.Point;
import com.app.model.shape.interfaces.iTransformable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class Shape<T extends Point> implements iTransformable<T> {
	protected final List<T> points;
	protected T center;
	protected ShapeType shapeType;
	protected double area;

	{
		points = new ArrayList<>();
	}

	protected Shape() {
	}

	public Shape(List<T> points) {
	}

	/**
	 * Найти площадь фигуры
	 */
	protected abstract double calcArea();

	public double getArea() {
		return area == 0 ? calcArea() : area;
	}

	public ShapeType getShapeType() {
		return shapeType;
	}

	public String convertPointsToString() {
		StringJoiner sj = new StringJoiner("; ");
		for (T point : points) {
			sj.add(point.toString());
		}
		return sj.toString();
	}

	@Override
	public String toString() {
		return shapeType.toString() + "\n\tКоординаты: " + points + " Центральная точка: " + center;
	}
}
