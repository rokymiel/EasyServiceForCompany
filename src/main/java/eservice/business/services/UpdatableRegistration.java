package eservice.business.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.sun.javafx.binding.ExpressionHelper;
import eservice.business.core.Client;
import eservice.business.core.Registration;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Optional;


public class UpdatableRegistration {
//    private SimpleObjectProperty<Registration> registration = new SimpleObjectProperty<>(this, "registration");

    private SimpleObjectProperty<Registration> registration = new SimpleObjectProperty<>();

    //    private
    private final String registrationId;
    private final Firestore db = FirestoreClient.getFirestore();
    private Optional<NotificationsListener<Registration>> registrationListener = Optional.empty();
    private DocumentReference reference;
    private boolean isFirst = true;


    private ExpressionHelper<Registration> helper = null;

    public UpdatableRegistration(String registrationId, NotificationsListener<Registration> registrationListener) {
        this.registrationId = registrationId;
        this.registrationListener = Optional.ofNullable(registrationListener);
        reference = db.collection("registrations").document(registrationId);
        reference.addSnapshotListener((documentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (documentSnapshots != null) {
                switch (getStatus(documentSnapshots)) {
                    case ADDED:
                        Registration reg = documentSnapshots.toObject(Registration.class);
                        if (reg.getClientId() != null) {
                            System.out.println(reg.getClientId());
                            client = new UpdatableClient(reg.getClientId(), (observableValue, client, newClient) -> {
                                Registration re = (Registration) registration.getValue().clone();
                                System.out.println("AAAAAASDADASD");
                                re.setClient(newClient);
                                registration.set(re);
                            });
                        }
//                        Re observableRegistration = new ObservableRegistration();
//                        client =  new UpdatableClient()
                        registration.set(reg);
                        this.registrationListener.ifPresent(x -> x.add(this, reg));
//                        registrationListener.add(registration.get());
                        break;
                    case MODIFIED:
                        Registration updatedRegistration = documentSnapshots.toObject(Registration.class);
//                        registration.set(updatedRegistration);
//                        registration.getValue().setSas("Asa");
                        System.out.println("MEM");
//                        registration.getValue().setDescription("SEEX");
                        registration.set(updatedRegistration);
//                        registration.set(registration.getValue());
//                        registrationListener.modify(registration.get());
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

    private UpdatableClient client;

    public Registration getRegistration() {
        return registration.getValue();
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

//    @Override
//    public void addListener(ChangeListener<? super Registration> changeListener) {
//        helper = ExpressionHelper.addListener(this.helper, this, changeListener);
//    }
//
//    @Override
//    public void removeListener(ChangeListener<? super Registration> changeListener) {
//        this.helper = ExpressionHelper.removeListener(this.helper, changeListener);
//    }
//
//    @Override
//    public void addListener(InvalidationListener invalidationListener) {
//        helper = ExpressionHelper.addListener(this.helper, this, invalidationListener);
//    }
//
//    @Override
//    public void removeListener(InvalidationListener invalidationListener) {
//        this.helper = ExpressionHelper.removeListener(this.helper, invalidationListener);
//    }

    public SimpleObjectProperty<Registration> getValue() {
        return registration;
    }
}
