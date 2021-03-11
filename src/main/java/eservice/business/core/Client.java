package eservice.business.core;

import com.google.cloud.firestore.annotation.DocumentId;

import java.util.List;

public class Client {

    @DocumentId
    private final String Id;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private List<Car> cars;

    public Client(String id, String name, String surname, String patronymic, String phoneNumber, List<Car> cars) {
        Id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.cars = cars;
    }

    public String getId() {
        return Id;
    }

}
