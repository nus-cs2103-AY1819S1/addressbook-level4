package guitests.guihandles.reservation;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.restaurant.model.reservation.Reservation;

/**
 * Provides a handle to a reservation card in the reservation list panel.
 */
public class ReservationCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PAX_FIELD_ID = "#pax";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TIME_FIELD_ID = "#time";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label paxLabel;
    private final Label dateLabel;
    private final Label timeLabel;
    private final Label remarkLabel;
    private final List<Label> tagLabels;

    public ReservationCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        paxLabel = getChildNode(PAX_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        timeLabel = getChildNode(TIME_FIELD_ID);
        remarkLabel = getChildNode(REMARK_FIELD_ID);

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

    public String getPax() {
        return paxLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }

    /**
     * Returns true if this handle contains {@code reservation}.
     */
    public boolean equals(Reservation reservation) {
        return getName().equals(reservation.getName().toString())
                && getPax().equals(reservation.getPax().toString())
                && getDate().equals(reservation.getDate().toString())
                && getTime().equals(reservation.getTime().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(reservation.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
