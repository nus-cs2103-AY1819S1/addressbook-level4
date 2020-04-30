package guitests.guihandles.menu;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.restaurant.model.menu.Item;

/**
 * Provides a handle to an item card in the item list panel.
 */
public class ItemStackPanelHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String PERCENT_FIELD_ID = "#percent";
    private static final String RECIPE_FIELD_ID = "#recipe";
    private static final String REQUIRED_INGREDIENTS_FIELD_ID = "#requiredIngredients";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label nameLabel;
    private final Label priceLabel;
    private final Label percentLabel;
    private final Label recipeLabel;
    private final Label requiredIngredientsLabel;
    private final List<Label> tagLabels;

    public ItemStackPanelHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);
        percentLabel = getChildNode(PERCENT_FIELD_ID);
        recipeLabel = getChildNode(RECIPE_FIELD_ID);
        requiredIngredientsLabel = getChildNode(REQUIRED_INGREDIENTS_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getPercent() {
        return percentLabel.getText();
    }

    public String getRecipe() {
        return recipeLabel.getText();
    }

    public String getRequiredIngredients() {
        return requiredIngredientsLabel.getText();
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
     * Returns true if this handle contains {@code item}.
     */
    public boolean equals(Item item) {
        return getName().equals(item.getName().toString())
                //&& getPrice().equals(item.getPrice().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(item.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
