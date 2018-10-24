package seedu.souschef.storage.ingredient;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.ingredient.IngredientAmount;
import seedu.souschef.model.ingredient.IngredientName;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.ingredient.IngredientServingUnit;


/**
 * class for xml context to model
 * xml health plan
 */
public class XmlAdaptedIngredientPortion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient portion's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String amount;
    @XmlElement(required = true)
    private String unit;

    //base constructor
    public XmlAdaptedIngredientPortion(){}

    public XmlAdaptedIngredientPortion(String name, String amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public XmlAdaptedIngredientPortion(IngredientPortion source) {
        name = source.getName().toString();
        amount = source.getAmount().toString();
        unit = source.getUnit().toString();
    }

    /**
     *
     * Method to model
     * to model
     */
    public IngredientPortion toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        final IngredientName modelName = new IngredientName(name);

        if (!modelName.isValid()) {
            throw new IllegalValueException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "amount"));
        }

        final IngredientAmount modelAmount;
        try {
            modelAmount = new IngredientAmount(amount);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(IngredientAmount.MESSAGE_AMOUNT_CONSTRAINTS);
        }

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "unit"));
        }

        final IngredientServingUnit modelUnit = new IngredientServingUnit(unit);

        if (!modelUnit.isValid()) {
            throw new IllegalValueException(IngredientServingUnit.MESSAGE_UNIT_CONSTRAINTS);
        }

        //TODO: Search and link to existing ingredient
        return new IngredientPortion(modelName, modelAmount, modelUnit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedIngredientPortion)) {
            return false;
        }

        XmlAdaptedIngredientPortion otherPlan = (XmlAdaptedIngredientPortion) other;
        return Objects.equals(name, otherPlan.name)
                && Objects.equals(amount, otherPlan.amount)
                && Objects.equals(unit, otherPlan.unit);
    }
}
