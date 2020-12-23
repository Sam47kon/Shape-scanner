package com.app.restapp.repository;

import com.app.restapp.model.point.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

// FIXME
@RepositoryRestResource(collectionResourceRel = "points", path = "points")
public interface PointRepository extends MongoRepository<Point, Long> {
	List<Point> findByX(@Param("x") Double x);

	List<Point> findByY(@Param("y") Double y);

	Optional<Point> findById(@NonNull String id);
}
