package eservice.ui.presentation;

import com.google.cloud.Timestamp;
import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.Registration;
import eservice.business.services.RegistrationsService;
import eservice.business.services.StatusService;
import eservice.business.services.UpdatableRegistration;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class RegistrationController implements ChangeListener<Registration> {
    private UpdatableRegistration updatableRegistration;
    private RegistrationsService registrationsService;

    @FXML
    private ComboBox<String> typeOfWorksBox;
    @FXML
    private TextField costField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> hourBox;
    @FXML
    private ComboBox<String> minutesBox;
    @FXML
    private TextField statusField;
    @FXML
    private Button nextButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button backButton;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> endHourBox;
    @FXML
    private ComboBox<String> endMinutesBox;

    @FXML
    private TextArea notesArea;

    @FXML
    private TextField surnameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField patronymicField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;

    @FXML
    private TextField markField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField gearField;
    @FXML
    private TextField bodyField;
    @FXML
    private TextField engineVolumeField;


    @FXML
    private Button cancelChangesButton;
    @FXML
    private Button saveChangesButton;

    private boolean init = true;
    private final StatusService statusService = new StatusService();

    public void set(UpdatableRegistration updatableRegistration, RegistrationsService registrationsService) {
        this.updatableRegistration = updatableRegistration;
        this.registrationsService = registrationsService;
        updatableRegistration.getValue().addListener(this);
        setListeners();
        setFields(updatableRegistration.getRegistration());
        System.out.println("SAST");
        init = false;

    }

    @FXML
    void initialize() {
        System.out.println("init");
        ObservableList<String> range = getClockTime(8, 22);
        ObservableList<String> range2 = getClockTime(0, 59);

        hourBox.setItems(range);
        minutesBox.setItems(range2);

        ObservableList<String> rangeEnd = getClockTime(8, 22);
        ObservableList<String> range2End = getClockTime(0, 59);

        endHourBox.setItems(rangeEnd);
        endMinutesBox.setItems(range2End);


    }

    @Override
    public void changed(ObservableValue<? extends Registration> observableValue, Registration registration, Registration newValue) {
        System.out.println("LIIIIIIIISTEEEEENNNNNN");
        Platform.runLater(() -> {
            if (newValue != null) {
                setFields(newValue);
            }
        });
    }

    private void setListeners() {
        datePicker.valueProperty().addListener(this::registrationDateChanged);
        hourBox.valueProperty().addListener(this::registrationHourChanged);
        minutesBox.valueProperty().addListener(this::registrationMinuteChanged);
        endDatePicker.valueProperty().addListener(this::endRegistrationDateChanged);
        endHourBox.valueProperty().addListener(this::endRegistrationHourChanged);
        endMinutesBox.valueProperty().addListener(this::endRegistrationMinuteChanged);
        typeOfWorksBox.valueProperty().addListener(this::typeOfWorksBoxChanged);
        costField.textProperty().addListener(this::costChanged);
    }

    private ZonedDateTime getZonedDateTime(Timestamp timestamp) {
        return timestamp.toDate().toInstant().atZone(ZoneId.systemDefault());
    }

    private void setEditableFields(Registration registration) {
        costField.setText(registration.getCost() == null ? "" : String.valueOf(registration.getCost()));
        datePicker.setValue(getZonedDateTime(registration.getDateOfRegistration()).toLocalDate());
        int hour = getZonedDateTime(registration.getDateOfRegistration()).toLocalTime().getHour();
        int minute = getZonedDateTime(registration.getDateOfRegistration()).toLocalTime().getMinute();


        hourBox.getSelectionModel().select(hour - 8);
        minutesBox.getSelectionModel().select(minute);

        if (registration.getTimeOfWorks() != null) {
            endDatePicker.setValue(getZonedDateTime(registration.getTimeOfWorks()).toLocalDate());
            endHourBox.getSelectionModel().select(getZonedDateTime(registration.getTimeOfWorks()).toLocalTime().getHour() - 8);
            endMinutesBox.getSelectionModel().select(getZonedDateTime(registration.getTimeOfWorks()).toLocalTime().getMinute());
        } else if (!init) {
            endDatePicker.setValue(null);
        }
    }

    private void setFields(Registration registration) {
        setStatus(registration.getStatus());
        setEditableFields(registration);
        notesArea.setText(registration.getNotes());
        Client client = registration.getClient();
        if (client != null) {
            surnameField.setText(client.getSurname());
            nameField.setText(client.getName());
            patronymicField.setText(client.getPatronymic());
            phoneField.setText(client.getPhoneNumber());
            emailField.setText(client.getEmail());

            Car car = client.getCar(registration.getCarId());
            if (car != null) {
                markField.setText(car.getMark());
                modelField.setText(car.getModel());
                yearField.setText(String.valueOf(car.getProductionYear()));
                gearField.setText(car.getGear());
                bodyField.setText(car.getBody());
                engineVolumeField.setText(String.valueOf(car.getEngineVolume()));
            }

        }
    }

    private void registrationDateChanged(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
        System.out.println("AA");
        if (newDate == null) {
            datePicker.setValue(oldDate);
            return;
        }
        if (endDatePicker.getValue() != null && endDatePicker.getValue().compareTo(newDate) < 0) {
            endDatePicker.setValue(null);
        }
        endDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                System.out.println("AAAaaA");
                System.out.println(newDate);
                setDisable(empty || date.compareTo(newDate) < 0);
            }
        });
        checkChanges();
    }

    private String status;

    private void setStatus(String status) {
        this.status = status;
        nextButton.setVisible(false);
        cancelButton.setVisible(false);
        backButton.setVisible(false);
        statusField.setText(statusService.getCurrent(status));

        String next = statusService.getNext(status);
        String cancellation = statusService.getCancellation(status);
        String back = statusService.getPrevious(status);

        if (next != null) {
            nextButton.setVisible(true);
            nextButton.setText(next);
        }
        if (cancellation != null) {
            cancelButton.setVisible(true);
            cancelButton.setText(cancellation);
        }
        if (back != null) {
            backButton.setVisible(true);
            backButton.setText(back);
        }

    }

    @FXML
    public void onNextClicked() {
        updatableRegistration.setStatus(statusService.getNextStatus(status));
        updatableRegistration.update();
    }

    @FXML
    public void onPreviousClicked() {
        updatableRegistration.setStatus(statusService.getPreviousStatus(status));
        updatableRegistration.update();

    }

    @FXML
    public void onCancelClicked() {
        updatableRegistration.setStatus(statusService.getCancellationStatus(status));
        updatableRegistration.update();
    }

    private void endRegistrationDateChanged(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
        if (newDate == null) {
            if (updatableRegistration.getRegistration().getTimeOfWorks() == null) {
                endHourBox.getSelectionModel().clearSelection();
                endMinutesBox.getSelectionModel().clearSelection();
                checkChanges();
            } else {
                datePicker.setValue(oldDate);
            }
            return;
        }
        if (endHourBox.getSelectionModel().getSelectedIndex() < 0 || endMinutesBox.getSelectionModel().getSelectedIndex() < 0) {
            endHourBox.getSelectionModel().select(hourBox.getSelectionModel().getSelectedIndex());
            endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
            checkChanges();
            return;
        }
        if (Objects.equals(newDate, datePicker.getValue())) {
            if (endHourBox.getSelectionModel().getSelectedIndex() < hourBox.getSelectionModel().getSelectedIndex()) {
                endHourBox.getSelectionModel().select(hourBox.getSelectionModel().getSelectedIndex());
            }
            if (endHourBox.getSelectionModel().getSelectedIndex() == hourBox.getSelectionModel().getSelectedIndex() &&
                    endMinutesBox.getSelectionModel().getSelectedIndex() < minutesBox.getSelectionModel().getSelectedIndex()) {
                endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
            }
        }
        checkChanges();
    }

    private void registrationHourChanged(ObservableValue<? extends String> observableValue, String oldHour, String newHour) {
        System.out.println("LOX2");
        if (endHourBox.getSelectionModel().getSelectedIndex() >= 0 && endMinutesBox.getSelectionModel().getSelectedIndex() >= 0) {
            if (Objects.equals(datePicker.getValue(), endDatePicker.getValue()) &&
                    endHourBox.getSelectionModel().getSelectedIndex() + 8 <= Integer.parseInt(newHour)) {
                endHourBox.getSelectionModel().select(newHour);
                if (endMinutesBox.getSelectionModel().getSelectedIndex() < minutesBox.getSelectionModel().getSelectedIndex()) {
                    endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
                }
            }

        }
        checkChanges();
    }

    private void registrationMinuteChanged(ObservableValue<? extends String> observableValue, String oldMinute, String newMinute) {
        System.out.println("LOX");
        if (endHourBox.getSelectionModel().getSelectedIndex() >= 0 && endMinutesBox.getSelectionModel().getSelectedIndex() >= 0) {
            if (Objects.equals(datePicker.getValue(), endDatePicker.getValue()) &&
                    endHourBox.getSelectionModel().getSelectedIndex() <= hourBox.getSelectionModel().getSelectedIndex() &&
                    endMinutesBox.getSelectionModel().getSelectedIndex() <= Integer.parseInt(newMinute)) {
                endMinutesBox.getSelectionModel().select(newMinute);
            }
        }
        checkChanges();
    }

    private void endRegistrationHourChanged(ObservableValue<? extends String> observableValue, String oldHour, String newHour) {

        if (newHour == null) {
            return;
        }
        if (endDatePicker.getValue() == null) {
            System.out.println("HEH");
            Platform.runLater(() -> endHourBox.getSelectionModel().clearSelection());
            return;
        }
        if (Objects.equals(datePicker.getValue(), endDatePicker.getValue())) {
            if (hourBox.getSelectionModel().getSelectedIndex() + 8 > Integer.parseInt(newHour)) {
                endHourBox.getSelectionModel().select(oldHour);
            } else if (hourBox.getSelectionModel().getSelectedIndex() + 8 == Integer.parseInt(newHour) &&
                    endMinutesBox.getSelectionModel().getSelectedIndex() < minutesBox.getSelectionModel().getSelectedIndex()) {
                endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
            }
        }
        checkChanges();
    }

    private void endRegistrationMinuteChanged(ObservableValue<? extends String> observableValue, String oldMinute, String newMinute) {
        if (newMinute == null) {
            return;
        }
        if (endDatePicker.getValue() == null) {
            Platform.runLater(() -> endMinutesBox.getSelectionModel().clearSelection());
            return;
        }
        if (Objects.equals(datePicker.getValue(), endDatePicker.getValue())) {
            if (hourBox.getSelectionModel().getSelectedIndex() == endHourBox.getSelectionModel().getSelectedIndex() &&
                    Integer.parseInt(newMinute) < minutesBox.getSelectionModel().getSelectedIndex()) {
                endMinutesBox.getSelectionModel().select(oldMinute);
            }
        }
        checkChanges();
    }

    private void costChanged(ObservableValue<? extends String> observableValue, String oldCost, String newCost) {
        System.out.println("MMM");
        if (newCost == null || newCost.equals("")) {
            checkChanges();
            return;
        }
        if (newCost.endsWith("d") || newCost.endsWith("D")) {
            costField.setText(oldCost);
        }
        try {
            Double.parseDouble(newCost);
            checkChanges();
        } catch (NumberFormatException ex) {
            costField.setText(oldCost);
        }
    }

    private void checkChanges() {
        if (!init && (typeOfWorksChanged() ||
                costFieldChanged() ||
                dateChanged() ||
                endDateChanged())) {
            editing = true;
            setEditable(true);
            return;
        }
        editing = false;
        setEditable(false);
    }

    private boolean typeOfWorksChanged() {
        return !Objects.equals(updatableRegistration.getRegistration().getTypeOfWorks(), typeOfWorksBox.getSelectionModel().getSelectedItem());
    }

    private boolean costFieldChanged() {
        return !Objects.equals(String.valueOf(updatableRegistration.getRegistration().getCost()), costField.getText());
    }

    private boolean dateChanged() {
        return !Objects.equals(getZonedDateTime(updatableRegistration.getRegistration().getDateOfRegistration()).toLocalDate(), datePicker.getValue()) ||
                !Objects.equals(getZonedDateTime(updatableRegistration.getRegistration().getDateOfRegistration()).toLocalTime().getHour(), hourBox.getSelectionModel().getSelectedIndex() + 8) ||
                !Objects.equals(getZonedDateTime(updatableRegistration.getRegistration().getDateOfRegistration()).toLocalTime().getMinute(), minutesBox.getSelectionModel().getSelectedIndex());
    }

    private boolean endDateChanged() {
        return (endDatePicker.getValue() != null && updatableRegistration.getRegistration().getTimeOfWorks() == null) ||
                (endDatePicker.getValue() != null && updatableRegistration.getRegistration().getTimeOfWorks() != null) && (
                        !Objects.equals(getZonedDateTime(updatableRegistration.getRegistration().getTimeOfWorks()).toLocalDate(), endDatePicker.getValue()) ||
                                !Objects.equals(getZonedDateTime(updatableRegistration.getRegistration().getTimeOfWorks()).toLocalTime().getHour(), endHourBox.getSelectionModel().getSelectedIndex() + 8) ||
                                !Objects.equals(getZonedDateTime(updatableRegistration.getRegistration().getTimeOfWorks()).toLocalTime().getMinute(), endMinutesBox.getSelectionModel().getSelectedIndex()));
    }

    private void typeOfWorksBoxChanged(ObservableValue<? extends String> observableValue, String oldType, String newType) {
        checkChanges();
    }


    private ObservableList<String> getClockTime(int start, int stop) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = start; i <= stop; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }

    @FXML
    private void saveChanges() {
        setEditable(false);
        System.out.println(endDatePicker.getValue());
        if (costFieldChanged()) {
            if (!(costField.getText() == null || costField.getText().equals(""))) {
                updatableRegistration.setCost(Double.parseDouble(costField.getText()));
            } else {
                updatableRegistration.setCost(0d);
            }
        }
        if (typeOfWorksChanged()) {
//            updatableRegistration.setTypeOfWorks(typeOfWorksBox.getSelectionModel().getSelectedItem());
        }
        if (dateChanged()) {
//            Date date = Date.from(datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            LocalDateTime dateTime = datePicker.getValue().atStartOfDay()
                    .withHour(hourBox.getSelectionModel().getSelectedIndex() + 8)
                    .withMinute(minutesBox.getSelectionModel().getSelectedIndex());
            updatableRegistration.setDateOfRegistration(Timestamp.of(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())));
        }
        if (endDateChanged()) {
            LocalDateTime dateTime = endDatePicker.getValue().atStartOfDay()
                    .withHour(endHourBox.getSelectionModel().getSelectedIndex() + 8)
                    .withMinute(endMinutesBox.getSelectionModel().getSelectedIndex());
            updatableRegistration.setTimeOfWorks(Timestamp.of(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())));
        }
        updatableRegistration.update();

    }

    @FXML
    private void cancelEdit() {
        setEditable(false);
        setEditableFields(updatableRegistration.getRegistration());
    }

    private boolean editing = false;

    private void setEditable(boolean b) {
        editing = b;
        saveChangesButton.setVisible(b);
        cancelChangesButton.setVisible(b);
    }

}
