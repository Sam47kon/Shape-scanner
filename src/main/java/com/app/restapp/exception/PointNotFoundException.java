package com.app.restapp.exception;

public class PointNotFoundException extends RuntimeException {
	public PointNotFoundException(String id) {
		super("Point not found! id=" + id);
	}
}
