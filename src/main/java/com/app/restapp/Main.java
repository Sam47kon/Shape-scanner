package com.app.restapp;

import com.app.restapp.model.point.Point2D;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@EntityScan("com.app.rest.model")
//@EnableMongoRepositories("com.app.restapp.repository")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
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
}
