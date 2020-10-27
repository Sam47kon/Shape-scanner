package com.app.model.shape;

import com.app.model.point.Point;
import com.app.model.shape.interfaces.iTransformable;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String toString() {
		return shapeType.toString() + "\n\tКоординаты: " + points + " Центральная точка: " + center;
	}
}
