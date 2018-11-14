package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;

/**
 * Represents a singular ModuleCard within this addressbook.
 */
public class ModuleCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String CODE_FIELD_ID = "#code";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String YEAR_FIELD_ID = "#academicYear";
    private static final String SEMESTER_FIELD_ID = "#semester";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label codeLabel;
    private final Label yearLabel;
    private final Label semesterLabel;
    private final List<Label> tagLabels;

    public ModuleCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        codeLabel = getChildNode(CODE_FIELD_ID);
        yearLabel = getChildNode(YEAR_FIELD_ID);
        semesterLabel = getChildNode(SEMESTER_FIELD_ID);

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

    public String getSemester() {
        return semesterLabel.getText();
    }

    public String getAcademicYear() {
        return yearLabel.getText();
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
                && getSemester().equals("Semester: " + module.getSemester().toString())
                && getAcademicYear().equals("Academic Year: " + module.getAcademicYear().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(module.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
