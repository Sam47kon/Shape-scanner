package com.app.mvvm;

import com.app.model.Shape;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ImageCreator extends Application {
	private final Shape shape;

	public ImageCreator(Shape shape) {
		this.shape = shape;
		initJFX();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void initJFX() {
		CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});
		try {
			if (!latch.await(5L, TimeUnit.SECONDS)) {
				throw new ExceptionInInitializerError();
			}
		} catch (InterruptedException e) {
			log.error("!latch.await(5L, TimeUnit.SECONDS --- InterruptedException");
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage, String shapeId) {
		try {
			shape.createImage(shapeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
	}
}
