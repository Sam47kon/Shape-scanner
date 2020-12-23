package com.app.restapp.event;

import com.app.restapp.annotation.CascadeSave;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@SuppressWarnings("NullableProblems")
public class CascadeCallback implements ReflectionUtils.FieldCallback {

	private final Object source;
	private final MongoOperations mongoOperations;

	public CascadeCallback(final Object source, final MongoOperations mongoOperations) {
		this.source = source;
		this.mongoOperations = mongoOperations;
	}

	@Override
	public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
			final Object fieldValue = field.get(getSource());

			if (fieldValue != null) {
				final FieldCallback callback = new FieldCallback();
				ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
				getMongoOperations().save(fieldValue);
			}
		}
	}

	private Object getSource() {
		return source;
	}

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}
}
