package seedu.learnvocabulary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.learnvocabulary.model.word.Word;

/**
 * An UI component that displays information of a {@code Word}.
 */
public class WordCard extends UiPart<Region> {

    private static final String FXML = "WordListCard.fxml";
    private static final String[] TAG_COLOR_STYLES =
        {"green",
        };

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/learnVocabulary-level4/issues/336">
     *     The issue on LearnVocabulary level 4</a>
     */

    public final Word word;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public WordCard(Word word, int displayedIndex) {
        super(FXML);
        this.word = word;
        id.setText(displayedIndex + ". ");
        name.setText(word.getName().fullName);
        initTags(word);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code word}.
     */
    private void initTags(Word word) {
        word.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WordCard)) {
            return false;
        }

        // state check
        WordCard card = (WordCard) other;
        return id.getText().equals(card.id.getText())
                && word.equals(card.word);
    }
}
