package com.app.model.shape.interfaces;

@FunctionalInterface
public interface iRotatable {
	/**
	 * Повернуть на заданное число градусов, относительно центра, в необходимую сторону
	 *
	 * @param angle - число в °
	 */
	void rotate(double angle);
}
