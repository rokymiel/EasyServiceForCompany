package eservice.business.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Car;
import eservice.business.core.Client;

import java.util.*;
import java.util.stream.Collectors;

public class ClientsService implements ClientsServiceable {
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference reference;
    private final NotificationsListener<String> clientsListener;
    private final Set<String> ids;

    public ClientsService(NotificationsListener<String> clientsListener) {
        this.clientsListener = clientsListener;
        reference = db.collection("users");
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
                            this.clientsListener.add(document.getDocument().getId());
                            break;
                        case REMOVED:
                            ids.remove(document.getDocument().getId());
                            this.clientsListener.remove(document.getDocument().getId());
                            break;
                    }
                }
            }

        });

    }


    @Override
    public List<UpdatableClient> getClients(NotificationsListener<Client> clientListener, NotificationsListener<Car> carListener) {
        return ids.stream().map(x -> new UpdatableClient(x, clientListener, carListener)).collect(Collectors.toList());
    }

    @Override
    public List<String> getClientIds() {
        return new ArrayList<>(ids);
    }

    @Override
    public boolean removeClientById(String id) {
        return false;
    }
}
