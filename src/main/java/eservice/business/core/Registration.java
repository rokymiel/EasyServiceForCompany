package eservice.business.core;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registration {
    @DocumentId
    private String id;
    @PropertyName(RegistrationFiled.CLIENT_ID)
    private String clientId;
    @PropertyName(RegistrationFiled.CAR_ID)
    private String carId;
    @PropertyName(RegistrationFiled.DATE_OF_CREATION)
    private Timestamp dateOfCreation;
    @PropertyName(RegistrationFiled.DATE_OF_REGISTRATION)
    private Timestamp dateOfRegistration;
    @PropertyName(RegistrationFiled.DESCRIPTION)
    private String description;
    @PropertyName(RegistrationFiled.TYPE_OF_WORKS)
    private String typeOfWorks;
    @PropertyName(RegistrationFiled.COST)
    private Double cost;
    @PropertyName(RegistrationFiled.TIME_OF_WORKS)
    private Timestamp timeOfWorks;
    @PropertyName(RegistrationFiled.STATUS)
    private String status;
    @PropertyName(RegistrationFiled.NOTES)
    private String notes;
    @Exclude
    private Client client;


    private final Map<String, Object> changesFields;

    public Registration() {
        changesFields = new HashMap<>();
    }

    private Registration(String id, String clientId, String carId, Timestamp dateOfCreation,
                         Timestamp dateOfRegistration, String description, String typeOfWorks,
                         Double cost, Timestamp timeOfWorks, String status, String notes, Client client, Map<String, Object> changesFields) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.dateOfCreation = dateOfCreation;
        this.dateOfRegistration = dateOfRegistration;
        this.description = description;
        this.typeOfWorks = typeOfWorks;
        this.cost = cost;
        this.timeOfWorks = timeOfWorks;
        this.status = status;
        this.notes = notes;
        this.client = client;
        this.changesFields = changesFields;
    }

    public Registration(String id, String clientId, String carId, Timestamp dateOfCreation, Timestamp dateOfRegistration, String description, String typeOfWorks, Double cost, Timestamp timeOfWorks, String status, String notes) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.dateOfCreation = dateOfCreation;
        this.dateOfRegistration = dateOfRegistration;
        this.description = description;
        this.typeOfWorks = typeOfWorks;
        this.cost = cost;
        this.timeOfWorks = timeOfWorks;
        this.status = status;
        this.notes = notes;
        changesFields = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    @PropertyName(RegistrationFiled.CLIENT_ID)
    public String getClientId() {
        return clientId;
    }

    @PropertyName(RegistrationFiled.CAR_ID)
    public String getCarId() {
        return carId;
    }

    @PropertyName(RegistrationFiled.DATE_OF_CREATION)
    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    @PropertyName(RegistrationFiled.DATE_OF_REGISTRATION)
    public Timestamp getDateOfRegistration() {
        return dateOfRegistration;
    }

    @Exclude()
    public void setDateOfRegistration(Timestamp dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
        changesFields.put(RegistrationFiled.DATE_OF_REGISTRATION, dateOfRegistration);
    }

    @PropertyName(RegistrationFiled.DESCRIPTION)
    public String getDescription() {
        return description;
    }

    @Exclude()
    public void setDescription(String description) {
        this.description = description;
        changesFields.put(RegistrationFiled.DESCRIPTION, description);
    }

    @PropertyName(RegistrationFiled.TYPE_OF_WORKS)
    public String getTypeOfWorks() {
        return typeOfWorks;
    }

    @Exclude()
    public void setTypeOfWorks(String typeOfWorks) {
        this.typeOfWorks = typeOfWorks;
        changesFields.put(RegistrationFiled.TYPE_OF_WORKS, typeOfWorks);
    }

    @PropertyName(RegistrationFiled.COST)
    public Double getCost() {
        return cost;
    }

    @Exclude()
    public void setCost(Double cost) {
        this.cost = cost;
        changesFields.put(RegistrationFiled.COST, cost);
    }

    @PropertyName(RegistrationFiled.TIME_OF_WORKS)
    public Timestamp getTimeOfWorks() {
        return timeOfWorks;
    }

    @Exclude()
    public void setTimeOfWorks(Timestamp timeOfWorks) {
        this.timeOfWorks = timeOfWorks;
        changesFields.put(RegistrationFiled.TIME_OF_WORKS, timeOfWorks);
    }

    @PropertyName(RegistrationFiled.STATUS)
    public String getStatus() {
        return status;
    }

    @Exclude()
    public void setStatus(String status) {
        this.status = status;
        changesFields.put(RegistrationFiled.STATUS, status);
    }

    @PropertyName(RegistrationFiled.NOTES)
    public String getNotes() {
        return notes;
    }

    public Map<String, Object> getChangesFields() {
        return changesFields;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", carId='" + carId + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfRegistration=" + dateOfRegistration +
                ", description='" + description + '\'' +
                ", typeOfWorks='" + typeOfWorks + '\'' +
                ", cost=" + cost +
                ", timeOfWorks=" + timeOfWorks +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", changesFields=" + changesFields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getClientId(), that.getClientId()) &&
                Objects.equals(getCarId(), that.getCarId()) &&
                Objects.equals(getDateOfCreation(), that.getDateOfCreation()) &&
                Objects.equals(getDateOfRegistration(), that.getDateOfRegistration()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getTypeOfWorks(), that.getTypeOfWorks()) &&
                Objects.equals(getCost(), that.getCost()) &&
                Objects.equals(getTimeOfWorks(), that.getTimeOfWorks()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getNotes(), that.getNotes()) &&
                Objects.equals(getClient(), that.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientId(), getCarId(), getDateOfCreation(), getDateOfRegistration(), getDescription(), getTypeOfWorks(), getCost(), getTimeOfWorks(), getStatus(), getNotes(), getClient());
    }

    @Override
    public Object clone() {
        return new Registration(id, clientId, carId, dateOfCreation, dateOfRegistration,
                description, typeOfWorks, cost, timeOfWorks, status, notes, client, changesFields);
    }
}

final class RegistrationFiled {
    public final static String ID = "id";
    public final static String CLIENT_ID = "client_id";
    public final static String CAR_ID = "car_id";
    public final static String DATE_OF_CREATION = "date_of_creation";
    public final static String DATE_OF_REGISTRATION = "date_of_registration";
    public final static String DESCRIPTION = "description";
    public final static String TYPE_OF_WORKS = "type_of_works";
    public final static String COST = "cost";
    public final static String TIME_OF_WORKS = "time_of_works";
    public final static String STATUS = "status";
    public final static String NOTES = "notes";

    private RegistrationFiled() {
    }
}
