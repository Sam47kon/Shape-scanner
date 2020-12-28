package com.app.restapp.service.impl;

import com.app.restapp.exception.ShapeNotFoundException;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.repository.ShapeRepository;
import com.app.restapp.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeServiceImpl implements ShapeService {

	private final ShapeRepository shapeRepository;

	@Autowired
	public ShapeServiceImpl(ShapeRepository shapeRepository) {
		this.shapeRepository = shapeRepository;
	}

	@Override
	public Shape getById(String id) {
		return shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
	}

	@Override
	public List<Shape> getAll() {
		return shapeRepository.findAll();
	}

	@Override
	public Shape insert(Shape shape) {
		return shapeRepository.save(shape);
	}

	@Override
	public Shape update(Shape updatedShape, String id) {
		return shapeRepository.findById(id).map(shape -> {
			updatedShape.setId(shape.getId());
			shape = updatedShape;
			return shapeRepository.save(shape);
		}).orElseThrow(() -> new ShapeNotFoundException(id));
	}

	@Override
	public void delete(Shape shape) {
		shapeRepository.delete(shape);
	}

	@Override
	public boolean deleteById(String id) {
		shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		shapeRepository.deleteById(id);
		return true;
	}

	@Override
	public void deleteAll() {
		shapeRepository.deleteAll();
	}

	@Override
	public Shape rotateShape(String id, double angle) {
		Shape shape = shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		shape.rotate(angle);
		return shapeRepository.save(shape);
	}

	@Override
	public Shape moveShape(String id, double x, double y) {
		Shape shape = shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		shape.move(new Point2D(x, y));
		return shapeRepository.save(shape);
	}

	@Override
	public Shape increase(String id, double scale) {
		Shape shape = shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		shape.increase(scale);
		return shapeRepository.save(shape);
	}

	@Override
	public Shape reduce(String id, double scale) {
		Shape shape = shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		shape.reduce(scale);
		return shapeRepository.save(shape);
	}
}
