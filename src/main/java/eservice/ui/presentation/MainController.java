package eservice.ui.presentation;

import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.Registration;
import eservice.business.services.NotificationsListener;
import eservice.business.services.RegistrationsService;
import eservice.business.services.UpdatableRegistration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MainController {
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

    @FXML
    void initialize() {
        System.out.println("AAAA");

        registrationsService = new RegistrationsService(new NotificationsListener<>() {
            @Override
            public void add(String item) {
                System.out.println("AddListItem");
                registrations.add(new UpdatableRegistration(item, new NotificationsListener<Registration>() {
                    @Override
                    public void add(Object sender, Registration item) {
                        UpdatableRegistration up = (UpdatableRegistration) sender;
                        if (Objects.equals(up.getRegistration().getStatus(), "new")) {
                            newRegistrations.add(up);
                        }
                    }
                }));
            }
        });
        newRegistrationsColumn.setCellValueFactory(cellData -> cellData.getValue().getValue());

        newRegistrationsTable.setItems(newRegistrations);


        newRegistrationsColumn.setCellFactory(col -> {
            TableCell<UpdatableRegistration, Registration> cell = new TableCell<>();

            cell.itemProperty().addListener((observableValue, o, newValue) -> {
                if (newValue != null) {
                    Node graphic = createDriverGraphic(newValue);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(graphic));
                }
            });
            return cell;
        });

        setColumn(dateColumn, x -> String.valueOf(x.getDateOfRegistration()));
        setColumn(statusColumn, Registration::getStatus);
        setColumn(carColumn, x -> x.getClient() != null ? x.getClient().getCar(x.getCarId()).getCarName() : "");
        setColumn(typeOfWorksColumn, Registration::getTypeOfWorks);
        setColumn(timeOfWorksColumn, x -> String.valueOf(x.getTimeOfWorks()));
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
            gridpane.add(new Label(car.getCarName()), 1, 0);   // столбец=1 строка=0
            gridpane.add(new Label(registration.getTypeOfWorks()), 1, 1);    // столбец=2 строка=0
            gridpane.add(new Label(registration.getDateOfRegistration().toString()), 1, 2);   // столбец=1 строка=0
        }


        return gridpane;
    }


}