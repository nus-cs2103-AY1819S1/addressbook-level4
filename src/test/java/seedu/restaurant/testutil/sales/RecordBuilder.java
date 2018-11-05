package seedu.restaurant.testutil.sales;

import java.util.HashMap;
import java.util.Map;

import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * A utility class to help with building SalesRecord objects.
 */
public class RecordBuilder {
    public static final String DEFAULT_DATE = "25-09-2018";
    public static final String DEFAULT_ITEM_NAME = "Fried Rice";
    public static final String DEFAULT_QUANTITY_SOLD = "35";
    public static final String DEFAULT_PRICE = "5.50";
    private Date date;
    private ItemName itemName;
    private QuantitySold quantitySold;
    private Price price;
    private double revenue;
    private Map<IngredientName, Integer> ingredientsUsed;

    public RecordBuilder() {
        date = new Date(DEFAULT_DATE);
        itemName = new ItemName(DEFAULT_ITEM_NAME);
        quantitySold = new QuantitySold(DEFAULT_QUANTITY_SOLD);
        price = new Price(DEFAULT_PRICE);
        revenue = quantitySold.getValue() * price.getValue();
        ingredientsUsed = new HashMap<>();
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(SalesRecord recordToCopy) {
        date = recordToCopy.getDate();
        itemName = recordToCopy.getName();
        quantitySold = recordToCopy.getQuantitySold();
        price = recordToCopy.getPrice();
        revenue = recordToCopy.getRevenue();
        ingredientsUsed = recordToCopy.getIngredientsUsed();
    }

    /**
     * Sets the {@code Date} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code ItemName} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.itemName = new ItemName(name);
        return this;
    }

    /**
     * Sets the {@code QuantitySold} of the {@code Record} that we are building.
     */
    public RecordBuilder withQuantitySold (String quantitySold) {
        this.quantitySold = new QuantitySold(quantitySold);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Record} that we are building.
     */
    public RecordBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code ingredientUsed} of the {@code Record} that we are building.
     */
    public RecordBuilder withIngredientUsed(Map<IngredientName, Integer> ingredientsUsed) {
        this.ingredientsUsed = ingredientsUsed;
        return this;
    }

    public SalesRecord build() {
        return new SalesRecord(date, itemName, quantitySold, price).setIngredientsUsed(ingredientsUsed);
    }
}
