package seedu.souschef.model.ingredient;

import java.util.Objects;

/**
 * class to store definition of ingredient serving unit.
 */
public class IngredientServingUnitDefinition {
    private final String unit;
    private final String commonUnit;
    private final Double conversionValue;

    public IngredientServingUnitDefinition(String unit, String commonUnit, Double conversionValue) {
        this.unit = unit;
        this.commonUnit = commonUnit;
        this.conversionValue = conversionValue;
    }

    public String getUnit() {
        return unit;
    }

    public String getCommonUnit() {
        return commonUnit;
    }

    public Double getConversionValue() {
        return conversionValue;
    }

    /**
     * Basically same with isSame method above.
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientServingUnitDefinition)) {
            return false;
        }

        IngredientServingUnitDefinition otherDefinition = (IngredientServingUnitDefinition) other;

        return otherDefinition != null
                && otherDefinition.getCommonUnit().equals(getCommonUnit())
                && otherDefinition.getConversionValue().equals(getConversionValue())
                && otherDefinition.getUnit().equals(getUnit());

    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, commonUnit, conversionValue);
    }
}
