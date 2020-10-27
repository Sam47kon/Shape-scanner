package com.app.main;

import com.app.helper.ScannerHelper;
import com.app.helper.ShapeCreateHelper;
import com.app.model.point.Point2D;
import com.app.model.shape.Shape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Menu {
	private final String MAIN_MENU = "Выберите действие:"
			+ "\n\t1 - Показать характеристики всех фигур"
			+ "\n\t2 - Выбрать конкретную фигуру (для действий над ней)"
			+ "\n\t3 - Создать фигуру"
			+ "\n\t4 - Сохранить изменения"
			+ "\n\t5 - Выход";
	private final String SHAPE_MENU = "\n\t1 - Повернуть"
			+ "\n\t2 - Переместить"
			+ "\n\t3 - Увеличить"
			+ "\n\t4 - Уменьшить"
			+ "\n\t5 - Рассчитать площадь"
			+ "\n\t6 - Удалить фигуру"
			+ "\n\t7 - Вернуться назад\n";
	private final String CHOOSE_ACTION = "Выберите действие: ";
	private final String ILLEGAL_INPUT = "Вы ввели число, отличное от предложенных";
	private final List<Shape<Point2D>> shapes;
	private final String fileName;

	public Menu(List<Shape<Point2D>> shapes, String fileName) {
		this.shapes = shapes;
		this.fileName = fileName;
	}

	public void start() {
		displayText("Добро пожаловать в приложение!");
		displayText(shapes.size() == 0
				? "Фигуры отсутствуют"
				: "Фигуры успешно загружены.\n\tДоступно... " + shapes.size() + " фигур");
		boolean itContinues = true;
		while (itContinues) {
			displayText(MAIN_MENU);
			switch (ScannerHelper.getIntFromInput(CHOOSE_ACTION)) {
				case 1:
					if (shapesIsEmpty()) break;
					displayText(shapes.toString());
					break;
				case 2:
					if (shapesIsEmpty()) break;
					int pos = shapes.size();
					while (pos >= shapes.size() || pos < 0) {
						pos = ScannerHelper.getIntFromInput("Выберите номер от 1 до " + shapes.size() + ": ") - 1;
					}
					actionsOnShape(pos);
					break;
				case 3:
					addShape();
					break;
				case 4:
					saveChanges();
					break;
				case 5:
					itContinues = !ScannerHelper.isYes("Действительно выйти? ");
					break;
				default:
					displayText(ILLEGAL_INPUT);
					break;
			}
		}
		ScannerHelper.close();
	}

	private void actionsOnShape(int position) {
		Shape<Point2D> shape = shapes.get(position);
		boolean itContinues = true;
		displayText("Вы выбрали: " + shape + "\nЧто необходимо сделать с фигурой?");
		while (itContinues) {
			switch (ScannerHelper.getIntFromInput(CHOOSE_ACTION + SHAPE_MENU)) {
				case 1:
					rotateShape(shape);
					break;
				case 2:
					moveShape(shape);
					break;
				case 3:
					scaleShape(shape, true);
					break;
				case 4:
					scaleShape(shape, false);
					break;
				case 5:
					displayText("Площадь = " + shape.getArea());
					break;
				case 6:
					itContinues = deleteShape(position);
					break;
				case 7:
					itContinues = false;
					break;
				default:
					displayText(ILLEGAL_INPUT);
					break;
			}
		}
	}

	private void rotateShape(Shape<Point2D> shape) {
		double angel = ScannerHelper.getDoubleFromInput("Введите угол, на который ходите повернуть: ");
		shape.rotate(angel);
		showChanges(shape);
	}

	private void moveShape(Shape<Point2D> shape) {
		double x = ScannerHelper.getDoubleFromInput("Введите x (дистанция по горизонтали): ");
		double y = ScannerHelper.getDoubleFromInput("Введите y (дистанция по вертикали): ");
		shape.move(new Point2D(x, y));
		showChanges(shape);
	}

	private void scaleShape(Shape<Point2D> shape, boolean isIncrease) {
		double scaleInc = -1;
		while (scaleInc < 0) {
			scaleInc = ScannerHelper.getDoubleFromInput("Введите натуральное число, в которое необходимо фигуру " +
					(isIncrease ? "увеличить: " : "уменьшить: "));
		}
		shape.increase(scaleInc);
		showChanges(shape);
	}

	private boolean deleteShape(int position) {
		if (ScannerHelper.isYes("Действительно удалить фигуру? (действие необратимо, фигура удалится из файла!)\n")) {
			displayText("Вы удалили фигуру");
			shapes.remove(position);
			return false;
		} else {
			displayText("Фигура не удалена. Возврат в меню действий...");
			return true;
		}
	}

	private void saveChanges() {
		try (FileWriter writer = new FileWriter(fileName)) {
			// TODO остановился здесь
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean shapesIsEmpty() {
		if (shapes.size() == 0) {
			displayText("Фигур отсутствуют!");
			return true;
		}
		return false;
	}

	private void addShape() {
		List<Point2D> points = ShapeCreateHelper.createPointFromInput();
		Shape<Point2D> createdShape = ShapeCreateHelper.getShapeFactory(points).createShape(points);
		shapes.add(createdShape);
		displayText("Добавлена фигура:" + createdShape);
	}

	private void showChanges(Shape<Point2D> shape) {
		displayText("Теперь фигура имеет координаты: " + shape);
	}

	public void displayText(String textToDisplay) {
		System.out.println(textToDisplay);
	}

}
