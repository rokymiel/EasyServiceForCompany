package eservice.ui.presentation;

import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.Registration;
import eservice.business.services.NotificationsListener;
import eservice.business.services.RegistrationsService;
import eservice.business.services.StatusService;
import eservice.business.services.UpdatableRegistration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;


public class MainController {

    String pattern = "dd.MM.yyyy HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    @FXML
    TableView<UpdatableRegistration> newRegistrationsTable;
    @FXML
    TableColumn<UpdatableRegistration, Registration> newRegistrationsColumn;
    @FXML
    TableView<UpdatableRegistration> allRegistrationsTable;
    @FXML
    TableColumn<UpdatableRegistration, Registration> dateColumn;
    @FXML
    TableColumn<UpdatableRegistration, Registration> statusColumn;
    @FXML
    TableColumn<UpdatableRegistration, Registration> carColumn;
    @FXML
    TableColumn<UpdatableRegistration, Registration> typeOfWorksColumn;
    @FXML
    TableColumn<UpdatableRegistration, Registration> timeOfWorksColumn;
    @FXML
    TableColumn<UpdatableRegistration, Registration> clientNameColumn;
    @FXML
    TableColumn<UpdatableRegistration, Registration> phoneColumn;

    ObservableList<UpdatableRegistration> registrations = FXCollections.observableArrayList();
    ObservableList<UpdatableRegistration> newRegistrations = FXCollections.observableArrayList();

    RegistrationsService registrationsService;
    final StatusService statusService = new StatusService();
    private final Object locker = new Object();
    @FXML
    void initialize() {
        System.out.println("AAAA");

        registrationsService = new RegistrationsService(new NotificationsListener<>() {
            @Override
            public void add(String item) {
                System.out.println("AddListItem");
                registrations.add(new UpdatableRegistration(item, new NotificationsListener<>() {
                    @Override
                    public void add(Object sender, Registration item) {
                        UpdatableRegistration up = (UpdatableRegistration) sender;
                        synchronized (locker) {
                            if (Objects.equals(up.getRegistration().getStatus(), "new")) {
                                newRegistrations.add(up);
                                System.out.println("add from add");
                                System.out.println(newRegistrations.size());
                                System.out.println(up.getRegistration());
                                up.getValue().addListener((observableValue, registration, newRegistration) -> {
                                    synchronized (locker) {
                                        System.out.println("List");
                                        if (!registration.getStatus().equals("new") && newRegistration.getStatus().equals("new")) {
                                            newRegistrations.add(up);
                                            System.out.println("add");

                                        } else if (registration.getStatus().equals("new") && !newRegistration.getStatus().equals("new")) {
                                            newRegistrations.remove(up);
                                            System.out.println("remove");

                                        }
                                    }
                                });
                            }
                        }
                    }
                }));

            }
        });

        newRegistrationsColumn.setCellValueFactory(cellData -> cellData.getValue().getValue());

        newRegistrationsTable.setItems(newRegistrations);

        newRegistrationsColumn.setCellFactory(col -> {
            TableCell<UpdatableRegistration, Registration> tableCell = new TableCell<>() {
                @Override
                protected void updateItem(Registration item, boolean empty) {
                    super.updateItem(item, empty);
                    System.out.println("Cellllllll");
                    System.out.println(item);
                    System.out.println(empty);
                    System.out.println(newRegistrations.size());
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        this.setGraphic(createDriverGraphic(item));
                    }
                }
            };
            tableCell.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!tableCell.isEmpty())) {
                    UpdatableRegistration rowData = tableCell.getTableRow().getItem();
                    registrationTapped(rowData);
                }
            });
            return tableCell;
        });
        newRegistrationsColumn.setComparator(Comparator.comparing(Registration::getDateOfCreation).reversed());

        allRegistrationsTable.setRowFactory(tv -> {
            TableRow<UpdatableRegistration> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    UpdatableRegistration rowData = row.getItem();
                    registrationTapped(rowData);
                }
            });
            return row;
        });

        setColumn(dateColumn, x -> simpleDateFormat.format(x.getDateOfRegistration().toDate()));
        setColumn(statusColumn, x -> statusService.getCurrent(x.getStatus()));
        setColumn(carColumn, x -> {
            if (x.getClient() != null) {
                Car car = x.getClient().getCar(x.getCarId());
                if (car != null) {
                    return car.getCarName();
                }
            }
            return "";
        });
        setColumn(typeOfWorksColumn, Registration::getTypeOfWorks);
        setColumn(timeOfWorksColumn, x -> x.getTimeOfWorks() != null ? simpleDateFormat.format(x.getTimeOfWorks().toDate()) : "");
        setColumn(clientNameColumn, x -> x.getClient() != null ? x.getClient().getFullName() : "");
        setColumn(phoneColumn, x -> x.getClient() != null ? x.getClient().getPhoneNumber() : "");
        allRegistrationsTable.setItems(registrations);
    }

    private void setColumn(TableColumn<UpdatableRegistration, Registration> column, Function<Registration, String> prop) {
        column.setCellValueFactory(cellData -> cellData.getValue().getValue());
        setCellFactory(column, prop);
    }

    private void setCellFactory(TableColumn<UpdatableRegistration, Registration> column, Function<Registration, String> prop) {
        column.setCellFactory(col -> {
            TableCell<UpdatableRegistration, Registration> cell = new TableCell<>();
            cell.itemProperty().addListener((observableValue, o, newValue) -> {
                if (newValue != null) {
                    Node graphic = new Label(prop.apply(newValue));
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(graphic));
                }
            });
            return cell;
        });
    }

    private Node createDriverGraphic(Registration registration) {

        GridPane gridpane = new GridPane();

        gridpane.setPadding(new Insets(5, 5, 5, 5));
        gridpane.add(new Label("Машина"), 0, 0);   // столбец=1 строка=0
        gridpane.add(new Label("Тип работ"), 0, 1);    // столбец=2 строка=0
        gridpane.add(new Label("Дата"), 0, 2);   // столбец=1 строка=0
        gridpane.setHgap(10);
        Client client = registration.getClient();
        if (client != null) {
            Car car = client.getCar(registration.getCarId());
            if (car != null) {
                gridpane.add(new Label(car.getCarName()), 1, 0);
            }
            gridpane.add(new Label(registration.getTypeOfWorks()), 1, 1);    // столбец=2 строка=0
            gridpane.add(new Label(simpleDateFormat.format(registration.getDateOfRegistration().toDate())), 1, 2);   // столбец=1 строка=0
        }


        return gridpane;
    }

    private void registrationTapped(UpdatableRegistration registration) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegistrationPage.fxml"));

            root = loader.load();

            RegistrationController controller = loader.<RegistrationController>getController();
            controller.set(registration, registrationsService);
            Stage stage = new Stage();
            stage.setTitle("Запись в автосервис");
            stage.setScene(new Scene(root, 800, 800));
            stage.show();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}