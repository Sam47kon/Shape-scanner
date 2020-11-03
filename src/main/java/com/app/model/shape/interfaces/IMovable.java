package com.app.model.shape.interfaces;

import com.app.model.point.Point;

@FunctionalInterface
public interface IMovable<T extends Point> {

	/**
	 * Переместить на заданное число точек, относительно центра, в необходимую сторону
	 *
	 * @param point - координата со значениями, на которые нужно сместить точки фигуры
	 */
	void move(T point);
}
