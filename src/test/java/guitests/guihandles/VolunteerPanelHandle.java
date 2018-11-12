package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.volunteer.Volunteer;

/**
 * Provides a handle to a volunteer card in the volunteer list panel.
 */
public class VolunteerPanelHandle extends NodeHandle<Node> {
    public static final String ID_FIELD_ID = "#id";

    private static final String NAME_FIELD_ID = "#name";
    private static final String GENDER_FIELD_ID = "#gender";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label genderLabel;
    private final Label birthdayLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final List<Label> tagLabels;


    public VolunteerPanelHandle(Node panelNode) {
        super(panelNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        genderLabel = getChildNode(GENDER_FIELD_ID);
        birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

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

    public String getGender() {
        return genderLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code volunteer}.
     */
    public boolean equals(Volunteer volunteer) {
        return getName().equals(volunteer.getName().fullName)
                && getGender().equals(volunteer.getGender().value)
                && getBirthday().equals(volunteer.getBirthday().value)
                && getPhone().equals(volunteer.getPhone().value)
                && getEmail().equals(volunteer.getEmail().value)
                && getAddress().equals(volunteer.getAddress().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(volunteer.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }

}
