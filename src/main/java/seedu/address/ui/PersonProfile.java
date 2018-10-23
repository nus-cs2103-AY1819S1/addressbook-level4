package seedu.address.ui;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;

/**
 * A UI component that displays information and profile picture of a {@code Person}.
 */
//@@author javenseow
public class PersonProfile extends UiPart<Region> {

    private static final String FXML = "PersonProfile.fxml";
    public final Person person;

    @FXML
    private HBox profilePane;
    @FXML
    private ImageView profilepicture;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label room;
    @FXML
    private Label school;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonProfile(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        room.setText(person.getRoom().value);
        school.setText(person.getSchool().value);
        email.setText(person.getEmail().value);
        //setProfilePicture(person);
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

   /* private void setProfilePicture(Person person) {
        try {
            BufferedImage bufferedImage;
            bufferedImage = ImageIO.read(person.getProfilePicture().filePath);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            profilepicture = setImage(image);
        } catch (IIOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonProfile)) {
            return false;
        }

        // state check
        PersonProfile profile = (PersonProfile) other;
        return room.getText().equals(profile.room.getText())
                && person.equals(profile.person);
    }
}
