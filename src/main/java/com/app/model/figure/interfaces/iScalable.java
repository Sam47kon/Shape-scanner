package com.app.model.figure.interfaces;

public interface iScalable {
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
