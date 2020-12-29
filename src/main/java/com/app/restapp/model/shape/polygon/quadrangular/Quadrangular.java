package com.app.restapp.model.shape.polygon.quadrangular;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.polygon.Polygon;
import com.app.util.Utils;

import java.util.List;

/**
 * Четырехугольная несамопересекающаяся фигура
 */
public final class Quadrangular extends Polygon {

	public Quadrangular(List<Point> points) {
		super(points);
		shapeType = Utils.define(points);
	}

	public Quadrangular() {
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
