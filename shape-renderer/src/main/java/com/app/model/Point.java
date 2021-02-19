package com.app.model;

import lombok.Data;

import static com.app.util.DecimalFormatter.DEC_FORMAT;

@Data
public class Point {
	private double x;
	private double y;

	public Point() {
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("x=%s, y=%s", DEC_FORMAT.format(x), DEC_FORMAT.format(y));
	}
}
