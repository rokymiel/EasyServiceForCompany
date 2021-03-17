package eservice.business.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.ClientsCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClientsService implements ClientsServiceable {

    private final Firestore db = FirestoreClient.getFirestore();
    private CollectionReference reference;
    private HashMap<String, Client> clientMap = new HashMap<>();

    public ClientsService() {
        reference = db.collection("users");
        reference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            if (queryDocumentSnapshots != null) {
                try {
                    clients(queryDocumentSnapshots.getDocumentChanges());
                } catch (ExecutionException | InterruptedException executionException) {
                    executionException.printStackTrace(); // TODO Обработать исключения
                }
            }
        });
    }

    @Override
    public List<Client> getClients() {
        return new ArrayList<>(clientMap.values());
    }

    @Override
    public boolean removeClientById(String id) {
        return false;
    }

    private void clients(List<DocumentChange> documentChanges) throws ExecutionException, InterruptedException {
        for (DocumentChange document : documentChanges) {
            Map<String, Object> d = document.getDocument().getData();
            Client client = document.getDocument().toObject(Client.class);
//            for (CollectionReference ref: document.getDocument().getReference().listCollections()){
//                System.out.println(ref.getId());
//            }
            List<Car> cars = new ArrayList<>();
            QuerySnapshot snapshot = document.getDocument().getReference().collection("cars").get().get();

            for (QueryDocumentSnapshot carDoc : snapshot.getDocuments()) {
                cars.add(carDoc.toObject(Car.class));
            }

            client.setCars(cars);

//            document.getDocument().get
//            Client client = ClientsCreator.create(document.getDocument());
            System.out.println(client);
//            switch (document.getType()) {
//                case ADDED:
//                case MODIFIED:
//                    clientMap.put(client.getId(), client);
//                    break;
//                case REMOVED:
//                    clientMap.remove(client.getId());
//                    break;
//            }
            System.out.println(d);
        }
    }
}
