package eservice.business.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Registration;

import java.util.Map;

public class RegistrationsService implements RegistrationsServiceable {
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference reference;

    public RegistrationsService() {
        reference = db.collection("registrations");

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
        reference.document(registration.getId()).update(registration.getChangesFields());
    }

    @Override
    public void finish(Registration registration) {
        registration.setStatus("done");
        update(registration);
    }
}
