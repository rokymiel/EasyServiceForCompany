package eservice.business.core;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.List;

public class Car {
    @DocumentId
    private String id;
    @PropertyName(CarField.MARK)
    private String mark;
    @PropertyName(CarField.MODEL)
    private String model;
    @PropertyName(CarField.GEAR)
    private String gear;
    @PropertyName(CarField.BODY)
    private String body;
    @PropertyName(CarField.ENGINE_VOLUME)
    private Double engineVolume;
    @PropertyName(CarField.PRODUCTION_YEAR)
    private Integer productionYear;
    @PropertyName(CarField.MILEAGE)
    private List<Mileage> mileage;

    public String getId() {
        return id;
    }

    @PropertyName(CarField.MARK)
    public String getMark() {
        return mark;
    }

    @PropertyName(CarField.MODEL)
    public String getModel() {
        return model;
    }

    @PropertyName(CarField.GEAR)
    public String getGear() {
        return gear;
    }

    @PropertyName(CarField.BODY)
    public String getBody() {
        return body;
    }

    @PropertyName(CarField.ENGINE_VOLUME)
    public Double getEngineVolume() {
        return engineVolume;
    }

    @PropertyName(CarField.PRODUCTION_YEAR)
    public Integer getProductionYear() {
        return productionYear;
    }

    @PropertyName(CarField.MILEAGE)
    public List<Mileage> getMileage() {
        return mileage;
    }

    public void addMileage(Mileage mileage) {
        this.mileage.add(mileage);
    }

    public Car() {
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
    public Object clone() {
        return new Car(id, mark, model, gear, body, engineVolume, productionYear, mileage);
    }

    public static final class CarField {
        public final static String MARK = "mark";
        public final static String MODEL = "model";
        public final static String GEAR = "gear";
        public final static String BODY = "body";
        public final static String ENGINE_VOLUME = "engine";
        public final static String PRODUCTION_YEAR = "production_year";
        public final static String MILEAGE = "mileage";

        private CarField() {
        }
    }
}

