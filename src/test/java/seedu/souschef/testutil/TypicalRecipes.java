package seedu.souschef.testutil;

import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_HR;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_MIN;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_1;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_5;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_AMERICA;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_BEE;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_SPICY;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_STAPLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.souschef.model.AppContent;
import seedu.souschef.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe APPLE = new RecipeBuilder().withName("Apple Pie")
            .withCooktime("23M")
            .withDifficulty("2")
            .withInstruction("Pre-heat oven.")
            .withTags("dessert").build();
    public static final Recipe BANDITO = new RecipeBuilder().withName("Bandito Pockett")
            .withCooktime("23M")
            .withDifficulty("5")
            .withInstruction("Mix all ingredients")
            .withTags("mexican", "wrap").build();
    public static final Recipe CHINESE = new RecipeBuilder().withName("Chinese Fried Noodles").withCooktime("23M")
            .withInstruction("Pre-heat oven.").withDifficulty("5").build();
    public static final Recipe DANISH = new RecipeBuilder().withName("Danish Blueberry Tart").withCooktime("23M")
            .withInstruction("Pre-heat oven.").withDifficulty("5").withTags("Danish").build();
    public static final Recipe EGG = new RecipeBuilder().withName("Egg Roll").withCooktime("23M")
            .withInstruction("Pre-heat oven.").withDifficulty("5").build();
    public static final Recipe FRENCH = new RecipeBuilder().withName("French Onion Soup").withCooktime("23M")
            .withInstruction("Pre-heat oven.").withDifficulty("5").build();
    public static final Recipe GREEN = new RecipeBuilder().withName("Green Curry").withCooktime("23M")
            .withInstruction("Pre-heat oven.").withDifficulty("5").build();

    // Manually added
    public static final Recipe HOON = new RecipeBuilder().withName("Hoon Meier").withCooktime("23M")
            .withDifficulty("5").build();
    public static final Recipe IDA = new RecipeBuilder().withName("Ida Mueller").withCooktime("23M")
            .withDifficulty("5").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe AMERICA = new RecipeBuilder().withName(VALID_NAME_AMERICA)
            .withDifficulty(VALID_DIFFICULTY_5).withCooktime(VALID_COOKTIME_MIN).withTags(VALID_TAG_SPICY).build();
    public static final Recipe BEE = new RecipeBuilder().withName(VALID_NAME_BEE).withDifficulty(VALID_DIFFICULTY_1)
            .withCooktime(VALID_COOKTIME_HR).withTags(VALID_TAG_STAPLE, VALID_TAG_SPICY)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns an {@code AppContent} with all the typical recipes.
     */
    public static AppContent getTypicalAddressBook() {
        AppContent ab = new AppContent();
        for (Recipe recipe : getTypicalRecipes()) {
            ab.getRecipes().add(recipe);
        }
        return ab;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(APPLE, BANDITO, CHINESE, DANISH, EGG, FRENCH, GREEN));
    }
}
