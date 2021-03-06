package com.app.restapp.config;

import com.app.consoleapp.helper.ShapeCreateHelper;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.polygon.quadrangular.Quadrangular;
import com.app.restapp.service.ShapeService;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.app.consoleapp.main.ConsoleApp.FILE_NAME;
import static com.app.util.Utils.getRuntime;

@Slf4j
@Configuration
public class InitDB implements CommandLineRunner {

	private final ShapeService shapeService;

	@Autowired
	public InitDB(ShapeService shapeService) {
		this.shapeService = shapeService;
	}

	private static void testConnectionDB() {
		Logger.getLogger("org.mongodb.").setLevel(Level.WARNING);
		try (MongoClient mongoClient = MongoClients.create(MongoConfig.URI)) {
			MongoIterable<String> strings = mongoClient.listDatabaseNames();
			MongoCursor<String> cursor = strings.cursor();
			Map<String, MongoDatabase> mongoDatabases = new HashMap<>();

			while (cursor.hasNext()) {
				String databaseName = cursor.next();
				mongoDatabases.put(databaseName, mongoClient.getDatabase(databaseName));
			}
			System.out.println("----------------------------------------------------------------------------------" + "\n"
					+ "----------------------------------------------------------------------------------" + "\n"
					+ "Названия БД:");
			mongoDatabases.values().stream().map(MongoDatabase::getName).forEach(System.out::println);
			System.out.println("----------------------------------------------------------------------------------" + "\n"
					+ "----------------------------------------------------------------------------------");
			// Нужная БД
			MongoDatabase shapeDB = mongoDatabases.get("shape-app");
		}
	}

	// TODO перенести это в тесты, если буду делать
	@Override
	public void run(String... args) {
//		createShapes();
//		testActions();
		getAllShapes();
	}

	private void createShapes() {
		shapeService.deleteAll();
		List<Shape> shapes = new ArrayList<>(ShapeCreateHelper.getShapesByFile(FILE_NAME).values());
		for (Shape shape : shapes) {
			shapeService.insert(shape);
		}
	}

	private void testActions() {
		// (8, 0); (9, 2); (1, 6); (0, 4) - Прямоугольник
		Shape shape = shapeService.insert(new Quadrangular(Arrays.asList(
				new Point2D(8, 0),
				new Point2D(9, 2),
				new Point2D(1, 6),
				new Point2D(0, 4)
		)));
		System.out.println(shape);

		System.out.println("rotateShape:\n" + shapeService.rotateShape(shape.getId(), 90));
		System.out.println("rotateShape:\n" + shapeService.rotateShape(shape.getId(), -90));

		System.out.println("moveShape:\n" + shapeService.moveShape(shape.getId(), 10, 10));
		System.out.println("moveShape:\n" + shapeService.moveShape(shape.getId(), -10, -10));

		System.out.println("increase:\n" + shapeService.increase(shape.getId(), 5));
		System.out.println("reduce:\n" + shapeService.reduce(shape.getId(), 5));
	}

	private void getAllShapes() {
		log.info("Start shapeRepository.findAll(): " + getRuntime(shapeService::getAll) + "ms");
		List<Shape> shapes = shapeService.getAll();
		shapes.stream().map(shape -> "id: " + shape.getId() + " " + shape).forEach(System.out::println);
	}
}
