package seedu.souschef.storage.recipe;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.storage.ingredient.XmlAdaptedIngredientPortion;

/**
 * JAXB-friendly adapted version of the Instruction.
 */
public class XmlAdaptedInstruction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Instruction's %s field is missing!";

    @XmlElement(required = true)
    private String instruction;
    @XmlElement
    private String cooktime;
    @XmlElement
    private Set<XmlAdaptedIngredientPortion> ingredients = new HashSet<>();

    /**
     * Constructs an XmlAdaptedInstruction.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedInstruction() {}

    /**
     * Constructs a {@code XmlAdaptedInstruction} with the given {@code instruction}.
     */
    public XmlAdaptedInstruction(String instruction, String cooktime, Set<XmlAdaptedIngredientPortion> ingredients) {
        this.instruction = instruction;
        this.cooktime = cooktime;
        if (Duration.parse(cooktime).isZero()) {
            this.cooktime = null;
        }
        if (ingredients != null) {
            this.ingredients = new HashSet<>(ingredients);
        }
    }

    /**
     * Converts a given Instruction into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedInstruction(Instruction source) {
        instruction = source.value;
        cooktime = source.cookTime.toString();
        if (source.cookTime.isZero()) {
            cooktime = null;
        }
        ingredients = source.ingredients.stream().map(XmlAdaptedIngredientPortion::new)
                .collect(Collectors.toSet());
    }

    /**
     * Converts this jaxb-friendly adapted instruction object into the recipeModel's Instruction object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe
     */
    public Instruction toModelType() throws IllegalValueException {
        final Set<IngredientPortion> ingredientPortions = new HashSet<>();
        for (XmlAdaptedIngredientPortion ingredientPortion : ingredients) {
            ingredientPortions.add(ingredientPortion.toModelType());
        }

        if (instruction == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Instruction.class.getSimpleName()));
        }
        if (!Instruction.isValidInstruction(instruction)) {
            throw new IllegalValueException(Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);
        }

        if (cooktime != null && !CookTime.isValidCookTime(cooktime)) {
            throw new IllegalValueException(CookTime.MESSAGE_COOKTIME_CONSTRAINTS);
        }
        CookTime ct = new CookTime(cooktime);

        if (ct == null) {
            return new Instruction(instruction, ingredientPortions);
        } else {
            return new Instruction(instruction, ct, ingredientPortions);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedInstruction)) {
            return false;
        }

        XmlAdaptedInstruction otherInstruction = (XmlAdaptedInstruction) other;
        return Objects.equals(instruction, otherInstruction.instruction)
                && Objects.equals(cooktime, otherInstruction.cooktime)
                && ingredients.equals(otherInstruction.ingredients);
    }
}
