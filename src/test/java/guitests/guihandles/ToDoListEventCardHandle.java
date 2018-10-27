package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.address.model.todolist.ToDoListEvent;

/**
 * Provides a handle to a todolist event card in the todolist event list panel.
 */
public class ToDoListEventCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String PRIORITY_FIELD_ID = "#venue";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label priorityLabel;
    private final Label descriptionLabel;

    public ToDoListEventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        titleLabel = getChildNode(TITLE_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        priorityLabel = getChildNode(PRIORITY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code toDoListevent}.
     */
    public boolean equals(ToDoListEvent toDoListEvent) {
        return getTitle().equals(toDoListEvent.getTitle().value)
                && getPriority().equals(toDoListEvent.getPriority().value)
                && getDescription().equals(toDoListEvent.getDescription().value);
    }
}
