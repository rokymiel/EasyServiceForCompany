package eservice.business.core;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Client {

    @DocumentId
    private String id;
    @PropertyName(ClientFiled.NAME)
    private String name;
    @PropertyName(ClientFiled.SURNAME)
    private String surname;
    @PropertyName(ClientFiled.PATRONYMIC)
    private String patronymic;
    @PropertyName(ClientFiled.PHONE_NUMBER)
    private String phoneNumber;
    @PropertyName(ClientFiled.DATE_OF_BIRTH)
    private Timestamp dateOfBirth;
    private List<Car> cars = new ArrayList<>();
    @PropertyName(ClientFiled.REGISTRATIONS)
    private List<String> registrations;

    public Client(String id, String name, String surname, String patronymic,
                  String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
//        this.cars = cars;
    }

    private Client(String id, String name, String surname, String patronymic, String phoneNumber, Timestamp dateOfBirth, List<Car> cars, List<String> registrations) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.cars = cars;
        this.registrations = registrations;
    }

    public Client() {

    }

    public String getId() {
        return id;
    }

    @PropertyName(ClientFiled.NAME)
    public String getName() {
        return name;
    }

    @PropertyName(ClientFiled.SURNAME)
    public String getSurname() {
        return surname;
    }

    @PropertyName(ClientFiled.PATRONYMIC)
    public String getPatronymic() {
        return patronymic;
    }

    @PropertyName(ClientFiled.PHONE_NUMBER)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName(ClientFiled.DATE_OF_BIRTH)
    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public List<String> getRegistrations() {
        return registrations;
    }

//    @PropertyName(ClientFiled.REGISTRATIONS)
//    public void setRegistrationsFromReference(List<DocumentReference> documentReference) {
//        System.out.println("AAAA");
//        for (DocumentReference document : documentReference) {
//            document.get().addListener();
//        }
//        this.registrations = null;
//    }

    public List<Car> getCars() {
        return cars;
    }

    public Car getCar(String id) {
        return cars.stream().filter(x -> x.getId().equals(id)).findFirst().get();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void updateCar(Car car) {
        cars.stream()
                .filter(x -> x.getId().equals(car.getId()))
                .findFirst().ifPresent(value -> cars.set(cars.indexOf(value), car));
    }

    public void removeCar(String id) {
        cars.removeIf(x -> x.getId().equals(id));
    }

    public void setCars(List<Car> cars) {
        this.cars = List.copyOf(cars);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", cars=" + cars +
                ", registrations=" + registrations +
                '}';
    }

    public String getFullName() {
        List<String> fullName = new ArrayList<>(List.of(getSurname(), getName()));
        if (getPatronymic() != null && !getPatronymic().isBlank()) {
            fullName.add(getPatronymic());
        }
        return String.join(" ", fullName);
    }

    @Override
    public Object clone() {
        return new Client(id, name, surname, patronymic, phoneNumber, dateOfBirth, cars, registrations);
    }
    //        this.cars = cars;
//    }

}

final class ClientFiled {
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String PATRONYMIC = "patronymic";
    public final static String PHONE_NUMBER = "phone_number";
    public final static String DATE_OF_BIRTH = "date_of_birth";
    public final static String REGISTRATIONS = "registrations";

    private ClientFiled() {
    }

}