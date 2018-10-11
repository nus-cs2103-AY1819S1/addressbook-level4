package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Food;

/**
 * JAXB-friendly adapted version of the Food.
 */
public class XmlAdaptedFood {

    @XmlValue
    private String foodName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedFood() {
    }

    /**
     * Constructs a {@code XmlAdaptedFood} with the given {@code foodName}.
     */
    public XmlAdaptedFood(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Converts a given Food into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedFood(Food source) {
        foodName = source.foodName;
    }

    /**
     * Converts this jaxb-friendly adapted food object into the model's Food object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Food toModelType() throws IllegalValueException {
        if (!Food.isValidFood(foodName)) {
            throw new IllegalValueException(Food.MESSAGE_FOOD_CONSTRAINTS);
        }
        return new Food(foodName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedFood)) {
            return false;
        }

        return foodName.equals(((XmlAdaptedFood) other).foodName);
    }
}
