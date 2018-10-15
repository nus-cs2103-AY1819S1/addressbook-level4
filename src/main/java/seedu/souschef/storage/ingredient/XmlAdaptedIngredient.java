package seedu.souschef.storage.ingredient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.ServingUnit;


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
        name = source.getName();
        amount = Double.toString(source.getAmount());
        unit = source.getUnit().toString();
        date = new SimpleDateFormat("MM-dd-yyyy").format(source.getDate());
    }

    /**

     *
     * Method to model

     * to model

     */
    public Ingredient toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "name"));
        }

        /*if (!name.isValidName(name)) {
            throw new IllegalValueException(name.MESSAGE_NAME_CONSTRAINTS);
        }*/

        final String modelName = new String(name);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "amount"));
        }

        /*if (!TargetWeight.isValidWeight(amount)) {
            throw new IllegalValueException(TargetWeight.MESSAGE_WEIGHT_CONSTRAINTS);
        }*/

        final double modelAmount = Double.parseDouble(amount);

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ServingUnit.class.getSimpleName()));
        }

        /*if (!CurrentWeight.isValidWeight(unit)) {
            throw new IllegalValueException(CurrentWeight.MESSAGE_WEIGHT_CONSTRAINTS);
        }*/

        final ServingUnit modelUnit = ServingUnit.valueOf(unit);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }

        /*if (!CurrentHeight.isValidHeight(date)) {
            throw new IllegalValueException(CurrentHeight.MESSAGE_HEIGHT_CONSTRAINTS);
        }*/

        final Date modelDate;
        try {
            modelDate = (new SimpleDateFormat("MM-dd-yyyy").parse(date));
        } catch (ParseException e) {
            throw new IllegalValueException(String.format("Wrong date format", Date.class.getSimpleName()));

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
