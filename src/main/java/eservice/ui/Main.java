package eservice.ui;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Car;
import eservice.business.core.Mileage;
import eservice.business.services.ClientsService;
import eservice.business.services.NotificationsListener;
import eservice.business.services.UpdatableClient;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.util.List;

public class Main implements NotificationsListener {
    public static void main(String[] args) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId("easyservice-e5e67")
                .build();
        FirebaseApp.initializeApp(options);
//
//
//        ClientsService clientsService = new ClientsService();
        Main m = new Main();
        Firestore db = FirestoreClient.getFirestore();
//        UpdatableClient updatableClient = new UpdatableClient("wiFF7jMoyVO8Tcij39a0", m, m);
        DocumentReference reference = db.collection("users").document("F0BmqjfrZXTXQyzdJsJ6eQr3ney1");
        Car carR = new Car("QWE", "MA", "MO", "GE","BOD", 2.0, 2000, List.of(new Mileage(Timestamp.now(),true,20000)));
//
        reference.set(carR);
        while (true) {
//            System.out.pri ntln(clientsService.getClients());
        }


//        SimpleIntegerProperty one = new SimpleIntegerProperty(1);
//        SimpleIntegerProperty two = new SimpleIntegerProperty(0);
//
//        // the binding we are interested in
//        NumberBinding sum = one.add(two);
//        sum.addListener(observable -> System.out.println("invalidated"));
//
//        // if you add a value change listener, the value will NOT be evaluated lazy anymore
//        sum.addListener((observable, oldValue, newValue) -> System.out.println("value changed from " + oldValue + " to " + newValue));
//
//        // is valid, since nothing changed so far
//        System.out.println("sum valid: " + sum.isValid());
//        // will invalidate the sum binding
//        two.set(1);
//        one.set(2); // invalidation event NOT fired here!
//        System.out.println("sum valid: " + sum.isValid());
//        // will validate the sum binding, since it is calculated lazy when getting the value
//        System.out.println("sum: " + sum.getValue());
//        System.out.println("sum valid: " + sum.isValid());
    }

    static void authImplicit() {
        // If you don't specify credentials when constructing the client, the client library will
        // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }

    @Override
    public void add(Object item) {
        System.out.println("Added " + item);
    }

    @Override
    public void modify(Object item) {
        System.out.println("Mofi " + item);
    }

    @Override
    public void remove(Object item) {
        System.out.println("removed " + item);

    }
}
