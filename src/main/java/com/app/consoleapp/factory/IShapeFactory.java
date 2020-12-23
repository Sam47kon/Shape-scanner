package com.app.consoleapp.factory;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.Shape;

import java.util.List;

public interface IShapeFactory {
	Shape createShape(List<Point> points);
}
