package com.app.restapp.model.shape.interfaces;

public interface IScalable {
	/**
	 * Увеличить фигуру в заданное число раз, относительно центра
	 *
	 * @param factor - множитель
	 */
	void increase(double factor);

	/**
	 * Уменьшить фигуру в заданное число раз, относительно центра
	 *
	 * @param factor - множитель
	 */
	void reduce(double factor);
}
