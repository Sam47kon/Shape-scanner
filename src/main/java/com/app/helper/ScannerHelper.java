package com.app.helper;

import java.util.Scanner;

public class ScannerHelper {
	private final Scanner reader;

	public ScannerHelper() {
		reader = new Scanner(System.in);
	}

	public double getDoubleFromInput(String text) { // метод ввода только числа
		boolean ifInputErr = true;
		double number = 0;
		System.out.print(text);
		while (ifInputErr) {    // цикл заставляет вводить только числа, не выводя ошибку InputMismatchException
			if (reader.hasNextDouble()) {    // has имеет булевское значение
				number = reader.nextDouble();
				ifInputErr = false;
			} else {
				System.out.print("Вы должны ввести число, а не текст! " + text);
				reader.next();
			}
		}
		return number;
	}


	public int getIntFromInput(String text) { // метод ввода только числа
		boolean ifInputErr = true;
		int number = 0;
		System.out.print(text);
		while (ifInputErr) {    // цикл заставляет вводить только числа, не выводя ошибку InputMismatchException
			if (reader.hasNextInt()) {
				number = reader.nextInt();
				ifInputErr = false;
			} else {
				System.out.print("Вы должны ввести число, а не текст! " + text);
				reader.next();
			}
		}
		return number;
	}

	public boolean isYes(String text) {
		System.out.print(text);
		String answer = reader.next().toLowerCase().trim().replaceAll("\\W", "");
		return "y".equals(answer) || "yes".equals(answer) || "да".equals(answer);
	}

	public void close() {
		reader.close();
	}
}
