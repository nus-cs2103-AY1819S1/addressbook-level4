package seedu.address.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

//@@author chivent

/**
 * An UI component that displays information of a selected image.
 */
public class FilmReelCard extends UiPart<Region> {

    private static final String FXML = "FilmCard.fxml";

    /**
     * An image panel for showing the previews
     */
    private final ObjectProperty<Image> image = new SimpleObjectProperty<Image>(
            new Image("https://via.placeholder.com/500x500"));

    @FXML
    private ImageView imagePreview;
    @FXML
    private Label name;
    @FXML
    private Label pathName;

    public FilmReelCard(Path path, int displayedIndex) throws FileNotFoundException {
        super(FXML);
        FileInputStream fis = new FileInputStream(path.toString());
        imagePreview.imageProperty().bind(image);
        image.setValue(new Image(fis));
        name.setText(displayedIndex + "");
        pathName.setText(path.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilmReelCard)) {
            return false;
        }

        // state check
        FilmReelCard card = (FilmReelCard) other;
        return name.getText().equals(card.name.getText())
                && pathName.getText().equals(card.pathName.getText());
    }
}

