package com.app.factory;

import com.app.model.point.Point;
import com.app.model.shape.Shape;

import java.util.List;

public interface iShapeFactory<T extends Point> {
	Shape<T> createShape(List<T> points);
}
