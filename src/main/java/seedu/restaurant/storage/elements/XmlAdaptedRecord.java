package seedu.restaurant.storage.elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * JAXB-friendly version of the SalesRecord.
 */
public class XmlAdaptedRecord {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";
    public static final String MESSAGE_QUANTITY_USED_CONSTRAINTS = "Quantity used should be a positive integer!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String itemName;
    @XmlElement(required = true)
    private String quantitySold;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private Map<String, String> ingredientsUsed;

    /**
     * Constructs an XmlAdaptedRecord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecord() {}

    /**
     * Constructs an {@code XmlAdaptedRecord} with the given record details.
     */
    public XmlAdaptedRecord(String date, String itemName, String quantitySold, String price) {
        this.date = date;
        this.itemName = itemName;
        this.quantitySold = quantitySold;
        this.price = price;
        this.ingredientsUsed = new HashMap<>();
    }

    /**
     * Converts a given SalesRecord into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecord
     */
    public XmlAdaptedRecord(SalesRecord source) {
        date = source.getDate().toString();
        itemName = source.getName().toString();
        quantitySold = String.valueOf(source.getQuantitySold().toString());
        price = String.valueOf(source.getPrice().toString());
        ingredientsUsed = new HashMap<>();

        for (Map.Entry<IngredientName, Integer> entry : source.getIngredientsUsed().entrySet()) {
            IngredientName ingredient = entry.getKey();
            Integer quantityUsed = entry.getValue();
            ingredientsUsed.put(ingredient.toString(), quantityUsed.toString());
        }
    }

    /**
     * Converts this jaxb-friendly adapted record object into the model's SalesRecord object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record
     */
    public SalesRecord toModelType() throws IllegalValueException {
        final Date modelDate = dateToModelType();
        final ItemName modelName = nameToModelType();
        final QuantitySold modelQuantitySold = quantitySoldToModelType();
        final Price modelPrice = priceToModelType();
        final Map<IngredientName, Integer> modelIngredientUsed = ingredientUsedToModelType();
        return new SalesRecord(modelDate, modelName, modelQuantitySold, modelPrice)
                .setIngredientsUsed(modelIngredientUsed);
    }

    /**
     * Converts this date string into the model's Date object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the date
     */
    private Date dateToModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(date);
    }

    /**
     * Converts this itemName string into the model's ItemName object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the itemName
     */
    private ItemName nameToModelType() throws IllegalValueException {
        if (itemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidName(itemName)) {
            throw new IllegalValueException(ItemName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new ItemName(itemName);
    }

    /**
     * Converts this quantitySold string into the model's QuantitySold object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the quantitySold
     */
    private QuantitySold quantitySoldToModelType() throws IllegalValueException {
        if (quantitySold == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuantitySold.class.getSimpleName()));
        }
        if (!QuantitySold.isValidQuantity(quantitySold)) {
            throw new IllegalValueException(QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        return new QuantitySold(quantitySold);
    }

    /**
     * Converts this price string into the model's Price object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the price
     */
    private Price priceToModelType() throws IllegalValueException {
        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new Price(price);
    }

    /**
     * Converts this Xml {@code ingredientsUsed} into the model's ingredientUsed.
     *
     * @throws IllegalValueException if there were any data constraints violated in the Ingredient
     */
    private Map<IngredientName, Integer> ingredientUsedToModelType() throws IllegalValueException {
        Map<IngredientName, Integer> modelIngredientUsed = new HashMap<>();

        for (Map.Entry<String, String> entry : ingredientsUsed.entrySet()) {
            String ingredientName = entry.getKey();
            String quantityUsed = entry.getValue();

            validateName(ingredientName);
            IngredientName modelName = new IngredientName(ingredientName);
            validateQuantityUsed(quantityUsed);
            Integer modelQuantity = Integer.parseInt(quantityUsed);

            modelIngredientUsed.put(modelName, modelQuantity);
        }
        return modelIngredientUsed;
    }

    /**
     * Checks if ingredient name is valid.
     *
     * @throws IllegalValueException if {@code ingredientName} is null or invalid
     */
    private void validateName(String ingredientName) throws IllegalValueException {
        if (ingredientName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientName.class.getSimpleName()));
        }
        if (!IngredientName.isValidName(ingredientName)) {
            throw new IllegalValueException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }
    }

    /**
     * Checks if quantity used is valid.
     *
     * @throws IllegalValueException if {@code quantityUsed} is null or invalid
     */
    private void validateQuantityUsed(String quantityUsed) throws IllegalValueException {
        if (quantityUsed == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Integer.class.getSimpleName()));
        }
        try {
            int quantity = Integer.parseInt(quantityUsed);
            if (quantity <= 0) {
                throw new IllegalValueException(MESSAGE_QUANTITY_USED_CONSTRAINTS);
            }
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_QUANTITY_USED_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecord)) {
            return false;
        }

        XmlAdaptedRecord otherRecord = (XmlAdaptedRecord) other;
        return Objects.equals(date, otherRecord.date)
                && Objects.equals(itemName, otherRecord.itemName)
                && Objects.equals(quantitySold, otherRecord.quantitySold)
                && Objects.equals(price, otherRecord.price)
                && Objects.equals(ingredientsUsed, otherRecord.ingredientsUsed);
    }
}
