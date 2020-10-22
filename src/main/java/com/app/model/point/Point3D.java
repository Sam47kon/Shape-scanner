package com.app.model.point;

import java.util.Locale;

public final class Point3D extends Point {
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
