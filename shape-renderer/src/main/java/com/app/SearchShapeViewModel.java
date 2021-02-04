package com.app;

import lombok.Data;
import org.zkoss.bind.annotation.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class SearchShapeViewModel {

	private String keyword;
	private List<Shape> shapes = new ArrayList<>(Arrays.asList(ShapeServiceImpl.shape, ShapeServiceImpl.shape));
	private Shape selectedShape;
	private ShapeService shapeService = new ShapeServiceImpl();

	@Command
	public void search() {
		shapes.clear();
		System.out.println("shapes = " + shapes);
		shapes.addAll(shapeService.search(keyword));
	}
}
