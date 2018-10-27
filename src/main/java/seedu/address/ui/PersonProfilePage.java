package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A UI component that displays full information of a {@code Person}.
 */
public class PersonProfilePage extends UiPart<Region> {
    private static final String DEFAULT_IMAGE_URL = "blank_profile";
    private static final String EMPTY_VALUE = "";
    private static final String FXML = "PersonProfilePage.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);

    @FXML
    private HBox cardPane;
    @FXML
    private Text name;
    @FXML
    private Text nric;
    @FXML
    private Text phone;
    @FXML
    private Text address;
    @FXML
    private Text email;
    @FXML
    private Text availability;
    @FXML
    private Text uniqueField;
    @FXML
    private FlowPane tags;
    @FXML
    private BufferedImage profileImageUrl;
    @FXML
    private Image profileImage;
    @FXML
    private ImageView profileImageDisplay;

    public PersonProfilePage() {
        super(FXML);
        showDefaultDisplayPanel();
        registerAsAnEventHandler(this);
    }

    public void showDefaultDisplayPanel() {

    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        Person selectedPerson = event.getNewSelection();
        name.setText(selectedPerson.getName().fullName);
        nric.setText(selectedPerson.getNric().code);
        phone.setText(selectedPerson.getPhone().value);
        address.setText(selectedPerson.getAddress().value);
        email.setText(selectedPerson.getEmail().value);
        if (selectedPerson instanceof Doctor) {
            Doctor doctor = (Doctor) selectedPerson;
            uniqueField.setText(doctor.getMedicalDepartment().deptName);
            availability.setVisible(true);
            availability.setText(doctor.currentAvailStatus());
            if (doctor.currentAvailStatus().equals(doctor.IS_AVAILABLE)) {
                availability.setStyle("-fx-background-color: #33ff77");
            } else {
                availability.setStyle("-fx-background-color: #ff4d4d");
            }
        } else {
            assert selectedPerson instanceof Patient;
            uniqueField.setText(EMPTY_VALUE);
            availability.setVisible(false);
        }

        tags.getChildren().clear();
        selectedPerson.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        setProfileImage(selectedPerson.getNric().code);
    }

    private void setProfileImage(String imageCode) {
        String imagePath = "/images/" + imageCode + ".png";
        try {
            profileImageUrl = ImageIO.read(PersonProfilePage.class.getResource(imagePath));
        } catch (IOException | IllegalArgumentException e) {
            logger.info("INVALID PROFILE IMAGE PATH: " + e.getLocalizedMessage());
            setProfileImage(DEFAULT_IMAGE_URL);
        }

        profileImage = SwingFXUtils.toFXImage(profileImageUrl, null);
        profileImageDisplay.setImage(profileImage);
        profileImageDisplay.setPreserveRatio(false);
        profileImageDisplay.setFitWidth(200);
        profileImageDisplay.setFitHeight(200);
    }
}
