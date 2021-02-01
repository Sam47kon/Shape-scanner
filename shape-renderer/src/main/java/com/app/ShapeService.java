package com.app;

import java.util.List;

public interface ShapeService {
	List<Shape> findAll();
	List<Shape> search(String keyword);
}
