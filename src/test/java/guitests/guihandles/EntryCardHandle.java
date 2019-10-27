package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.entry.ResumeEntry;

/**
 * provides a handle to a entry card in the entry list panel.
 */
public class EntryCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String SUBTITLE_FIELD_ID = "#subtitle";
    private static final String DURATION_FIELD_ID = "#duration";
    private static final String CAT_FIELD_ID = "#categoryTag";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label subtitleLabel;
    private final Label durationLabel;
    private final Label categoryLabel;
    private final List<Label> tagLabels;

    public EntryCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        titleLabel = getChildNode(TITLE_FIELD_ID);
        subtitleLabel = getChildNode(SUBTITLE_FIELD_ID);
        durationLabel = getChildNode(DURATION_FIELD_ID);
        categoryLabel = getChildNode(CAT_FIELD_ID);

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

    public String getCategory() {
        return categoryLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getSubtitle() {
        return subtitleLabel.getText();
    }

    public String getDuration() {
        return durationLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     * @param entry
     */
    public boolean equals(ResumeEntry entry) {
        return getCategory().equals(entry.getCategory().cateName)
                && getTitle().equals(entry.getEntryInfo().getTitle())
                && getSubtitle().equals(entry.getEntryInfo().getSubHeader())
                && getDuration().equals(entry.getEntryInfo().getDuration())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(entry.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
