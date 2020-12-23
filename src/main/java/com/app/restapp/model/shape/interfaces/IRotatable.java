package com.app.restapp.model.shape.interfaces;

@FunctionalInterface
public interface IRotatable {
	/**
	 * Повернуть на заданное число градусов, относительно центра, в необходимую сторону
	 *
	 * @param angle - число в °
	 */
	void rotate(double angle);
}
