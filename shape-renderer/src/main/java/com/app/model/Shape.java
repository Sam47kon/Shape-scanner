package com.app.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Data
@Slf4j
public class Shape {

	private String id;
	private ShapeType shapeType;
	private Point center;
	private double radius;
	private List<Point> points;

	public Shape() {
	}

	public Shape(String id, List<Point> points, Point center, double radius, ShapeType shapeType) {
		this.id = id;
		this.points = points;
		this.center = center;
		this.radius = radius;
		this.shapeType = shapeType;
	}

	private double calcDistance(Point pointA, Point pointB) {
		return Math.sqrt(Math.pow(pointB.getX() - pointA.getX(), 2) + Math.pow(pointB.getY() - pointA.getY(), 2));
	}

	public void createImage(String shapeId) {
//		File file = getFileFromResource("image/" + shapeId);
		File file = new File("src/main/resources/image/image1.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(drawImage(), null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private WritableImage drawImage() {
		Canvas canvas = new Canvas(400, 400);
		GraphicsContext context = canvas.getGraphicsContext2D();
		if (shapeType == ShapeType.CIRCLE) {
			radius = calcDistance(points.get(0), points.get(1));
			context.fillOval(center.getX() + 200, center.getY() + 200, radius, radius);
		} else {
			double[] x = points.stream().mapToDouble(value -> value.getX() + 200).toArray();
			double[] y = points.stream().mapToDouble(value -> value.getY() + 200).toArray();
			context.fillPolygon(x, y, points.size());
		}
		return canvas.snapshot(null, null);
	}

	private File getFileFromResource(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("Файл не найден " + fileName);
		}
		try {
			return new File(resource.toURI());
		} catch (URISyntaxException e) {
			return null;
		}
	}
}
