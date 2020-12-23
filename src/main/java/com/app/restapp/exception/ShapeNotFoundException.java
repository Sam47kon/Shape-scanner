package com.app.restapp.exception;

public class ShapeNotFoundException extends RuntimeException {
	public ShapeNotFoundException(String id) {
		super("Shape not found! id=" + id);
	}
}
