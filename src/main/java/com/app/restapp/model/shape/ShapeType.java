package com.app.restapp.model.shape;

public enum ShapeType {
	QUADRANGULAR("Четырехугольник"),
	SQUARE("Квадрат"),
	RHOMBUS("Ромб"),
	RECTANGLE("Прямоугольник"),
	POLYGON("Многоугольник"),
	PARALLELOGRAM("Параллелограмм"),
	TRIANGLE("Треугольник"),
	CIRCLE("Окружность"),
	SPHERE("Сфера");

	private final String title;

	ShapeType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "Тип фигуры: " + title;
	}
}
