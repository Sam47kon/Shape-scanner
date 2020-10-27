package com.app.main;

import com.app.helper.ScannerHelper;
import com.app.helper.ShapeCreateHelper;
import com.app.model.point.Point2D;
import com.app.model.shape.Shape;

import java.util.List;

public class Application {
	private final String MAIN_MENU = "Выберите действие:"
			+ "\n\t1 - Показать характеристики всех фигур"
			+ "\n\t2 - Выбрать конкретную фигуру (для действий над ней)"
			+ "\n\t3 - Создать фигуру"
			+ "\n\t4 - Выход";
	private final String SHAPE_MENU = "\n\t1 - Повернуть"
			+ "\n\t2 - Переместить"
			+ "\n\t3 - Увеличить"
			+ "\n\t4 - Уменьшить"
			+ "\n\t5 - Рассчитать площадь"
			+ "\n\t6 - Удалить фигуру"
			+ "\n\t7 - Вернуться назад\n";
	private final String CHOOSE_ACTION = "Выберите действие: ";
	private final String ILLEGAL_INPUT = "Вы ввели число, отличное от предложенных";

	public void start(List<Shape<Point2D>> shapes) {
		boolean itContinues = true;
		while (itContinues) {
			System.out.println(MAIN_MENU);
			switch (ScannerHelper.getIntFromInput(CHOOSE_ACTION)) {
				case 1:
					System.out.println(shapes);
					break;
				case 2:
					if (shapes.size() == 0) {
						System.out.println("Фигур отсутствуют!");
						break;
					}
					int pos = shapes.size();
					while (pos >= shapes.size()
							|| pos < 0) {
						pos = ScannerHelper.getIntFromInput("Выберите номер от 1 до " + shapes.size() + ": ") - 1;
					}
					actionsOnShape(shapes, pos);
					break;
				case 3:
					List<Point2D> points = ShapeCreateHelper.createPointFromInput();
					Shape<Point2D> createdShape = (ShapeCreateHelper.getShapeFactory(points)
							.createShape(points));
					shapes.add(createdShape);
					System.out.println("Добавлена фигура:" + createdShape);
					break;
				case 4:
					itContinues = !ScannerHelper.isYes("Действительно выйти? ");
					break;
				default:
					System.out.println(ILLEGAL_INPUT);
					break;
			}
		}
		ScannerHelper.close();
	}

	private void actionsOnShape(List<Shape<Point2D>> shapes, int position) {
		Shape<Point2D> shape = shapes.get(position);
		boolean itContinues = true;
		System.out.println("Вы выбрали: " + shape + "\nЧто необходимо сделать с фигурой?");
		while (itContinues) {
			switch (ScannerHelper.getIntFromInput(CHOOSE_ACTION + SHAPE_MENU)) {
				case 1:
					double angel = ScannerHelper.getDoubleFromInput("Введите угол, на который ходите повернуть: ");
					shape.rotate(angel);
					showChanges(shape);
					break;
				case 2:
					double x = ScannerHelper.getDoubleFromInput("Введите x (дистанция по горизонтали): ");
					double y = ScannerHelper.getDoubleFromInput("Введите y (дистанция по вертикали): ");
					shape.move(new Point2D(x, y));
					showChanges(shape);
					break;
				case 3:
					double scaleInc = -1;
					while (scaleInc < 0) {
						scaleInc = ScannerHelper.getDoubleFromInput("Введите число, в которое необходимо увеличить фигуру: ");
					}
					shape.increase(scaleInc);
					showChanges(shape);
					break;
				case 4:
					double scaleReduce = -1;
					while (scaleReduce < 0) {
						scaleReduce = ScannerHelper.getDoubleFromInput("Введите число, в которое необходимо уменьшить фигуру: ");
					}
					shape.reduce(scaleReduce);
					showChanges(shape);
					break;
				case 5:
					System.out.println("Площадь = " + shape.getArea());
					break;
				case 6:
					if (ScannerHelper.isYes("Действительно удалить фигуру? (действие необратимо, фигура удалится из файла!)\n")) {
						System.out.println("Вы удалили фигуру");
						shapes.remove(position);
						itContinues = false;
					} else System.out.println("Фигура не удалена. Возврат в меню...");
					break;
				case 7:
					itContinues = false;
					break;
				default:
					System.out.println(ILLEGAL_INPUT);
					break;
			}
		}
	}

	private void showChanges(Shape<Point2D> shape) {
		System.out.println("Теперь фигура имеет координаты: " + shape);
	}
}
