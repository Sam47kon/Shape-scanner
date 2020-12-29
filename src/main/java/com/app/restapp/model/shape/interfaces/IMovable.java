package com.app.restapp.model.shape.interfaces;

import com.app.restapp.model.point.Point;

@FunctionalInterface
public interface IMovable {

	/**
	 * Переместить на заданное число точек, относительно центра, в необходимую сторону
	 *
	 * @param point - координата со значениями, на которые нужно сместить точки фигуры
	 */
	void move(Point point);
}
