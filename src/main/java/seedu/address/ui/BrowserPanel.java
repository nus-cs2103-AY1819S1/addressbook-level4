package seedu.address.ui;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Patient;
import seedu.address.model.person.medicalrecord.Disease;
import seedu.address.model.person.medicalrecord.DrugAllergy;
import seedu.address.model.person.medicalrecord.Note;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    @FXML
    private TreeView treeView;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(Patient patient) {
        loadPage(SEARCH_PAGE_URL + patient.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        // loadPersonPage(event.getNewSelection());

        System.out.println("clicked: " + event.getNewSelection());


        Patient patientClicked = event.getNewSelection();


        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);

        makeBranch("Name: " + patientClicked.getName().fullName, root);
        makeBranch("IC: " + patientClicked.getIcNumber().value, root);
        makeBranch("Address: " + patientClicked.getAddress().value, root);
        makeBranch("Phone: " + patientClicked.getPhone().value, root);
        makeBranch("Email: " + patientClicked.getEmail().value, root);

        TreeItem<String> medicalRecords;
        medicalRecords = makeBranch("Medical Records", root);
        makeBranch("Blood Type: " + patientClicked.getMedicalRecord().getBloodType().value, medicalRecords);

        TreeItem<String> drugAllergies;
        drugAllergies = makeBranch("Drug Allergies", medicalRecords);
        List<DrugAllergy> drugAllergyList = patientClicked.getMedicalRecord().getDrugAllergies();
        for (DrugAllergy drugAllergy: drugAllergyList) {
            makeBranch(drugAllergy.value, drugAllergies);
        }

        TreeItem<String> diseaseHistory;
        diseaseHistory = makeBranch("Disease History", medicalRecords);
        List<Disease> diseaseList = patientClicked.getMedicalRecord().getDiseaseHistory();
        for (Disease disease: diseaseList) {
            makeBranch(disease.value, diseaseHistory);
        }

        TreeItem<String> notes;
        notes = makeBranch("Notes", medicalRecords);
        List<Note> noteList = patientClicked.getMedicalRecord().getNotes();
        for (Note note: noteList) {
            System.out.println(note);
            makeBranch(note.getMessage().value, notes);
        }

        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }

    /**
     * Creates a branch in the tree item, returning the child item.
     * @param title
     * @param parent
     * @return the child TreeItem after branching out.
     */
    private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> child = new TreeItem<>(title);
        child.setExpanded(true);
        parent.getChildren().add(child);
        return child;
    }
}
