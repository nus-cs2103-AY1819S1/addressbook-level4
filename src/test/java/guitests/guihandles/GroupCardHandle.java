package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.group.Group;

/**
 * Provides a handle to a group card in the group list panel.
 */
public class GroupCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String GROUP_TITLE_FIELD_ID = "#groupTitle";
    private static final String GROUP_DESC_FIELD_ID = "#groupDescription";

    private final Label idLabel;
    private final Label groupLabel;
    private final Label groupDescription;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        groupLabel = getChildNode(GROUP_TITLE_FIELD_ID);
        groupDescription = getChildNode(GROUP_DESC_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getGroupTitle() {
        return groupLabel.getText();
    }

    public String getGroupDescription() {
        return groupDescription.getText();
    }

    /**
     * Returns true if this handle contains {@code group}.
     */
    public boolean equals(Group group) {
        return getGroupTitle().equals(group.getTitle().fullTitle)
            && getGroupDescription().equals(group.getDescription().statement);
    }
}
