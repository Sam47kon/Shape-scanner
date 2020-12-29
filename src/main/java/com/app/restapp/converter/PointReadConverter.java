package com.app.restapp.converter;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point2D;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PointReadConverter implements Converter<Document, Point> {

	@Override
	public Point convert(Document dbObject) {
		Point point = new Point2D();
//		point.setX(Double.parseDouble(dbObject.get("x").toString()));
//		dbObject.get("y");


		return point;
	}


//	public Shape createShape(Document shape) {
//		int id = shape.getInteger("id");
//
//		ArrayList<Document> pointsDoc = (ArrayList<Document>) shape.get("points");
//		ArrayList<Point2D> points = new ArrayList<>();
//		pointsDoc.forEach(p -> points.add(new Point2D(p.getDouble("x"), p.getDouble("y"))));
//
//		ShapeType shapeType = Utils.getShapeTypeByName(shape.getString("shapeType"));
//
//		ArrayList<Double> centerArray = (ArrayList<Double>) shape.get("center");
//		Point2D center = new Point2D(centerArray.get(0), centerArray.get(1));
//
//		//radius =null
//		Double radius = shape.getDouble("radius");
//
//		ShapeCreateHelper helper = new ShapeCreateHelper();
//		IShapeFactory factory = helper.getShapeFactory(shapeType);
//		Shape figure =factory.createShape(id, points, shapeType, center, radius);
//		return figure;
}
