package seedu.inventory.model.item;

import java.util.function.Predicate;

/**
 * Tests that a {@code Item}'s {@code Name} matches any of the keywords given.
 */
public class SkuMatchPredicate implements Predicate<Item> {
    private final String sku;

    public SkuMatchPredicate(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean test(Item item) {
        return item.getSku().toString().equalsIgnoreCase(sku);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SkuMatchPredicate // instanceof handles nulls
                && sku.equals(((SkuMatchPredicate) other).sku)); // state check
    }

}
