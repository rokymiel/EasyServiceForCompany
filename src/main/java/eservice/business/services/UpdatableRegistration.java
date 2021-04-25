package eservice.business.services;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firebase.cloud.FirestoreClient;
import com.sun.javafx.binding.ExpressionHelper;
import eservice.business.core.Client;
import eservice.business.core.Mileage;
import eservice.business.core.Registration;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


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
        setNonChangesFields();
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
                        System.out.println("SET");

                        registration.set(reg);
                        this.registrationListener.ifPresent(x -> x.add(this, reg));
//                        registrationListener.add(registration.get());
                        break;
                    case MODIFIED:
                        System.out.println("MOD");
                        Registration updatedRegistration = documentSnapshots.toObject(Registration.class);
                        updatedRegistration.setClient(registration.getValue().getClient());
//                        registration.set(updatedRegistration);
//                        registration.getValue().setSas("Asa");
//                        registration.getValue().setDescription("SEEX");

                        registration.setValue(updatedRegistration);

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

    public void verify(Mileage mileage) {
        mileage.setVerified(true);
        client.verify(mileage, registration.getValue().getCarId());
    }

    public void addMileage(Mileage mileage) {
        client.addMileage(mileage, registration.getValue().getCarId());
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

    public SimpleObjectProperty<Registration> getValue() {
        return registration;
    }

    private Map<String, Object> changesFields;

    public Map<String, Object> getChangesFields() {
        return changesFields;
    }

    public void setNonChangesFields() {
        changesFields = new HashMap<>();
    }

    public void setDateOfRegistration(Timestamp dateOfRegistration) {
        changesFields.put(Registration.RegistrationFiled.DATE_OF_REGISTRATION, dateOfRegistration);
    }


    public void setDescription(String description) {
        changesFields.put(Registration.RegistrationFiled.DESCRIPTION, description);
    }

    public void setTypeOfWorks(String typeOfWorks) {
        changesFields.put(Registration.RegistrationFiled.TYPE_OF_WORKS, typeOfWorks);
    }

    public void setCost(Double cost) {
        changesFields.put(Registration.RegistrationFiled.COST, cost);
    }


    public void setTimeOfWorks(Timestamp timeOfWorks) {
        changesFields.put(Registration.RegistrationFiled.TIME_OF_WORKS, timeOfWorks);

    }

    public void setStatus(String status) {
        changesFields.put(Registration.RegistrationFiled.STATUS, status);
    }

    public void update() {
        if (changesFields.isEmpty()) {
            return;
        }

        reference.update(changesFields);
        setNonChangesFields();
    }
}
