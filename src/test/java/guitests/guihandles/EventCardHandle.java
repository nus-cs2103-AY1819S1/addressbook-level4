package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.scheduler.model.event.Event;

/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String EVENT_NAME_FIELD_ID = "#eventName";
    private static final String START_DATETIME_FIELD_ID = "#startDateTime";
    private static final String END_DATETIME_FIELD_ID = "#endDateTime";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label eventNameLabel;
    private final Label startDateTimeLabel;
    private final Label endDateTimeLabel;
    private final Label venueLabel;
    private final List<Label> tagLabels;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        eventNameLabel = getChildNode(EVENT_NAME_FIELD_ID);
        startDateTimeLabel = getChildNode(START_DATETIME_FIELD_ID);
        endDateTimeLabel = getChildNode(END_DATETIME_FIELD_ID);
        venueLabel = getChildNode(VENUE_FIELD_ID);

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

    public String getEventName() {
        return eventNameLabel.getText();
    }

    public String getStartDateTime() {
        return startDateTimeLabel.getText();
    }

    public String getEndDateTime() {
        return endDateTimeLabel.getText();
    }

    public String getVenue() {
        return venueLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code event}.
     */
    public boolean equals(Event event) {
        return getEventName().equals(event.getEventName().value)
                && getStartDateTime().equals(event.getStartDateTime().getPrettyString())
                && getEndDateTime().equals(event.getEndDateTime().getPrettyString())
                && getVenue().equals(event.getVenue().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(event.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
