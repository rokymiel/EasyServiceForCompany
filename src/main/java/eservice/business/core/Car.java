package eservice.business.core;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class Car {
    @DocumentId
    private String id;
    @PropertyName(CarFiled.MARK)
    private String mark;
    @PropertyName(CarFiled.MODEL)
    private String model;
    @PropertyName(CarFiled.GEAR)
    private String gear;
    @PropertyName(CarFiled.ENGINE_VOLUME)
    private Double engineVolume;
    @PropertyName(CarFiled.PRODUCTION_YEAR)
    private Integer productionYear;

    public String getId() {
        return id;
    }

    @PropertyName(CarFiled.MARK)
    public String getMark() {
        return mark;
    }

    @PropertyName(CarFiled.MODEL)
    public String getModel() {
        return model;
    }

    @PropertyName(CarFiled.GEAR)
    public String getGear() {
        return gear;
    }

    @PropertyName(CarFiled.ENGINE_VOLUME)
    public Double getEngineVolume() {
        return engineVolume;
    }

    @PropertyName(CarFiled.PRODUCTION_YEAR)
    public Integer getProductionYear() {
        return productionYear;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", gear='" + gear + '\'' +
                ", engineVolume=" + engineVolume +
                ", productionYear=" + productionYear +
                '}';
    }
}

final class CarFiled {
    public final static String MARK = "mark";
    public final static String MODEL = "model";
    public final static String GEAR = "gear";
    public final static String ENGINE_VOLUME = "engine_volume";
    public final static String PRODUCTION_YEAR = "production_year";
}
