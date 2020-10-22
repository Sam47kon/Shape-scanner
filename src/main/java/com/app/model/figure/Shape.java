package com.app.model.figure;

import com.app.model.figure.interfaces.iMovable;
import com.app.model.figure.interfaces.iRotatable;
import com.app.model.figure.interfaces.iScalable;
import com.app.model.point.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class Shape<T extends Point> implements iMovable<T>, iRotatable, iScalable {
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
