package seedu.souschef.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;
import seedu.souschef.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Pandan Chicken";
    public static final String DEFAULT_DIFFICULTY = "5";
    public static final String DEFAULT_COOKTIME = "23M";
    public static final String DEFAULT_INSTRUCTION = "Pre-heat oven.";

    private Name name;
    private Difficulty difficulty;
    private CookTime cookTime;
    private Set<Tag> tags;
    private List<Instruction> instructions;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        cookTime = new CookTime(DEFAULT_COOKTIME);
        tags = new HashSet<>();
        instructions = new ArrayList<>();
        instructions.add(new Instruction(DEFAULT_INSTRUCTION));
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        difficulty = recipeToCopy.getDifficulty();
        cookTime = recipeToCopy.getCookTime();
        tags = new HashSet<>(recipeToCopy.getTags());
        instructions = new ArrayList<>(recipeToCopy.getInstructions());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withCooktime(String cooktime) {
        this.cookTime = new CookTime(cooktime);
        return this;
    }

    /**
     * Parses the {@code instructions} into a {@code List<Instruction>} and set it to the {@code Recipe}
     * that we are building.
     */
    public RecipeBuilder withInstruction(String ... instructions) {
        this.instructions = SampleDataUtil.getInstructionList(instructions);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, difficulty, cookTime, instructions, tags);
    }
}
