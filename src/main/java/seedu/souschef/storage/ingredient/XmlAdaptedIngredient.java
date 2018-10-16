package seedu.souschef.storage.ingredient;

import java.text.ParseException;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientAmount;
import seedu.souschef.model.ingredient.IngredientDate;
import seedu.souschef.model.ingredient.IngredientName;
import seedu.souschef.model.ingredient.IngredientServingUnit;


/**
 * class for xml context to model
 * xml health plan
 */
public class XmlAdaptedIngredient {


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String amount;
    @XmlElement(required = true)
    private String unit;
    @XmlElement(required = true)
    private String date;

    //base constructor
    public XmlAdaptedIngredient(){}

    public XmlAdaptedIngredient(String name, String amount, String unit, String date) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.date = date;
    }

    public XmlAdaptedIngredient(Ingredient source) {
        name = source.getName().toString();
        amount = source.getAmount().toString();
        unit = source.getUnit().toString();
        date = source.getDate().toString();
    }

    /**

     *
     * Method to model

     * to model

     */
    public Ingredient toModelType() throws IllegalValueException {

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

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        final IngredientDate modelDate;
        try {
            modelDate = new IngredientDate(date);
        } catch (ParseException e) {
            throw new IllegalValueException(String.format(IngredientDate.MESSAGE_DATE_CONSTRAINTS));
        }

        return new Ingredient(modelName, modelAmount, modelUnit, modelDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedIngredient)) {
            return false;
        }

        XmlAdaptedIngredient otherPlan = (XmlAdaptedIngredient) other;
        return Objects.equals(name, otherPlan.name)
                && Objects.equals(amount, otherPlan.amount)
                && Objects.equals(unit, otherPlan.unit)
                && Objects.equals(date, otherPlan.date);
    }
}
