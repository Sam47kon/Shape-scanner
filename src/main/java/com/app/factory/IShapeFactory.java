package com.app.factory;

import com.app.model.point.Point;
import com.app.model.shape.Shape;

import java.util.List;

public interface IShapeFactory<T extends Point> {
	Shape<T> createShape(List<T> points);
}
