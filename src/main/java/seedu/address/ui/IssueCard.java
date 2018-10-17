package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.Issue;
import seedu.address.model.issue.Solution;

/**
 * An UI component that displays information of a {@code Issue}.
 */
public class IssueCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be thrown
     * by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/saveit-level4/issues/336">The issue on SaveIt level 4</a>
     */

    public final Issue issue;

    @FXML
    private HBox cardPane;
    @FXML
    private Label statement;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane solutions;
    @FXML
    private FlowPane tags;

    public IssueCard(Issue issue, int displayedIndex) {
        super(FXML);
        this.issue = issue;
        id.setText(displayedIndex + ". ");
        statement.setText(issue.getStatement().issue);
        description.setText(issue.getDescription().value);
        int index = 1;
        List<Solution>solutionList = issue.getSolutions();
        for (Solution solution: solutionList) {
            solutions.getChildren().add(new Label(index + ". " + "[ " + solution.solutionName + ']' + " "));
            index++;
        }
        issue.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IssueCard)) {
            return false;
        }

        // state check
        IssueCard card = (IssueCard) other;
        return id.getText().equals(card.id.getText())
                && issue.equals(card.issue);
    }
}
