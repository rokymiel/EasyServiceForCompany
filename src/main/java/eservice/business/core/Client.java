package eservice.business.core;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.List;

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
    private List<Car> cars;

    public Client(String id, String name, String surname, String patronymic,
                  String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
//        this.cars = cars;
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
    //    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public void setPatronymic(String patronymic) {
//        this.patronymic = patronymic;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

//    public void setCars(List<Car> cars) {


    public List<Car> getCars() {
        return cars;
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
                ", cars=" + cars +
                '}';
    }

//        this.cars = cars;
//    }

}

final class ClientFiled {
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String PATRONYMIC = "patronymic";
    public final static String PHONE_NUMBER = "phone_number";
}