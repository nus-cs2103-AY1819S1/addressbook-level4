package seedu.address.ui;

import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Person;

//@@author snajef
/**
 * The Medication Panel (maybe more in the future?) of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    private static final String FXML = "BrowserPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    // Remember to set the fx:id of the elements in the .fxml file!
    @javafx.fxml.FXML
    private TableView<Prescription> prescriptionTableView;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> drugNameCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> dosageCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> dosageUnitCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> dosesPerDayCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> startDateCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> endDateCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> durationCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> activePrescriptionCol;

    private Person currentSelection;
    private ObservableList<Person> persons;

    /**
     * C'tor for the BrowserPanel.
     * We need the MainWindow to supply us with a view of the list of {@code Person}s so that we can
     * update our pointer whenever a {@code Person} is updated.
     */
    public BrowserPanel(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));
        currentSelection = event.getNewSelection();
        resetTableView(currentSelection);
    }

    /**
     * Current strategy is to refresh the medication panel every time a new result is available.
     * This might potentially burden the app for every new result available.
     * Might want to consider creating a new type of event for submitting medication and subscribing only to that.
     * TODO?
     */
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));

        // If we're not looking at anything, there's no need to update.
        if (currentSelection == null) {
            return;
        }

        // The AddressBook works by re-creating the Person object from scratch, hence
        // our current pointer is outdated, so we have to find our new Person object and update our table to point
        // to his medication list.
        currentSelection = persons.filtered(person -> currentSelection.isSamePerson(person)).get(0);
        resetTableView(currentSelection);
    }

    /** Resets the table view given a {@code Person}. */
    private void resetTableView(Person person) {
        resetTable(person.getPrescriptionList());
    }

    /** Resets the table view given a {@code PrescriptionList}. */
    public void resetTable(PrescriptionList prescriptionList) {
        ObservableList<Prescription> pxs = prescriptionList.getViewOfPrescriptionList();
        prescriptionTableView.setItems(pxs);

        drugNameCol.setCellValueFactory(
            param -> new SimpleStringProperty(param.getValue()
                .getDrugName()
                .toString()));

        dosageCol.setCellValueFactory(
            param -> new SimpleStringProperty(Double.toString(param.getValue()
                .getDose()
                .getDose())));

        dosageUnitCol.setCellValueFactory(
            param -> new SimpleStringProperty(param.getValue()
                .getDose()
                .getDoseUnit()));

        dosesPerDayCol.setCellValueFactory(
            param -> new SimpleStringProperty(Integer.toString(param.getValue()
                .getDose()
                .getDosesPerDay())));

        startDateCol.setCellValueFactory(
            param -> new SimpleStringProperty(param.getValue()
                .getDuration()
                .getStartDateAsString()));

        endDateCol.setCellValueFactory(
            param -> new SimpleStringProperty(param.getValue()
                .getDuration()
                .getEndDateAsString()));

        durationCol.setCellValueFactory(
            param -> new SimpleStringProperty(param.getValue()
                .getDuration()
                .getDurationAsString()));

        activePrescriptionCol.setCellValueFactory(
            param -> {
                Calendar today = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                try {
                    endDate.setTime(Duration.DATE_FORMAT.parse(param.getValue().getDuration().getEndDateAsString()));
                } catch (ParseException e) {
                    logger.log(Level.SEVERE, loggingPrefix + "An exception has occurred: ", e);
                    return new SimpleStringProperty("An error has occurred.");
                }

                return new SimpleStringProperty((isAfter(today, endDate) ? "No" : "Yes"));
            });
    }

    /**
     * Setter method to fix the currently selected person.
     * Should only be used for testing purposes.
     * Will only work if the current selection is null.
     */
    public void setCurrentSelection(Person p) {
        currentSelection = (currentSelection == null ? p : currentSelection);
    }

    /**
     * Helper method to check if a given {@code Calendar} is after another given {@code Calendar}.
     * @return true iff left is strictly after right.
     */
    private boolean isAfter(Calendar left, Calendar right) {
        if (left.get(Calendar.YEAR) < right.get(Calendar.YEAR)) {
            return false;
        } else if (left.get(Calendar.YEAR) > right.get(Calendar.YEAR)) {
            return true;
        }

        if (left.get(Calendar.DAY_OF_YEAR) <= right.get(Calendar.DAY_OF_YEAR)) {
            return false;
        }

        return true;
    }
}
