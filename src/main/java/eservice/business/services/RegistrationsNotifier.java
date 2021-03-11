package eservice.business.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Registration;

import java.util.ArrayList;
import java.util.List;

public class RegistrationsNotifier implements RegistrationsNotifierable {
    private List<NotificationsListener> listeners;
    private RegistrationsServiceable registrationsService;
    private final Firestore db = FirestoreClient.getFirestore();
    private CollectionReference reference;

    public RegistrationsNotifier(RegistrationsServiceable registrationsService) {
        this.registrationsService = registrationsService;
        listeners = new ArrayList<NotificationsListener>();
        reference = db.collection("registrations");
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirestoreException e) {
                if (e != null) {
                    return;
                }
                registrations(queryDocumentSnapshots.getDocumentChanges());
            }
        });
    }


    public void addListener(NotificationsListener listener) {
        listeners.add(listener);
    }

    private void registrations(List<DocumentChange> documentChanges) {
        for (DocumentChange document : documentChanges) {
            switch (document.getType()) {
                case ADDED:
                    final Registration registration = document.getDocument().toObject(Registration.class);
                    listeners.forEach(x -> x.add(registration));
                    break;
                case REMOVED:
                    break;
                case MODIFIED:
                    break;
            }
        }
    }
}
