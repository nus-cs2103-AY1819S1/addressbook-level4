package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.tag.Tag;

/**
 * Provides a handle to a group card in the group list panel.
 */
public class GroupCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String GROUP_FIELD_ID = "#group";

    private final Label idLabel;
    private final Label groupLabel;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        groupLabel = getChildNode(GROUP_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getGroup() {
        return groupLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code group}.
     */
    public boolean equals(Tag group) {
        return getGroup().equals(group.tagName);
    }
}
