package com.app.restapp.config;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.point.Point2D;
import com.app.restapp.model.shape.polygon.Polygon;
import com.app.restapp.repository.PointRepository;
import com.app.restapp.repository.ShapeRepository;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.app.util.Utils.getRuntime;

@Slf4j
@Configuration
public class InitDB implements CommandLineRunner {

	private final PointRepository pointRepository;
	private final ShapeRepository shapeRepository;


	@Autowired
	public InitDB(PointRepository pointRepository, ShapeRepository shapeRepository) {
		this.pointRepository = pointRepository;
		this.shapeRepository = shapeRepository;
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

	@Override
	public void run(String... args) throws Exception {
		// init 5 Points
		log.info("Start pointRepository.deleteAll(): " + getRuntime(pointRepository::deleteAll) + "ms");

		log.info("Start pointRepository.save 5 points: " + getRuntime(() -> {
			for (int i = 0; i < 5; i++) {
				pointRepository.save(new Point2D(i, i + 2));
			}
		}) + "ms");

		log.info("Start pointRepository.findAll(): " + getRuntime(pointRepository::findAll) + "ms");
		List<Point> points = pointRepository.findAll();
		points.stream().map(point -> "id: " + point.getId() + " " + point).forEach(System.out::println);


		// init 5 shapes
		log.info("Start shapeRepository.deleteAll(): " + getRuntime(shapeRepository::deleteAll) + "ms");

		log.info("Start shapeRepository.save 5 shapes: " + getRuntime(() -> {
			for (long i = 0; i < 5; i++) {
				shapeRepository.save(new Polygon(points));
			}
		}) + "ms");

		// FIXME Cannot set property points because no setter, no wither and it's not part of the persistence constructor protected
		log.info("Start shapeRepository.findAll(): " + getRuntime(shapeRepository::findAll) + "ms");
		shapeRepository.findAll().stream().map(shape -> "id: " + shape.getId() + " " + shape).forEach(System.out::println);
	}
}
