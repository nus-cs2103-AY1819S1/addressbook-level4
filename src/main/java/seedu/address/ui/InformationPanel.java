package seedu.address.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

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
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.patient.Patient;

/**
 * The Information Panel of the App.
 */
public class InformationPanel extends UiPart<Region> {
    private static final String FXML = "InformationPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Patient patient;

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

    public InformationPanel() {
        super(FXML);
        this.patient = null;
        registerAsAnEventHandler(this);
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
    }

    /**
     * Loads a patient's information on the panel.
     */
    private void loadPatientInformation(Patient patient) {
        Iterator<String> allergiesItr =  patient.getMedicalHistory().getAllergies().iterator();
        Iterator<String> conditionsItr =  patient.getMedicalHistory().getConditions().iterator();
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

    @Subscribe
    private void handleInformationPanelChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        // TODO - handle doctor case
        if (event.getNewSelection() instanceof Patient) {
            loadPatientInformation((Patient) (event.getNewSelection()));
        }
    }
}
