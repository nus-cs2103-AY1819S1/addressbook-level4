package seedu.souschef.storage.recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;

/**
 * JAXB-friendly version of the Recipe.
 */
public class XmlAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String difficulty;
    @XmlElement(required = true)
    private String cooktime;
    @XmlElement(required = true)
    private List<XmlAdaptedInstruction> instructions = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRecipe.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecipe() {}

    /**
     * Constructs an {@code XmlAdaptedRecipe} with the given recipe details.
     */
    public XmlAdaptedRecipe(String name, String difficulty, String cooktime,
                            List<XmlAdaptedInstruction> instructions, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.difficulty = difficulty;
        this.cooktime = cooktime;
        if (instructions != null) {
            this.instructions = new ArrayList<>(instructions);
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Recipe into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecipe
     */
    public XmlAdaptedRecipe(Recipe source) {
        name = source.getName().fullName;
        difficulty = source.getDifficulty().toString();
        cooktime = source.getCookTime().toString();
        instructions = source.getInstructions().stream()
                .map(XmlAdaptedInstruction::new)
                .collect(Collectors.toList());
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted recipe object into the recipeModel's Recipe object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Instruction> modelInstructions = new ArrayList<>();
        for (XmlAdaptedInstruction instruction : instructions) {
            modelInstructions.add(instruction.toModelType());
        }
        // TODO: To implement reject if instruction is empty.
        if (modelInstructions.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Instruction.class.getSimpleName()));
        }

        final List<Tag> recipeTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            recipeTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        if (cooktime == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, CookTime.class.getSimpleName()));
        }
        if (!CookTime.isValidCookTime(cooktime)) {
            throw new IllegalValueException(CookTime.MESSAGE_COOKTIME_CONSTRAINTS);
        }
        final CookTime modelCooktime = new CookTime(cooktime);

        final Set<Tag> modelTags = new HashSet<>(recipeTags);
        return new Recipe(modelName, modelDifficulty, modelCooktime, modelInstructions, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecipe)) {
            return false;
        }

        XmlAdaptedRecipe otherRecipe = (XmlAdaptedRecipe) other;
        return Objects.equals(name, otherRecipe.name)
                && Objects.equals(difficulty, otherRecipe.difficulty)
                && Objects.equals(cooktime, otherRecipe.cooktime)
                && instructions.equals(otherRecipe.instructions)
                && tagged.equals(otherRecipe.tagged);
    }
}
