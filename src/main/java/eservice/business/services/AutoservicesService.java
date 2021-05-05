package eservice.business.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class AutoservicesService {
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference reference;

    public AutoservicesService() {
        reference = db.collection("services");
    }


    public void getService(String code, Consumer<Service> consumer) {
        CompletableFuture.runAsync(()-> {
            try {
                consumer.accept(reference.document(code).get().get().toObject(Service.class));
            } catch (Exception e) {
                consumer.accept(null);
            }
        });
    }
}
