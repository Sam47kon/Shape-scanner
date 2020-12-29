package com.app.restapp.controller;

import com.app.restapp.model.shape.Shape;
import com.app.restapp.service.ShapeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

	@PostMapping
	public ResponseEntity<Shape> createShape(@RequestBody Shape shape) {
		return ResponseEntity.status(HttpStatus.CREATED).body(shapeService.insert(shape));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Shape> updateShape(@PathVariable String id, @RequestBody Shape shape) {
		return ResponseEntity.accepted().body(shapeService.update(id, shape));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Shape> rotateShape(@PathVariable String id, @RequestBody double angle) {
		return ResponseEntity.accepted().body(shapeService.rotateShape(id, angle));
	}

	// TODO доделать 3 действия с сервиса
}
