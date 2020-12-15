package com.app.model.point;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Locale;

@JsonAutoDetect
@Data
public abstract class Point implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private double x;
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
