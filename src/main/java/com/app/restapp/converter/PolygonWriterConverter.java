package com.app.restapp.converter;

import com.app.restapp.model.shape.polygon.Polygon;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PolygonWriterConverter implements Converter<Polygon, DBObject> {

	@Override
	public DBObject convert(Polygon shape) {
		final DBObject dbObject = new BasicDBObject();
		dbObject.put("points", shape.getPoints());
		dbObject.put("center", shape.getCenter());
		dbObject.put("shapeType", shape.getShapeType());
		dbObject.removeField("_class");
		return dbObject;
	}
}
