package com.app.model.figure.interfaces;

import com.app.model.point.Point;

public interface iMovable<T extends Point> {

	/**
	 * Переместить на заданное число точек, относительно центра, в необходимую сторону
	 *
	 * @param point - координата со значениями, на которые нужно сместить точки фигуры
	 */
	void move(T point);
}
