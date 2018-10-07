package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Provides a handle to a calendar event card in the calendar event list panel.
 */
public class CalendarEventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String LOCATION_FIELD_ID = "#venue";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label locationLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    public CalendarEventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        locationLabel = getChildNode(LOCATION_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

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

    public String getAddress() {
        return locationLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code calendarevent}.
     */
    public boolean equals(CalendarEvent calendarEvent) {
        return getName().equals(calendarEvent.getName().fullName)
            && getAddress().equals(calendarEvent.getLocation().value)
            && getPhone().equals(calendarEvent.getPhone().value)
            && getEmail().equals(calendarEvent.getEmail().value)
            && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(calendarEvent.getTags().stream()
            .map(tag -> tag.tagName)
            .collect(Collectors.toList())));
    }
}
