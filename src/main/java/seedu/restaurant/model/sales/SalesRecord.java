package seedu.restaurant.model.sales;

import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.restaurant.model.ingredient.IngredientName;

//@@author HyperionNKJ
/**
 * Represents a sales record in the sales book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SalesRecord {

    // Identity field
    private final Date date;
    private final ItemName name;

    // Data fields
    private final QuantitySold quantitySold;
    private final Price price;
    private final double revenue;
    private final Map<IngredientName, Integer> ingredientsUsed;

    /**
     * Every field must be present and not null.
     */
    public SalesRecord(Date date, ItemName name, QuantitySold quantitySold, Price price) {
        this(date, name, quantitySold, price, quantitySold.getValue() * price.getValue(), new HashMap<>());
    }

    private SalesRecord(Date date, ItemName name, QuantitySold quantitySold, Price price, double revenue,
            Map<IngredientName, Integer> ingredientsUsed) {
        requireAllNonNull(date, name, quantitySold, price, revenue, ingredientsUsed);
        this.date = date;
        this.name = name;
        this.quantitySold = quantitySold;
        this.price = price;
        this.revenue = revenue;
        this.ingredientsUsed = ingredientsUsed;
    }

    public Date getDate() {
        return date;
    }

    public ItemName getName() {
        return name;
    }

    public QuantitySold getQuantitySold() {
        return quantitySold;
    }

    public Price getPrice() {
        return price;
    }

    public double getRevenue() {
        return revenue;
    }

    public Map<IngredientName, Integer> getIngredientsUsed() {
        return ingredientsUsed;
    }

    /**
     * Returns a new SalesRecord object with specified {@code ingredientsUsed} and all other attributes unchanged.
     * This is to ensure immutability.
     * @param ingredientsUsed The ingredients and their corresponding quantity used.
     */
    public SalesRecord setIngredientsUsed(Map<IngredientName, Integer> ingredientsUsed) {
        return new SalesRecord(this.date, this.name, this.quantitySold, this.price, this.revenue, ingredientsUsed);
    }

    /**
     * Returns true if both records have the same identity field (i.e. same date and name).
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(SalesRecord otherRecord) {
        if (otherRecord == this) {
            return true;
        }
        return otherRecord != null
                && getName().equals(otherRecord.getName())
                && getDate().equals(otherRecord.getDate());
    }

    /**
     * Returns true if both records have the same identity and data fields.
     * This defines a stronger notion of equality between two records.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SalesRecord)) {
            return false;
        }

        SalesRecord otherRecord = (SalesRecord) other;
        return otherRecord.getDate().equals(getDate())
                && otherRecord.getName().equals(getName())
                && otherRecord.getQuantitySold().equals(getQuantitySold())
                && otherRecord.getPrice().equals(getPrice())
                && otherRecord.getRevenue() == getRevenue();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, name, quantitySold, price, revenue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(", Item Name:  ")
                .append(getName())
                .append(", Quantity Sold: ")
                .append(getQuantitySold())
                .append(", Item Price: $")
                .append(getPrice());
        return builder.toString();
    }
}
