package com.app.restapp.converter;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point3D;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO почему не удаляется _class? по дебагу customConversions вызывается, но convert нет
@Component
public class PointWriterConverter implements Converter<Point, DBObject> {

	@Override
	public DBObject convert(Point point) {
		final DBObject dbObject = new BasicDBObject();
		dbObject.put("x", point.getX());
		dbObject.put("y", point.getY());
		if (point instanceof Point3D) {
			dbObject.put("z", ((Point3D) point).getZ());
		}
		dbObject.removeField("_class");
		return dbObject;
	}
}
