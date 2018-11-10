package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * The Information Panel of the App.
 */
public class InformationPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Patient patient;
    private Doctor doctor;

    @FXML
    private Label allergiesLabel;

    @FXML
    private Label conditionsLabel;

    @FXML
    private TableView<Appointment> upcomingAppointmentTable;

    @FXML
    private TableView<Appointment> pastAppointmentTable;

    @FXML
    private TableColumn<Appointment, ArrayList<Prescription>> prescriptionColumn;

    @FXML
    private TableColumn<Appointment, ArrayList<Prescription>> prescriptionPastColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateTimeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateTimePastColumn;

    public InformationPanel(String fxmlPath) {
        super(fxmlPath);
        this.patient = null;
        registerAsAnEventHandler(this);
    }

    private void setConnections() {
        upcomingAppointmentTable.getItems().clear();
        pastAppointmentTable.getItems().clear();
        allergiesLabel.setText(" ");
        conditionsLabel.setText(" ");
    }

    private void setConnections(ObservableList<Appointment> upcomingAppointmentList) {
        upcomingAppointmentTable.getItems().clear();
        upcomingAppointmentTable.getItems().addAll(upcomingAppointmentList);
        dateTimeColumn.setCellFactory(column -> {
            return new TableCell<Appointment, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        setText(item.format(formatter));
                    }
                }
            };
        });
    }

    private void setConnections(ObservableList<String> allergiesList,
                                ObservableList<String> conditionsList,
                                ObservableList<Appointment> upcomingAppointmentList,
                                ObservableList<Appointment> pastAppointmentList) {

        String parsedAllergiesList = allergiesList.toString().substring(1, allergiesList.toString().length() - 1);
        String parsedConditionsList = conditionsList.toString().substring(1, conditionsList.toString().length() - 1);
        allergiesLabel.setText(parsedAllergiesList);
        conditionsLabel.setText(parsedConditionsList);
        upcomingAppointmentTable.getItems().clear();
        pastAppointmentTable.getItems().clear();
        upcomingAppointmentTable.getItems().addAll(upcomingAppointmentList);
        pastAppointmentTable.getItems().addAll(pastAppointmentList);
        prescriptionColumn.setCellFactory(column -> {
            return new TableCell<Appointment, ArrayList<Prescription>>() {
                @Override
                protected void updateItem(ArrayList<Prescription> item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setGraphic(null);
                        return;
                    } else {
                        final Text text = new Text(item.toString()
                                .substring(1, item.toString().length() - 1)
                                .replaceAll(", ", "\n"));
                        text.setWrappingWidth(500.0);
                        setPrefHeight(text.getLayoutBounds().getHeight());
                        setGraphic(text);
                        text.setFill(Color.WHITE);
                    }
                }
            };
        });
        prescriptionPastColumn.setCellFactory(column -> {
            return new TableCell<Appointment, ArrayList<Prescription>>() {
                @Override
                protected void updateItem(ArrayList<Prescription> item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setGraphic(null);
                        return;
                    } else {
                        final Text text = new Text(item.toString()
                                .substring(1, item.toString().length() - 1)
                                .replaceAll(", ", "\n"));
                        text.setWrappingWidth(500.0);
                        setPrefHeight(text.getLayoutBounds().getHeight());
                        setGraphic(text);
                        text.setFill(Color.WHITE);
                    }
                }
            };
        });
        dateTimeColumn.setCellFactory(column -> {
            return new TableCell<Appointment, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        setText(item.format(formatter));
                        if (item.isBefore(LocalDateTime.now())) {
                            setTextFill(Color.RED);
                        }
                    }
                }
            };
        });
        dateTimePastColumn.setCellFactory(column -> {
            return new TableCell<Appointment, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        setText(item.format(formatter));
                    }
                }
            };
        });
    }

    /**
     * Loads a patient's information on the panel.
     */
    private void loadPatientInformation(Patient patient) {
        Iterator<String> allergiesItr = patient.getMedicalHistory().getAllergies().iterator();
        Iterator<String> conditionsItr = patient.getMedicalHistory().getConditions().iterator();
        Iterator<Appointment> upcomingAppointmentItr = patient.getUpcomingAppointments().iterator();
        Iterator<Appointment> pastAppointmentItr = patient.getPastAppointments().iterator();

        ObservableList<String> allergiesList = FXCollections.observableArrayList();
        ObservableList<String> conditionsList = FXCollections.observableArrayList();
        ObservableList<Appointment> upcomingAppointmentList = FXCollections.observableArrayList();
        ObservableList<Appointment> pastAppointmentList = FXCollections.observableArrayList();

        while (allergiesItr.hasNext()) {
            allergiesList.add(allergiesItr.next());
        }
        while (conditionsItr.hasNext()) {
            conditionsList.add(conditionsItr.next());
        }
        while (upcomingAppointmentItr.hasNext()) {
            upcomingAppointmentList.add(upcomingAppointmentItr.next());
        }
        while (pastAppointmentItr.hasNext()) {
            pastAppointmentList.add(pastAppointmentItr.next());
        }
        setConnections(allergiesList, conditionsList, upcomingAppointmentList, pastAppointmentList);
    }

    /**
     * Loads a doctor's information on the panel.
     */
    private void loadDoctorInformation(Doctor doctor) {
        Iterator<Appointment> upcomingAppointmentItr = doctor.getUpcomingAppointments().iterator();
        ObservableList<Appointment> upcomingAppointmentList = FXCollections.observableArrayList();
        while (upcomingAppointmentItr.hasNext()) {
            upcomingAppointmentList.add(upcomingAppointmentItr.next());
        }
        setConnections(upcomingAppointmentList);
    }

    /**
     * Clear information on the panel.
     */
    private void clearInformation() {
        setConnections();
    }

    /**
     * Changes panel between doctor and patient.
     */
    public void changePanel(Person person) {
        if (person instanceof Patient) {
            loadPatientInformation((Patient) person);
        } else if (person instanceof Doctor) {
            loadDoctorInformation((Doctor) person);
        } else {
            clearInformation();
        }
    }
}
