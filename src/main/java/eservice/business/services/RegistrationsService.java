package eservice.business.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;

public class RegistrationsService {
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference fullReference;
    private final Query reference;
    private final NotificationsListener<String> registrationsListener;
    private final Set<String> ids;

    public RegistrationsService(String serviceId, NotificationsListener<String> registrationsListener) {
        this.registrationsListener = registrationsListener;
        fullReference = db.collection("registrations");
        reference = fullReference.whereEqualTo("service_id", serviceId);
        ids = new HashSet<>();
        reference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (queryDocumentSnapshots != null) {
                List<DocumentChange> changeList = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange document : changeList) {
                    switch (document.getType()) {
                        case ADDED:
                            ids.add(document.getDocument().getId());
                            this.registrationsListener.add(document.getDocument().getId());
                            break;
                        case REMOVED:
                            ids.remove(document.getDocument().getId());
                            this.registrationsListener.remove(document.getDocument().getId());
                            break;
                    }
                }
            }

        });

    }

}
