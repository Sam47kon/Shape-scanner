package com.app;

import lombok.Data;
import org.zkoss.bind.annotation.Command;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchShapeViewModel {

	private String keyword;
	private List<Shape> shapes = new ArrayList<>();
	private Shape selectedShape;
	private ShapeService shapeService = new ShapeServiceImpl();

	@Command
	public void search() {
		shapes.clear();
		shapes.addAll(shapeService.search(keyword));
	}
}
