package seedu.address.model.ride;

import java.util.function.Predicate;

/**
 * Tests that an attribute value matches the predicate given
 */
public class AttributePredicate implements Predicate<NumericAttribute> {
    private char operator;
    private NumericAttribute attribute;

    public AttributePredicate(char operator, NumericAttribute attribute) {
        this.operator = operator;
        this.attribute = attribute;
    }

    public NumericAttribute getAttribute() {
        return attribute;
    }

    @Override
    public boolean test(NumericAttribute attribute) {
        switch (operator) {

        case '<':
            return attribute.getValue() < this.attribute.getValue();
        case '>':
            return attribute.getValue() > this.attribute.getValue();
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttributePredicate // instanceof handles nulls
                && this.attribute.equals(((AttributePredicate) other).attribute)
                && this.operator == ((AttributePredicate) other).operator);
    }
}
