package com.app.restapp.model.shape.polygon;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.ShapeType;

import java.util.List;

import static com.app.util.Utils.*;

public class Polygon extends Shape {
	protected double side1;
	protected double side2;
	protected double side3;
	protected double angleA;
	protected double angleB;
	protected double angleC;
	protected Point pointA;
	protected Point pointB;
	protected Point pointC;

	protected Polygon() {
	}

	public Polygon(List<Point> points) {
		copyPoints(this.points, points);
		center = calcMidPoint(this.points);
		this.pointA = this.points.get(0);
		this.pointB = this.points.get(1);
		this.pointC = this.points.get(2);

		this.side1 = calcDistance(pointA, pointB);
		this.side2 = calcDistance(pointB, pointC);
		shapeType = ShapeType.POLYGON;
	}

	/**
	 * Вычисляет площадь многоугольника по формуле Гаусса
	 * до этого был реализован этот вариант:
	 * Площадь произвольного несамопересекающегося четырёхугольника, заданного на плоскости координатами своих вершин
	 * * (x1,y1),(x2,y2),(x3,y3),(x4,y4) в порядке обхода, равна:
	 * * S = |(x1-x2)(y1+y2) + (x2-x3)(y2+y3) + (x3-x4)(y3+y4) + (x4-x1)(y4+y1)| * 1/2
	 *
	 * @return - площадь любого многоугольника
	 */
	@Override
	protected double calcArea() {
		double sumX = 0;
		double sumY = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			sumX = sumX + points.get(i).getX() * points.get(i + 1).getY();
			sumY = sumY + points.get(i).getY() * points.get(i + 1).getX();
		}
		return 0.5 * Math.abs(sumX - sumY);
	}

	@Override
	public void move(Point point) {
		movePoints(points, point);
		movePointByPoint(center, point);
	}

	@Override
	public void rotate(double angle) {
		rotateShape(points, center, angle);
	}

	@Override
	public void increase(double factor) {
		scaleShape(points, center, factor);
		area = calcArea();
		side1 *= factor;
		side2 *= factor;
		side3 *= factor;
	}

	@Override
	public void reduce(double factor) {
		increase(1 / factor);
	}
}
