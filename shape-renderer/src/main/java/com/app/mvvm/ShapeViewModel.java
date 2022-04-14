package com.app.mvvm;

import com.app.model.Point;
import com.app.model.Shape;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class ShapeViewModel {

	private String keyword;
	private ListModelList<Shape> shapes;
	private Shape selectedShape;

	private Double rotateAngle;
	private Double moveX;
	private Double moveY;
	private Double increaseScale;
	private Double reduceScale;
	private String srcImg = "/image/image1.png";

	private Shape newShape;
	private Double x;
	private Double y;
	private List<Point> points;

	private ShapeService shapeService = new ShapeServiceImpl();
	private Map<String, String> validationMessages = new HashMap<>();


	public void setSelectedShape(Shape selectedShape) {
		this.selectedShape = selectedShape;
		ImageCreator imageCreator = new ImageCreator(this.selectedShape);
		Platform.runLater(() -> imageCreator.start(new Stage(), this.selectedShape.getId()));
		try {
			imageCreator.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Init
	public void init() {
		shapes = new ListModelList<>();
		points = new ListModelList<>();
	}

	@Command
	@NotifyChange({"keyword", "selectedShape"})
	public void search() throws IOException {
		shapes.clear();
		unselect();
		shapes.addAll(shapeService.search(keyword));
	}

	@Command
	@NotifyChange({"x", "y", "points"})
	public void addPoint() {
		if (x == null || y == null) {
			log.error("X {}, Y {}", x, y);
		} else {
			points.add(new Point(x, y));
			log.info(points.toString());
		}
		x = null;
		y = null;
	}

	@Command
	@NotifyChange({"points", "x", "y", "shapes", "selectedShape", "srcImg"})
	public void createShape() {
		Shape createdShape = shapeService.create(points);
		log.info("CALL: createShape: {}", createdShape.toString());
		shapes.add(createdShape);
		selectedShape = createdShape;
		points.clear();
		x = null;
		y = null;
	}

	@Command
	@NotifyChange({"shapes", "selectedShape", "rotateAngle"})
	public void rotateShape() {
		log.info("CALL: rotateShape: id {}, rotateAngle {}", selectedShape.getId(), rotateAngle);
//		shapes.remove(selectedShape); // FIXME как найти изменяемый shape в листе?
		selectedShape = shapeService.rotate(selectedShape.getId(), rotateAngle);
//		shapes.add(selectedShape);
		log.info("CALL: rotateShape: result: {}", selectedShape);
		rotateAngle = null;
	}

	@Command
	@NotifyChange({"shapes", "selectedShape", "moveX", "moveY"})
	public void moveShape() {
		log.info("CALL: moveShape: id {}, moveX {}, moveY {}", selectedShape.getId(), moveX, moveY);
		selectedShape = shapeService.move(selectedShape.getId(), moveX, moveY);
		log.info("CALL: moveShape: result: {}", selectedShape);
		moveX = null;
		moveY = null;
	}

	@Command
	@NotifyChange({"shapes", "selectedShape", "increaseScale"})
	public void increaseShape() {
		scale(true);
		increaseScale = null;
	}

	@Command
	@NotifyChange({"shapes", "selectedShape", "reduceScale"})
	public void reduceShape() {
		scale(false);
		reduceScale = null;
	}

	@Command
	public void deleteShape() {
		shapeService.delete(selectedShape);
	}

	@Command
	public void confirmDelete() {
		shapes.remove(selectedShape);
		shapeService.delete(selectedShape);
		selectedShape = null;
	}

	private void scale(boolean increase) {
		log.info("CALL: scale: increase {}, id {}, scale {}", increase, selectedShape.getId(),
				increase ? increaseScale : reduceScale);
		selectedShape = shapeService.scale(selectedShape.getId(), increase,
				increase ? increaseScale : reduceScale);
		log.info("CALL: scale: result: {}", selectedShape);
	}

	private void unselect() {
		selectedShape = null;
	}
}
