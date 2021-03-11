package eservice.business.core;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registration {
    @DocumentId
    private final String id;
    @PropertyName(RegistrationFiled.CLIENT_ID)
    private final String clientId;
    @PropertyName(RegistrationFiled.CAR_ID)
    private final String carId;
    @PropertyName(RegistrationFiled.DATE_OF_CREATION)
    private final Date dateOfCreation;
    @PropertyName(RegistrationFiled.DATE_OF_REGISTRATION)
    private Date dateOfRegistration;
    @PropertyName(RegistrationFiled.DESCRIPTION)
    private String description;
    @PropertyName(RegistrationFiled.TYPE_OF_WORKS)
    private String typeOfWorks;
    @PropertyName(RegistrationFiled.COST)
    private Integer cost;
    @PropertyName(RegistrationFiled.TIME_OF_WORKS)
    private Integer timeOfWorks;
    @PropertyName(RegistrationFiled.STATUS)
    private String status;

    private final Map<String, Object> changesFields = new HashMap<>();

    public Registration(String id, String clientId, String carId, Date dateOfCreation, Date dateOfRegistration, String description, String typeOfWorks, Integer cost, Integer timeOfWorks, String status) {
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
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCarId() {
        return carId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
        changesFields.put(RegistrationFiled.DATE_OF_REGISTRATION, dateOfRegistration);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        changesFields.put(RegistrationFiled.DESCRIPTION, description);
    }

    public String getTypeOfWorks() {
        return typeOfWorks;
    }

    public void setTypeOfWorks(String typeOfWorks) {
        this.typeOfWorks = typeOfWorks;
        changesFields.put(RegistrationFiled.TYPE_OF_WORKS, typeOfWorks);
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
        changesFields.put(RegistrationFiled.COST, cost);
    }

    public Integer getTimeOfWorks() {
        return timeOfWorks;
    }

    public void setTimeOfWorks(Integer timeOfWorks) {
        this.timeOfWorks = timeOfWorks;
        changesFields.put(RegistrationFiled.TIME_OF_WORKS, timeOfWorks);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        changesFields.put(RegistrationFiled.STATUS, status);
    }

    public Map<String, Object> getChangesFields() {
        return changesFields;
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

    private RegistrationFiled() {
    }
}
