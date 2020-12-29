package com.app.restapp.service.impl;

import com.app.consoleapp.helper.ShapeCreateHelper;
import com.app.restapp.exception.ShapeNotFoundException;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.ShapeType;
import com.app.restapp.repository.ShapeRepository;
import com.app.restapp.service.ShapeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShapeServiceImpl implements ShapeService {

	private final ShapeRepository shapeRepository;

	@Autowired
	public ShapeServiceImpl(ShapeRepository shapeRepository) {
		this.shapeRepository = shapeRepository;
	}

	@Override
	public Shape getById(String id) {
		return findById(id);
	}

	@Override
	public List<Shape> getAll() {
		return shapeRepository.findAll();
	}

	@Override
	public Shape insert(Shape shape) {
		Shape resultShape = ShapeCreateHelper.getShapeFactory(
				ShapeType.valueOf(shape.getClass().getSimpleName().toUpperCase()))
				.createShape(shape.getPoints());
		log.debug("Created shape:\n" + shape);
		return shapeRepository.save(resultShape);
	}

	@Override
	public Shape update(String id, Shape updatedShape) {
		findById(id);
		Shape resultShape = ShapeCreateHelper.getShapeFactory(
				ShapeType.valueOf(updatedShape.getClass().getSimpleName().toUpperCase()))
				.createShape(updatedShape.getPoints());
		resultShape.setId(id);
		log.debug("Updated shape:\n" + resultShape);
		return shapeRepository.save(resultShape);
	}

	@Override
	public void deleteById(String id) {
		findById(id);
		shapeRepository.deleteById(id);
		log.debug("Deleted id: " + id);
	}

	@Override
	public void deleteAll() {
		shapeRepository.deleteAll();
		log.debug("Deleted all Shapes");
	}

	@Override
	public Shape rotateShape(String id, double angle) {
		Shape shape = findById(id);
		shape.rotate(angle);
		log.debug("Rotated Shape id: " + id);
		return shapeRepository.save(shape);
	}

	@Override
	public Shape moveShape(String id, double x, double y) {
		Shape shape = findById(id);
		shape.move(new Point2D(x, y));
		log.debug("Moved Shape id: " + id);
		return shapeRepository.save(shape);
	}

	@Override
	public Shape increase(String id, double scale) {
		Shape shape = findById(id);
		shape.increase(scale);
		log.debug("Increase Shape id: " + id);
		return shapeRepository.save(shape);
	}

	@Override
	public Shape reduce(String id, double scale) {
		Shape shape = findById(id);
		shape.reduce(scale);
		log.debug("Reduce Shape id: " + id);
		return shapeRepository.save(shape);
	}

	private Shape findById(String id) {
		Shape shape = shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		log.debug("Find shape id:" + id + "\n" + shape);
		return shape;
	}
}
