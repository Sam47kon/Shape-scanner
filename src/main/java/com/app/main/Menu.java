package com.app.main;

import com.app.helper.ScannerHelper;
import com.app.helper.ShapeCreateHelper;
import com.app.model.point.Point2D;
import com.app.model.shape.Shape;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
	private final TreeMap<String, Shape<Point2D>> shapes;
	private final String fileName;

	public Menu(TreeMap<String, Shape<Point2D>> shapes, String fileName) {
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
					displayAllShapes();
					break;
				case 2:
					if (shapesIsEmpty()) break;
					getShapeForAction();
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

	private void displayAllShapes() {
		for (String key : shapes.keySet()) {
			displayText("Строка № " + key + ": " + shapes.get(key));
		}
	}

	private void getShapeForAction() {
		boolean validate;
		String key;
		do {
			key = String.valueOf(ScannerHelper.getIntFromInput("Доступные фигуры:\n " + shapes.keySet() + "\nВведите номер: "));
			validate = shapes.containsKey(key);

		} while (!validate);
		actionsOnShape(key);
	}

	private void actionsOnShape(String position) {
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

	private boolean deleteShape(String position) {
		if (ScannerHelper.isYes("Действительно удалить фигуру?\n")) {
			displayText("Вы удалили фигуру");
			shapes.remove(position);
			return false;
		} else {
			displayText("Фигура не удалена. Возврат в меню действий...");
			return true;
		}
	}

	private void saveChanges() {
		StringBuilder sb = new StringBuilder();
		try {
			List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				Shape<Point2D> shape = shapes.get(String.valueOf(i + 1));
				String newLine;
				if (shape != null) {
					newLine = shape.convertPointsToString();
					String line = fileContent.get(i);
					if (!line.equals(newLine)) {
						fileContent.set(i, newLine);
						sb.append("Фигура в строке №").append(i + 1).append(" заменена:\nБыло:\n\t")
								.append(line).append("\nСтало:\n\t").append(newLine).append("\n");
					}
				} else {
					if (ShapeCreateHelper.isShapeInLine(fileContent.get(i))) {
						sb.append("Фигура удалена в строке №").append(i + 1);
						fileContent.set(i, "");
					}
				}
			}
			sb.append("\n");
			if (shapes.size() != 0) {
				for (int i = fileContent.size() + 1; i <= Integer.parseInt(shapes.lastKey()); i++) {
					fileContent.add(shapes.get(String.valueOf(i)).convertPointsToString());
					sb.append("Добавлена новая фигура в строку №").append(i).append(" с координатами:\n\t").append(fileContent.get(i - 1));
				}
			}
			displayText(sb.toString());
			Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);
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
		shapes.put(String.valueOf(ShapeCreateHelper.moveToNextLine()), createdShape);
		displayText("Добавлена фигура:" + createdShape);
	}

	private void showChanges(Shape<Point2D> shape) {
		displayText("Теперь фигура имеет координаты: " + shape);
	}

	public void displayText(String textToDisplay) {
		System.out.println(textToDisplay);
	}
}
