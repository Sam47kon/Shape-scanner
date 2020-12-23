package com.app.restapp.config;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.shape.Shape;
import com.app.restapp.model.shape.polygon.Polygon;
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
		String uri = "mongodb+srv://Sam47kon:4747@cluster0.isrzl.mongodb.net/shape-app?retryWrites=true&w=majority";
		try (MongoClient mongoClient = MongoClients.create(uri)) {
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

	// TODO перенести это в тесты
	@Override
	public void run(String... args) throws Exception {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			points.add(new Point2D(i, i + 2));
		}

		// init 5 shapes
		log.info("Start shapeRepository.deleteAll(): " + getRuntime(shapeService::deleteAll) + "ms");

		log.info("Start shapeRepository.save 5 shapes: " + getRuntime(() -> {
			for (long i = 0; i < 5; i++) {
				shapeService.insert(new Polygon(points));
			}
		}) + "ms");

		// (8, 0); (9, 2); (1, 6); (0, 4) - Прямоугольник
		shapeService.insert(new Quadrangular(Arrays.asList(
				new Point2D(8, 0),
				new Point2D(9, 2),
				new Point2D(1, 6),
				new Point2D(0, 4)
		)));

		log.info("Start shapeRepository.findAll(): " + getRuntime(shapeService::getAll) + "ms");
		List<Shape> shapes = shapeService.getAll();
		shapes.stream().map(shape -> "id: " + shape.getId() + " " + shape).forEach(System.out::println);

//		log.info("shapeService.deleteById(shapes.get(0).getId()): ");
//		shapeService.deleteById(shapes.get(0).getId());
	}
}
