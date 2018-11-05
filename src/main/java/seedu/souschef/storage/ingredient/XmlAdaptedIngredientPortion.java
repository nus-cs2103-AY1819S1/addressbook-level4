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
        this.name = name.toLowerCase();
        this.amount = amount;
        this.unit = unit.toLowerCase();
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

        if (!IngredientName.isValid(name)) {
            throw new IllegalValueException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }

        final IngredientName modelName = new IngredientName(name);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "amount"));
        }

        double tempAmount;
        try {
            tempAmount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(IngredientAmount.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        final IngredientAmount modelAmount = new IngredientAmount(tempAmount);

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "unit"));
        }

        if (!IngredientServingUnit.isValid(unit)) {
            throw new IllegalValueException(IngredientServingUnit.MESSAGE_UNIT_CONSTRAINTS);
        }

        final IngredientServingUnit modelUnit = new IngredientServingUnit(unit);

        //TODO: Search and link to existing ingredient
        return new IngredientPortion(modelName, modelUnit, modelAmount);
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
