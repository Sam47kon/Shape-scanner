package com.app.restapp.service;

import com.app.restapp.model.shape.Shape;

public interface ShapeService extends ShapeServiceRest {

	Shape rotateShape(String id, double angle);

	Shape moveShape(String id, double x, double y);

	Shape increase(String id, double scale);

	Shape reduce(String id, double scale);
}
