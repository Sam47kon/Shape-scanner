package com.app;

import com.app.helper.ShapeCreateHelper;
import com.app.main.Menu;
import com.app.model.point.Point;
import com.app.model.point.Point2D;
import com.app.rest.exception.PointNotFoundException;
import com.app.rest.repository.PointRepository;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.mongodb.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
//@EntityScan("com.app.model")
//@EnableMongoRepositories("com.app.rest.repository")
public class Main implements CommandLineRunner {

	private final PointRepository pointRepository;

	@Autowired
	public Main(PointRepository pointRepository) {
		this.pointRepository = pointRepository;
	}

	public static void main(String[] args) {
//		SpringApplication.run(Main.class, args);
		startMongoDB();
	}

	private static void startMenu() {
		final String FILE_NAME = "template.txt";
		Menu menu = new Menu(ShapeCreateHelper.getShapesByFile(FILE_NAME), FILE_NAME);
		menu.start();
	}

	private static void startMongoDB() {
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

	private static void startFirebase() {
		final String PATH_CREDENTIALS = "C:/Users/OTR-2000/Documents/Dok/ServiceAccountKey.json";
//		final String PATH_CREDENTIALS = "ServiceAccountKey.json";
		try {
			FileInputStream serviceAccount = new FileInputStream(PATH_CREDENTIALS);
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://shape-app-47.firebaseio.com")
					.build();
			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> future = db.collection("sampleData").document("inspiration").set(new Point2D(1, 2));

		try {
			System.out.println("Успешно загружено " + " время: " + future.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(String... args) throws Exception {
		pointRepository.deleteAll();

		for (long i = 1; i < 5; i++) {
			pointRepository.save(new Point2D(i, i + 2));

			long finalL = i;
			System.out.println(pointRepository.findById(i).orElseThrow(() -> new PointNotFoundException(finalL)));
		}
		for (Point point : pointRepository.findAll()) {
			System.out.println(point);
		}
	}
}
