package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.thanepark.model.ride.Ride;

/**
 * Provides a handle to a ride card in the ride list panel.
 */
public class RideCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String INFO_FIELD_ID = "#rideInfo";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String STATUS_FIELD_ID = "#statusString";

    private final Label idLabel;
    private final Label nameLabel;
    private final List<Label> rideInfoList;
    private final List<Label> tagLabels;
    private final Label statusLabel;

    public RideCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        statusLabel = getChildNode(STATUS_FIELD_ID);
        Region infoContainer = getChildNode(INFO_FIELD_ID);
        rideInfoList = infoContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

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

    public String getStatusString() {
        return statusLabel.getText();
    }

    public List<String> getInfo() {
        return rideInfoList
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code ride}.
     */
    public boolean equals(Ride ride) {
        return getName().equals(ride.getName().fullName)
                && ImmutableMultiset.copyOf(getInfo()).equals(ImmutableMultiset.copyOf(ride.getInformation()))
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(ride.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())))
                && getStatusString().equals(ride.getStatus().name());
    }
}
