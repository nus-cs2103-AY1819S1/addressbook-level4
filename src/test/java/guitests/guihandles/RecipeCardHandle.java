package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.souschef.model.recipe.Recipe;

/**
 * Provides a handle to a recipe card in the recipe list panel.
 */
public class RecipeCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DIFFICULTY_FIELD_ID = "#difficulty";
    private static final String COOKTIME_FIELD_ID = "#duration";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label difficultyLabel;
    private final Label cooktimeLabel;
    private final List<Label> tagLabels;

    public RecipeCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        difficultyLabel = getChildNode(DIFFICULTY_FIELD_ID);
        cooktimeLabel = getChildNode(COOKTIME_FIELD_ID);

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

    public String getDifficulty() {
        return difficultyLabel.getText();
    }

    public String getCooktime() {
        return cooktimeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code recipe}.
     */
    public boolean equals(Recipe recipe) {
        return getName().equals(recipe.getName().fullName)
                && getDifficulty().equals(recipe.getDifficulty().toString())
                && getCooktime().equals(recipe.getCookTime().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(recipe.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
