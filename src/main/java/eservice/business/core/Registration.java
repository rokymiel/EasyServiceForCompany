package eservice.business.core;

import java.util.Date;

public class Registration {
    private final String Id;
    private final String ClientId;
    private final String CarId;
    private Date dateOfCreation;
    private Date dateOfRegistration;
    private String description;
    private String typeOfWorks;
    private Integer cost;
    private Integer timeOfWorks;
    private String status;

    public Registration(String id, String clientId, String carId, Date dateOfCreation, Date dateOfRegistration, String description, String typeOfWorks, Integer cost, Integer timeOfWorks) {
        Id = id;
        ClientId = clientId;
        CarId = carId;
        this.dateOfCreation = dateOfCreation;
        this.dateOfRegistration = dateOfRegistration;
        this.description = description;
        this.typeOfWorks = typeOfWorks;
        this.cost = cost;
        this.timeOfWorks = timeOfWorks;
    }
}
