package eservice.business.core;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.List;
import java.util.Map;

public class Car {
    @DocumentId
    private String id;
    @PropertyName(CarFiled.MARK)
    private String mark;
    @PropertyName(CarFiled.MODEL)
    private String model;
    @PropertyName(CarFiled.GEAR)
    private String gear;
    @PropertyName(CarFiled.BODY)
    private String body;
    @PropertyName(CarFiled.ENGINE_VOLUME)
    private Double engineVolume;
    @PropertyName(CarFiled.PRODUCTION_YEAR)
    private Integer productionYear;
    @PropertyName(CarFiled.MILEAGE)
    private List<Mileage> mileage;

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

    @PropertyName(CarFiled.BODY)
    public String getBody() {
        return body;
    }

    @PropertyName(CarFiled.ENGINE_VOLUME)
    public Double getEngineVolume() {
        return engineVolume;
    }

    @PropertyName(CarFiled.PRODUCTION_YEAR)
    public Integer getProductionYear() {
        return productionYear;
    }

    @PropertyName(CarFiled.MILEAGE)
    public List<Mileage> getMileage() {
        return mileage;
    }

    public void addMileage(Mileage mileage) {
        this.mileage.add(mileage);
    }

    public Car() {
    }

    private Car(String id, String mark, String model, String gear, String body, Double engineVolume, Integer productionYear) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.gear = gear;
        this.engineVolume = engineVolume;
        this.productionYear = productionYear;
        this.body = body;
    }

    public Car(String id, String mark, String model, String gear, String body, Double engineVolume, Integer productionYear, List<Mileage> mileage) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.gear = gear;
        this.engineVolume = engineVolume;
        this.productionYear = productionYear;
        this.body = body;
        this.mileage = mileage;
    }

    public String getCarName() {
        return getMark() + " " + getModel();
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

    @Override
    public Object clone() {
        return new Car(id, mark, model, gear, body, engineVolume, productionYear);
    }

    public static final class CarFiled {
        public final static String MARK = "mark";
        public final static String MODEL = "model";
        public final static String GEAR = "gear";
        public final static String BODY = "body";
        public final static String ENGINE_VOLUME = "engine";
        public final static String PRODUCTION_YEAR = "production_year";
        public final static String MILEAGE = "mileage";

        private CarFiled() {
        }
    }
}

