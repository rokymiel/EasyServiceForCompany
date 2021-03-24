package eservice.business.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Car;
import eservice.business.core.Client;

import java.util.ArrayList;
import java.util.List;

public class UpdatableClient {
    private Client client;
    private final String clientId;
    private final Firestore db = FirestoreClient.getFirestore();
    private DocumentReference reference;
    private boolean isFirst = true;
    private NotificationsListener<Client> clientListener;
    private NotificationsListener<Car> carListener;

    public UpdatableClient(String clientId, NotificationsListener<Client> clientListener, NotificationsListener<Car> carListener) {
        this.clientListener = clientListener;
        this.carListener = carListener;
        this.clientId = clientId;
        reference = db.collection("users").document(clientId);
        reference.addSnapshotListener((documentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (documentSnapshots != null) {
                switch (getStatus(documentSnapshots)) {
                    case ADDED:
                        client = documentSnapshots.toObject(Client.class);
                        System.out.println("As");
                        listenCars();
                        System.out.println("As");
                        clientListener.add(client);
                        break;
                    case MODIFIED:
                        Client updatedClient = documentSnapshots.toObject(Client.class);
                        updatedClient.setCars(client.getCars());
                        client = updatedClient;
//                        if(updatedClient.get)
                        clientListener.modify(client);
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
        reference.collection("cars").addSnapshotListener(((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (queryDocumentSnapshots != null) {
                List<DocumentChange> changeList = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange document : changeList) {
                    Car car;
                    switch (document.getType()) {
                        case ADDED:
                            car = document.getDocument().toObject(Car.class);
                            client.addCar(car);
                            carListener.add(car);
                            break;
                        case MODIFIED:
                            car = document.getDocument().toObject(Car.class);
                            client.updateCar(car);
                            carListener.modify(car);
                            break;
                        case REMOVED:
                            client.removeCar(document.getDocument().getId());
                            break;
                    }
                }
            }
        }));
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

