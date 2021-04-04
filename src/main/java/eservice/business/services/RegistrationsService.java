package eservice.business.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Car;
import eservice.business.core.Registration;

import java.util.*;
import java.util.stream.Collectors;

public class RegistrationsService implements RegistrationsServiceable {
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference reference;
    private final NotificationsListener<String> registrationsListener;
    private final Set<String> ids;

    public RegistrationsService(NotificationsListener<String> registrationsListener) {
        this.registrationsListener = registrationsListener;
        reference = db.collection("registrations");
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

    @Override
    public List<UpdatableRegistration> getRegistrations(NotificationsListener<Registration> registrationListener) {
        return ids.stream().map(x -> new UpdatableRegistration(x, registrationListener)).collect(Collectors.toList());
    }

    @Override
    public List<String> getRegistrations() {
        return new ArrayList<>(ids);
    }

    @Override
    public void accept(Registration registration) {
        registration.setStatus("accepted");
        update(registration);
    }

    @Override
    public void deny(Registration registration) {
        registration.setStatus("denied");
        update(registration);
    }

    @Override
    public void update(Registration registration) {
        reference.document(registration.getId()).set(registration);//.update(registration.getChangesFields());
    }

    public void add(Registration registration) {
        reference.add(registration);
    }

    @Override
    public void finish(Registration registration) {
        registration.setStatus("done");
        update(registration);
    }
}
