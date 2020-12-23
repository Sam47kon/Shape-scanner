package com.app.restapp.event;

import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@SuppressWarnings("NullableProblems")
public class FieldCallback implements ReflectionUtils.FieldCallback {

	private boolean idFound;

	@Override
	public void doWith(Field field) throws IllegalArgumentException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(Id.class)) {
			idFound = true;
		}
	}

	public boolean isIdFound() {
		return idFound;
	}
}
