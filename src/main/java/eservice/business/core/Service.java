package eservice.business.core;

import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.List;

public class Service {
    @DocumentId
    private String id;
    @PropertyName(ServiceField.NAME)
    private String name;
    @PropertyName(ServiceField.ADDRESS)
    private String address;
    @PropertyName(ServiceField.LOCATION)
    private GeoPoint location;
    @PropertyName(ServiceField.PHONE)
    private String phone;
    @PropertyName(ServiceField.WORK_TIME)
    private List<String> workTime;
    @PropertyName(ServiceField.WORK_TYPES)
    private List<String> workTypes;

    public String getId() {
        return id;
    }

    @PropertyName(ServiceField.NAME)
    public String getName() {
        return name;
    }

    @PropertyName(ServiceField.ADDRESS)
    public String getAddress() {
        return address;
    }

    @PropertyName(ServiceField.LOCATION)
    public GeoPoint getLocation() {
        return location;
    }

    @PropertyName(ServiceField.PHONE)
    public String getPhone() {
        return phone;
    }

    @PropertyName(ServiceField.WORK_TIME)
    public List<String> getWorkTime() {
        return workTime;
    }

    @PropertyName(ServiceField.WORK_TYPES)
    public List<String> getWorkTypes() {
        return workTypes;
    }

    public static final class ServiceField {
        public final static String NAME = "name";
        public final static String ADDRESS = "address";
        public final static String LOCATION = "location";
        public final static String PHONE = "phone";
        public final static String WORK_TIME = "work_time";
        public final static String WORK_TYPES = "work_types";

        private ServiceField() {
        }
    }
}
