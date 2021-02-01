package com.app;

import lombok.Data;

import java.util.List;

@Data
public class Shape {

	private String id;
	private String shapeType;
	private Point center;
	private List<Point> points;

	public Shape() {
	}

	public Shape(String id, List<Point> points, Point center, String shapeType) {
		this.points = points;
		this.center = center;
		this.shapeType = shapeType;
	}
}
