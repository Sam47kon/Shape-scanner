package com.app.restapp.model.point;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public final class Point2D extends Point {
	public Point2D(double x, double y) {
		super(x, y);
	}

	public Point2D() {
	}

	@Override
	public String toString() {
		return "(" + super.toString() + ")";
	}
}
