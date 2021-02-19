package com.app.restapp.repository;

import com.app.restapp.model.shape.Shape;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "shapes", path = "shapes")
public interface ShapeRepository extends MongoRepository<Shape, String> {

	// TODO реализовать в сервисе, добавить в контроллер и поменять в сервисе ui вместо findAll - findByShapeType
	@Query("{ 'shapeType' : ?0 ")
	List<Shape> findShapesByShapeType(String shapeType);
}
