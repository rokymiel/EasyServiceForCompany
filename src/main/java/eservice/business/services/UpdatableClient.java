package eservice.business.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.Mileage;
import eservice.business.core.Token;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UpdatableClient {
    private SimpleObjectProperty<Client> client = new SimpleObjectProperty<>();
    private final String clientId;
    private final Firestore db = FirestoreClient.getFirestore();
    private DocumentReference reference;
    private boolean isFirst = true;
    private Optional<NotificationsListener<Client>> clientListener = Optional.empty();
    private Optional<NotificationsListener<Car>> carListener = Optional.empty();

    public UpdatableClient(String clientId, ChangeListener<? super Client> changeListener) {
        this(clientId, null, null);
        client.addListener(changeListener);
    }

    public UpdatableClient(String clientId, NotificationsListener<Client> clientListener, NotificationsListener<Car> carListener) {
        this.clientListener = Optional.ofNullable(clientListener);
        this.carListener = Optional.ofNullable(carListener);
        this.clientId = clientId;
        reference = db.collection("users").document(clientId);

        reference.addSnapshotListener((documentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (documentSnapshots != null) {
                switch (getStatus(documentSnapshots)) {
                    case ADDED:
                        client.set(documentSnapshots.toObject(Client.class));
                        listenCars();
                        listenTokens();
                        this.clientListener.ifPresent(x -> x.add(client.getValue()));
                        break;
                    case MODIFIED:
                        Client updatedClient = documentSnapshots.toObject(Client.class);
                        updatedClient.setCars(client.getValue().getCars());
                        System.out.println(updatedClient);
                        client.set(updatedClient);
                        this.clientListener.ifPresent(x -> x.modify(client.getValue()));
                        break;
                    case REMOVED:
                        client = null;
//                        clientListener.remove();
                        break;
                    case DO_NOT_EXIST:
                        break;
                }
            }
            isFirst = false;
        });
    }

    private void listenCars() {
        reference.collection("cars").addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (queryDocumentSnapshots != null) {
                List<DocumentChange> changeList = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange document : changeList) {
                    Car car;
                    Client upd;
                    switch (document.getType()) {
                        case ADDED:
                            car = document.getDocument().toObject(Car.class);
                            System.out.println("CAR " + car.getCarName());
                            System.out.println("getMileage " + car.getMileage());
                            upd = (Client) client.getValue().clone();
                            upd.addCar(car);
                            client.set(upd);
                            carListener.ifPresent(x -> x.add(car));
                            break;
                        case MODIFIED:
                            car = document.getDocument().toObject(Car.class);
                            upd = (Client) client.getValue().clone();
                            upd.updateCar(car);
                            client.set(upd);
                            carListener.ifPresent(x -> x.modify(car));
                            break;
                        case REMOVED:
                            upd = (Client) client.getValue().clone();
                            upd.removeCar(document.getDocument().getId());
                            client.set(upd);
                            break;
                    }
                }
            }
        });
    }

    private void listenTokens() {
        reference.collection("tokens").addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            System.out.println("TOOOOOKKK");
            if (queryDocumentSnapshots != null) {
                List<DocumentChange> changeList = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange document : changeList) {
                    Token token;
                    System.out.println(document.getDocument().getData());
                    switch (document.getType()) {
                        case ADDED:
                            token = document.getDocument().toObject(Token.class);
                            System.out.println(token);
                            client.getValue().addToken(token);
                            break;
                        case REMOVED:
                            token = document.getDocument().toObject(Token.class);
                            client.getValue().removeToken(token);
                            break;
                    }
                }
            }
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

    public synchronized void verify(Mileage mileage, String carId) {
        mileage.setVerified(true);
        reference.collection("cars").document(carId).update(Map.of(Car.CarField.MILEAGE, client.getValue().getCar(carId).getMileage()));
    }

    public synchronized void addMileage(Mileage mileage, String carId) {
        client.getValue().getCar(carId).addMileage(mileage);
        reference.collection("cars").document(carId).update(Map.of(Car.CarField.MILEAGE, client.getValue().getCar(carId).getMileage()));
    }


}

