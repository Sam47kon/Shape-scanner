package com.app.model;

import lombok.Data;

import java.util.List;

@Data
public class Shape {

	private String id;
	private ShapeType shapeType;
	private Point center;
	private List<Point> points;

	public Shape() {
	}

	public Shape(String id, List<Point> points, Point center, ShapeType shapeType) {
		this.id = id;
		this.points = points;
		this.center = center;
		this.shapeType = shapeType;
	}
}
