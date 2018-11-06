package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;

//@@author ericyjw
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
