package guitests.guihandles;

import com.google.common.collect.ImmutableMultiset;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.module.Module;

public class ModuleCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String CODE_FIELD_ID = "#code";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label codeLabel;
    private final List<Label> tagLabels;

    public ModuleCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        codeLabel = getChildNode(CODE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getCode() {
        return codeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code module}.
     */
    public boolean equals(Module module) {
        return getName().equals(module.getModuleTitle().toString())
                && getCode().equals(module.getModuleCode().toString() + ":")
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(module.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }

}
