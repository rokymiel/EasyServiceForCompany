package eservice.business.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Client;
import eservice.business.core.Registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsService implements ClientsServiceable {

    private final Firestore db = FirestoreClient.getFirestore();
    private CollectionReference reference;
    private HashMap<String, Client> clientMap = new HashMap<>();

    public ClientsService(RegistrationsServiceable registrationsService) {
        reference = db.collection("clients");
        reference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (queryDocumentSnapshots != null) {
                clients(queryDocumentSnapshots.getDocumentChanges());
            }
        });
    }

    @Override
    public List<Client> getClients() {
        return List.copyOf(clientMap.values());
    }

    @Override
    public boolean removeClientById(String id) {
        return false;
    }

    private void clients(List<DocumentChange> documentChanges) {
        for (DocumentChange document : documentChanges) {
            Client client = document.getDocument().toObject(Client.class);
            switch (document.getType()) {
                case ADDED, MODIFIED:
                    clientMap.put(client.getId(), client);
                    break;
                case REMOVED:
                    clientMap.remove(client.getId());
                    break;
            }
        }
    }
}
