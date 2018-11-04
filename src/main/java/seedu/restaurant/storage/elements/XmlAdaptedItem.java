package seedu.restaurant.storage.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.model.menu.Recipe;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.storage.XmlAdaptedTag;

//@@author yican95
/**
 * JAXB-friendly version of the Item.
 */
public class XmlAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";
    public static final String REQUIRED_INGREDIENT = "requiredIngredients";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String originalPrice;
    @XmlElement(required = true)
    private String percent;
    @XmlElement(required = true)
    private String recipe;
    @XmlElement(required = true)
    private Map<String, String> requiredIngredients = new HashMap<>();

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedItem(String name, String price, String percent, String recipe, List<XmlAdaptedTag> tagged,
            Map<String, String> requiredIngredients) {
        this.name = name;
        this.price = price;
        this.originalPrice = price;
        this.percent = percent;
        this.recipe = recipe;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (requiredIngredients != null) {
            this.requiredIngredients = new HashMap<>(requiredIngredients);
        }
    }

    /**
     * Converts a given Item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedItem
     */
    public XmlAdaptedItem(Item source) {
        name = source.getName().toString();
        price = source.getPrice().toString();
        originalPrice = String.format("%.2f", source.getPrice().getOriginalValue());
        percent = String.format("%.0f", source.getPercent());
        recipe = source.getRecipe().toString();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        for (Map.Entry<IngredientName, Integer> entry : source.getRequiredIngredients().entrySet()) {
            String ingredientName = entry.getKey().toString();
            String num = entry.getValue().toString();
            requiredIngredients.put(ingredientName, num);
        }
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> itemTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            itemTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        if (originalPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(originalPrice)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        if (percent == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPercent(percent)) {
            throw new IllegalValueException(Price.MESSAGE_PERCENT_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price, originalPrice, percent);

        if (recipe == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Recipe.class.getSimpleName()));
        }
        final Recipe modelRecipe = new Recipe(recipe);

        if (requiredIngredients == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, REQUIRED_INGREDIENT));
        }
        Map<IngredientName, Integer> modelRequiredIngredients = new HashMap<>();
        for (Map.Entry<String, String> entry : requiredIngredients.entrySet()) {
            if (!IngredientName.isValidName(entry.getKey())) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, REQUIRED_INGREDIENT));
            }
            IngredientName ingredientName = new IngredientName(entry.getKey());
            Integer num = Integer.parseInt(entry.getValue());
            if (num <= 0) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, REQUIRED_INGREDIENT));
            }
            modelRequiredIngredients.put(ingredientName, num);
        }

        final Set<Tag> modelTags = new HashSet<>(itemTags);
        return new Item(modelName, modelPrice, modelRecipe, modelTags, modelRequiredIngredients);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }

        XmlAdaptedItem otherItem = (XmlAdaptedItem) other;
        return Objects.equals(name, otherItem.name)
                && Objects.equals(price, otherItem.price)
                && Objects.equals(originalPrice, otherItem.originalPrice)
                && tagged.equals(otherItem.tagged);
    }
}
