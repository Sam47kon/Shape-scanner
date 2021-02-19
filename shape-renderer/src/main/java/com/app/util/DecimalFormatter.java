package com.app.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalFormatter {
	public static DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");

	static {
		DEC_FORMAT.setRoundingMode(RoundingMode.CEILING);
	}
}
