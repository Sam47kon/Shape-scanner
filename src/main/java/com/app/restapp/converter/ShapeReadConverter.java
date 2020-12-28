package com.app.restapp.converter;

import com.app.consoleapp.factory.IShapeFactory;
import com.app.consoleapp.helper.ShapeCreateHelper;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.ShapeType;
import com.mongodb.DBObject;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO нужно ли мне это?
@Slf4j
@Component
public class ShapeReadConverter implements Converter<DBObject, Shape> {

	@Override
	public Shape convert(DBObject dbObject) {
		Object shapeType = dbObject.get("shapeType");
		log.info(dbObject.toString());
		IShapeFactory factory = ShapeCreateHelper.getShapeFactory(ShapeType.valueOf(
				Document.parse(dbObject.toString()).getString("shapeType")));
//		List<Point> points = dbObject.get("points");
//		final Shape shape = factory.createShape();

		return null;
	}
}
