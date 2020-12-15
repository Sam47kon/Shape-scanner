package com.app.model.shape;

import com.app.model.point.Point;
import com.app.model.shape.circle.Circle;
import com.app.model.shape.interfaces.ITransformable;
import com.app.model.shape.polygon.Polygon;
import com.app.model.shape.polygon.quadrangular.Quadrangular;
import com.app.model.shape.polygon.triangle.Triangle;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Circle.class, name = "Circle"),
		@JsonSubTypes.Type(value = Polygon.class, name = "Polygon"),
		@JsonSubTypes.Type(value = Quadrangular.class, name = "Quadrangular"),
		@JsonSubTypes.Type(value = Triangle.class, name = "Triangle")
})
public abstract class Shape<T extends Point> implements ITransformable<T>, Serializable {
	private static final long serialVersionUID = 1L;
	protected final List<T> points;
	protected T center;
	protected ShapeType shapeType;
	protected double area;

	{
		points = new ArrayList<>();
	}

	public Shape() {
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
