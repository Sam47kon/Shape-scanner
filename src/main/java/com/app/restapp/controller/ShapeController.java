package com.app.restapp.controller;

import com.app.consoleapp.factory.IShapeFactory;
import com.app.consoleapp.helper.ShapeCreateHelper;
import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.ShapeType;
import com.app.restapp.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO добавить сюда все действия из меню и создать имплементации
@RestController
@RequestMapping(value = "shapes")
public class ShapeController {

	private final ShapeService shapeService;

	@Autowired
	public ShapeController(ShapeService shapeService) {
		this.shapeService = shapeService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Shape> getShape(@PathVariable String id) {
		return ResponseEntity.ok(shapeService.getById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Shape> deleteShape(@PathVariable String id) {
		shapeService.deleteById(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping
	public ResponseEntity<List<Shape>> getAllShapes() {
		return ResponseEntity.ok(shapeService.getAll());
	}

	@PostMapping // FIXME исправить
	public ResponseEntity<Shape> createShape(@RequestPart List<Point> points, @RequestPart String type) {
		System.out.println("Вызов фабрики");
		IShapeFactory factory = ShapeCreateHelper.getShapeFactory(ShapeType.valueOf(type));
		System.out.println("Вызов создания фигуры");
		Shape shape = factory.createShape(points);
		return ResponseEntity.status(HttpStatus.CREATED).body(shapeService.insert(shape));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Shape> updateShape(@PathVariable String id, @RequestBody Shape shape) {
		System.out.println("вызоов");
		return ResponseEntity.accepted().body(shapeService.update(id, shape));
	}

	@PutMapping("/{id}?{angle}")
	public ResponseEntity<Shape> rotateShape(@PathVariable String id, @PathVariable double angle) {
		return ResponseEntity.accepted().body(shapeService.rotateShape(id, angle));
	}
}
