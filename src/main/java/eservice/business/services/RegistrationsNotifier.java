package eservice.business.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Registration;

import java.util.ArrayList;
import java.util.List;

public class RegistrationsNotifier implements RegistrationsNotifierable {
    private List<NotificationsListener<Registration>> listeners;
    private RegistrationsServiceable registrationsService;
    private final Firestore db = FirestoreClient.getFirestore();
    private CollectionReference reference;

    public RegistrationsNotifier(RegistrationsServiceable registrationsService) {
        this.registrationsService = registrationsService;
        listeners = new ArrayList<>();
        reference = db.collection("registrations");
        reference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (queryDocumentSnapshots != null) {
                registrations(queryDocumentSnapshots.getDocumentChanges());
            }
        });
    }


    public void addListener(NotificationsListener<Registration> listener) {
        listeners.add(listener);
    }

    private void registrations(List<DocumentChange> documentChanges) {
        for (DocumentChange document : documentChanges) {
            Registration registration = document.getDocument().toObject(Registration.class);
            System.out.println(registration);
            switch (document.getType()) {
                case ADDED:
                    listeners.forEach(x -> x.add(registration));
                    break;
                case REMOVED:
                    listeners.forEach(x -> x.remove(registration));
                    break;
                case MODIFIED:
                    listeners.forEach(x -> x.modify(registration));
                    break;
            }
        }
    }
}
