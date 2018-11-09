package seedu.restaurant.testutil.menu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.model.menu.Recipe;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.model.util.SampleDataUtil;

//@@author yican95
/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Cheese Fries";
    public static final String DEFAULT_PRICE = "2";
    public static final String DEFAULT_RECIPE = "";

    private Name name;
    private Price price;
    private Recipe recipe;
    private Set<Tag> tags;
    private Map<IngredientName, Integer> requiredIngredients;

    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
        recipe = new Recipe(DEFAULT_RECIPE);
        tags = new HashSet<>();
        requiredIngredients = new HashMap<>();
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        price = itemToCopy.getPrice();
        recipe = itemToCopy.getRecipe();
        tags = new HashSet<>(itemToCopy.getTags());
        requiredIngredients = new HashMap<>(itemToCopy.getRequiredIngredients());
    }

    /**
     * Sets the {@code Name} of the {@code Item} that we are building.
     */
    public ItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public ItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Item} that we are building.
     */
    public ItemBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Recipe} of the {@code Item} that we are building.
     */
    public ItemBuilder withRecipe(String recipe) {
        this.recipe = new Recipe(recipe);
        return this;
    }

    /**
     * Sets the requiredIngredients of the {@code Item} that we are building..
     */
    public ItemBuilder withRequiredIngredients(Map<String, String> requiredIngredients) {
        this.requiredIngredients = new HashMap<>();
        for (Map.Entry<String, String> entry : requiredIngredients.entrySet()) {
            this.requiredIngredients.put(new IngredientName(entry.getKey()),
                    Integer.parseInt(entry.getValue()));
        }
        return this;
    }

    public Item build() {
        return new Item(name, price, recipe, tags, requiredIngredients);
    }

}
