package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.cca.Cca;

//@@author ericyjw

/**
 * Provides a handle to a cca card in the cca list panel.
 */
public class CcaCardHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#ccaName";

    private final Label nameLabel;

    public CcaCardHandle(Node cardNode) {
        super(cardNode);
        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code Cca}.
     */
    public boolean equals(Cca cca) {
        return getName().equals(cca.getCcaName());
    }

}
