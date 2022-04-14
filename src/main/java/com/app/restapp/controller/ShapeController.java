package com.app.restapp.controller;

import com.app.restapp.config.InitDB;
import com.app.restapp.model.point.Point;
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

	@GetMapping(value = "login/")
	public ResponseEntity<String> getShape(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "password",
			defaultValue = "") String password) {
		Boolean isAuthorized = InitDB.testConnectionDB(name, password);
		return isAuthorized ? ResponseEntity.ok("OK")
				: new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
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
	public ResponseEntity<Shape> createShape(@RequestBody List<Point> points) {
		return ResponseEntity.status(HttpStatus.CREATED).body(shapeService.createByPoints(points));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Shape> updateShape(@PathVariable String id, @RequestBody Shape shape) {
		return ResponseEntity.accepted().body(shapeService.update(id, shape));
	}

	@PutMapping("rotate/{id}")
	public ResponseEntity<Shape> rotateShape(@PathVariable String id, @RequestParam(value = "angle", defaultValue = "0") double angle) {
		return ResponseEntity.accepted().body(shapeService.rotateShape(id, angle));
	}

	@PutMapping("move/{id}")
	public ResponseEntity<Shape> moveShape(@PathVariable String id, @RequestParam(value = "x", defaultValue = "0") double x,
										   @RequestParam(value = "y", defaultValue = "0") double y) {
		return ResponseEntity.accepted().body(shapeService.moveShape(id, x, y));
	}

	@PutMapping("increase/{id}")
	public ResponseEntity<Shape> increaseShape(@PathVariable String id, @RequestParam(value = "scale", defaultValue = "1") double scale) {
		return ResponseEntity.accepted().body(shapeService.increase(id, scale));
	}

	@PutMapping("reduce/{id}")
	public ResponseEntity<Shape> reduceShape(@PathVariable String id, @RequestParam(value = "scale", defaultValue = "1") double scale) {
		return ResponseEntity.accepted().body(shapeService.reduce(id, scale));
	}
}
