package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.inventory.model.item.Item;

/**
 * Provides a handle to a item card in the item list panel.
 */
public class ItemCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String IMAGE_FIELD_ID = "#image";
    private static final String NAME_FIELD_ID = "#name";
    private static final String QUANTITY_FIELD_ID = "#quantity";
    private static final String SKU_FIELD_ID = "#sku";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label imageLabel;
    private final Label quantityLabel;
    private final Label skuLabel;
    private final List<Label> tagLabels;

    public ItemCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        imageLabel = getChildNode(IMAGE_FIELD_ID);
        quantityLabel = getChildNode(QUANTITY_FIELD_ID);
        skuLabel = getChildNode(SKU_FIELD_ID);

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

    public String getImage() {
        return imageLabel.getText();
    }

    public String getQuantity() {
        return quantityLabel.getText();
    }

    public String getSku() {
        return skuLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code item}.
     */
    public boolean equals(Item item) {
        return getName().equals(item.getName().fullName)
                && getImage().equals(item.getImage().value)
                && getQuantity().equals(item.getQuantity().value)
                && getSku().equals(item.getSku().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(item.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
