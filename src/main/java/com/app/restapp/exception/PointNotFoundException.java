package com.app.restapp.exception;

// TODO добавить хэндлер
public class PointNotFoundException extends RuntimeException {
	public PointNotFoundException(Long id) {
		super("Point not found! id=" + id);
	}
}
