package eservice.business.core;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.PropertyName;

public final class Mileage {
    @PropertyName("date")
    private Timestamp date;
    @PropertyName("is_verified")
    private Boolean isVerified;
    @PropertyName("value")
    private Integer value;

    public Mileage() {

    }
    @PropertyName("date")
    public Timestamp getDate() {
        return date;
    }
    @PropertyName("is_verified")
    public Boolean getVerified() {
        return isVerified;
    }
    @PropertyName("value")
    public Integer getValue() {
        return value;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Mileage(Timestamp date, Boolean isVerified, Integer value) {
        this.date = date;
        this.isVerified = isVerified;
        this.value = value;
    }
}
