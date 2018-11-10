package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.project.Assignment;

/**
 * Provides a handle to an assignment card in the assignment list panel.
 */
public class AssignmentCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String PROJECT_NAME_FIELD_ID = "#assignmentName";
    private static final String AUTHOR_FIELD_ID = "#author";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private final Label idLabel;
    private final Label projectNameLabel;
    private final Label authorLabel;
    private final Label descriptionLabel;

    public AssignmentCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        projectNameLabel = getChildNode(PROJECT_NAME_FIELD_ID);
        authorLabel = getChildNode(AUTHOR_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getProjectName() {
        return projectNameLabel.getText();
    }

    public String getAuthor() {
        return authorLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code assignment}.
     */
    public boolean equals(Assignment assignment) {
        return getProjectName().equals(assignment.getProjectName().fullProjectName)
                && getAuthor().equals(assignment.getAuthor().fullName)
                && getDescription().equals(assignment.getDescription().value);
    }
}
