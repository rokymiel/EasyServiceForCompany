package eservice.business.core;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.HashMap;
import java.util.Map;

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
    private Integer timeOfWorks;
    @PropertyName(RegistrationFiled.STATUS)
    private String status;
    @PropertyName(RegistrationFiled.NOTES)
    private String notes;

    private final Map<String, Object> changesFields = new HashMap<>();

    public Registration() {
    }

    public Registration(String id, String clientId, String carId, Timestamp dateOfCreation, Timestamp dateOfRegistration, String description, String typeOfWorks, Double cost, Integer timeOfWorks, String status, String notes) {
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
    public Integer getTimeOfWorks() {
        return timeOfWorks;
    }
    @Exclude()
    public void setTimeOfWorks(Integer timeOfWorks) {
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
