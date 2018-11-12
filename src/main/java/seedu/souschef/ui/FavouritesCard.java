package seedu.souschef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import seedu.souschef.model.favourite.Favourites;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class FavouritesCard extends GenericCard<Favourites> {

    private static final String FXML = "FavouritesCard.fxml";

    public final Favourites favourite;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label duration;
    @FXML
    private Label difficulty;
    @FXML
    private FlowPane tags;

    public FavouritesCard(Favourites favourite, int displayedIndex) {
        super(FXML);
        this.favourite = favourite;
        id.setText(displayedIndex + ". ");
        name.setText(favourite.getName().fullName);
        duration.setText("Duration: " + favourite.getCookTime().toString());
        difficulty.setText("Difficulty: " + favourite.getDifficulty().toString());
        favourite.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }

        // state check
        FavouritesCard card = (FavouritesCard) other;
        return id.getText().equals(card.id.getText())
                && favourite.equals(card.favourite);
    }
}
