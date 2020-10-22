package com.app.factory;

import com.app.model.figure.Shape;
import com.app.model.point.Point;

import java.util.List;

public interface iShapeFactory<T extends Point> {
	Shape<T> createShape(List<T> points);
}
