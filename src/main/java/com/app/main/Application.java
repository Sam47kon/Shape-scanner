package com.app.main;

import com.app.helper.ScannerHelper;
import com.app.model.figure.Shape;
import com.app.model.point.Point2D;

import java.util.List;

public class Application {
	private static final ScannerHelper scanner = new ScannerHelper();

	public static void start(List<Shape<Point2D>> shapes) {
		boolean itContinues = true;
		while (itContinues) {
			System.out.println("Выберите действие:"
					+ "\n\t1 - показать характеристики всех фигур,"
					+ "\n\t2 - выбрать конкретную фигуру (для действий над ней)"
					+ "\n\t3 - выход"
			);
			switch (scanner.getIntFromInput("Выберите действие: ")) {
				case 1:
					System.out.println(shapes);
					break;
				case 2:
					int pos = shapes.size() + 1;
					while (pos > shapes.size() || pos < 0) {
						pos = scanner.getIntFromInput("Выберите номер от 1 до " + shapes.size() + " ") - 1;
					}
					actionsOnShape(shapes.get(pos));
					break;
				case 3:
					itContinues = scanner.isYes("Действительно выйти? ");
					break;
				default:
					System.out.println("Вы ввели число, отличное от отпредложенных");
					break;
			}
		}
		scanner.close();
	}

	private static void actionsOnShape(Shape<Point2D> shape) {
		boolean itContinues = true;
		System.out.println("Что необходимо сделать с фигурой?"
				+ "\n\t1 - Повернуть"
				+ "\n\t2 - Переместить"
				+ "\n\t3 - Увеличить"
				+ "\n\t4 - Уменьшить"
				+ "\n\t5 - Рассчитать площадь"
				+ "\n\t6 - Вернуться назад");
		while (itContinues) {
			switch (scanner.getIntFromInput("Выберите действие: ")) {
				case 1:
					double angel = scanner.getDoubleFromInput("Введите угол, на который ходите повернуть: ");
					shape.rotate(angel);
					System.out.println("Теперь фигура имеет координаты: " + shape);
					break;
				case 2: // TODO остановился тут
//					double angel = scanner.getDoubleFromInput("Введите угол, на который ходите повернуть: ");
//					shape.rotate(angel);
//					System.out.println("Теперь фигура имеет координаты: " + shape);
					break;
				case 3:
				case 4:
				case 5:
				case 6:
				default:
					System.out.println("Вы ввели число, отличное от отпредложенных");
					break;
			}
		}
	}
}
