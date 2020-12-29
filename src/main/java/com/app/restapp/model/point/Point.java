package com.app.restapp.model.point;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Locale;

//@JsonAutoDetect
//@JsonSubTypes({
//		@JsonSubTypes.Type(value = Point2D.class),
//		@JsonSubTypes.Type(value = Point3D.class)
//})
@Document(collection = "points")
@Data
public abstract class Point
//		implements Serializable
{
//	private static final long serialVersionUID = 1L;
	@Field(name = "x")
	private double x;
	@Field(name = "y")
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
	}

	@Override
	public String toString() {
		return String.format(Locale.ENGLISH, "x=%1$.2f, y=%2$.2f", x, y);
	}
}
