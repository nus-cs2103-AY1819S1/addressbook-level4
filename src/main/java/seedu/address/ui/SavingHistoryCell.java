package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Wish}.
 */
public class SavingHistoryCell extends UiPart<Region> {

    private static final String FXML = "SavingHistoryCell.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on WishBook level 4</a>
     */

    @FXML
    private Label history;

    public SavingHistoryCell(String savingHistory) {
        super(FXML);
        history.setText(savingHistory);
    }
}
