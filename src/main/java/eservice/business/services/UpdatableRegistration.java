package eservice.business.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Client;
import eservice.business.core.Registration;

public class UpdatableRegistration {
    private Registration registration;
    private final String registrationId;
    private final Firestore db = FirestoreClient.getFirestore();
    private final NotificationsListener<Registration> registrationListener;
    private DocumentReference reference;
    private boolean isFirst = true;

    public UpdatableRegistration(String registrationId, NotificationsListener<Registration> registrationListener) {
        this.registrationId = registrationId;
        this.registrationListener = registrationListener;
        reference = db.collection("registrations").document(registrationId);
        reference.addSnapshotListener((documentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (documentSnapshots != null) {
                switch (getStatus(documentSnapshots)) {
                    case ADDED:
                        registration = documentSnapshots.toObject(Registration.class);
                        registrationListener.add(registration);
                        break;
                    case MODIFIED:
                        Registration updatedRegistration = documentSnapshots.toObject(Registration.class);
                        registration = updatedRegistration;
                        registrationListener.modify(registration);
                        break;
                    case REMOVED:
                        registration = null;
                        break;
                    case DO_NOT_EXIST:
                        break;
                }
            }
            isFirst = false;
        });
    }


    private ObjectStatus getStatus(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            if (isFirst) return ObjectStatus.ADDED;
            else return ObjectStatus.MODIFIED;
        } else {
            if (isFirst) return ObjectStatus.DO_NOT_EXIST;
            else return ObjectStatus.REMOVED;
        }
    }
}
