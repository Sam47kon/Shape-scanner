package com.app.restapp.model.point;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Locale;

public final class Point3D extends Point {
	@Field(name = "z")
	private double z;

	public Point3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + super.toString() + String.format(Locale.ENGLISH, ", z=%1$.2f", z);
	}
}
