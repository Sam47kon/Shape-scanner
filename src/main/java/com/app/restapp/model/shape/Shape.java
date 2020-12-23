package com.app.restapp.model.shape;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.circle.Circle;
import com.app.restapp.model.shape.interfaces.ITransformable;
import com.app.restapp.model.shape.polygon.Polygon;
import com.app.restapp.model.shape.polygon.quadrangular.Quadrangular;
import com.app.restapp.model.shape.polygon.triangle.Triangle;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "shapes")
@QueryEntity
@Data
public abstract class Shape implements ITransformable, Serializable {
	private static final long serialVersionUID = 1L;
	@Field("points")
	protected List<Point> points;
	@Field("center")
	protected Point center;
	@Field("shapeType")
	protected ShapeType shapeType;
	@Transient
	@Setter(value = AccessLevel.NONE)
	protected double area;
	@Id
	private String id;

	{
		points = new ArrayList<>();
	}

	public Shape() {
	}

	public Shape(List<Point> points) {
	}

	/**
	 * Найти площадь фигуры
	 */
	protected abstract double calcArea();

	public double getArea() {
		return area == 0 ? calcArea() : area;
	}

	public String convertPointsToString() {
		StringJoiner sj = new StringJoiner("; ");
		for (Point point : points) {
			sj.add(point.toString());
		}
		return sj.toString();
	}


	@Override
	public String toString() {
		return shapeType.toString() + "\n\tКоординаты: " + points + " Центральная точка: " + center;
	}
}
