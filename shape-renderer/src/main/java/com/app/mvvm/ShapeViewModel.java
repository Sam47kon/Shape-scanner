package com.app.mvvm;

import com.app.model.Point;
import com.app.model.Shape;
import lombok.Data;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ShapeViewModel {

	private String keyword;
	private List<Shape> shapes;
	private Shape selectedShape;
	private Point selectedPoint;
	private ShapeService shapeService = new ShapeServiceImpl();
	private Map<String, String> validationMessages = new HashMap<>();

	@Init
	public void init(){
		shapes = new ListModelList<>();
	}

	@Command
	@NotifyChange("keyword")
	public void search() throws IOException {
		shapes.clear();
		unselect();
		shapes.addAll(shapeService.search(keyword));
	}

	@Command
	public void newShape() {

	}

	@Command
	public void saveShape() {
		shapeService.save(selectedShape);
		validationMessages.clear();
	}

	@Command
	public void updateShape() {

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

	private void unselect() {
		selectedShape = null;
	}
}
