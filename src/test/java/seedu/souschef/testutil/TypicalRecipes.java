package seedu.souschef.testutil;

import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_AMY;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.souschef.model.AppContent;
import seedu.souschef.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe ALICE = new RecipeBuilder().withName("Alice Pauline")
            .withCooktime("PT23M")
            .withDifficulty("5")
            .withTags("friends").build();
    public static final Recipe BENSON = new RecipeBuilder().withName("Benson Meier")
            .withCooktime("PT23M")
            .withDifficulty("5")
            .withInstruction("Mix all ingredients")
            .withTags("owesMoney", "friends").build();
    public static final Recipe CARL = new RecipeBuilder().withName("Carl Kurz").withCooktime("PT23M")
            .withDifficulty("5").build();
    public static final Recipe DANIEL = new RecipeBuilder().withName("Daniel Meier").withCooktime("PT23M")
            .withDifficulty("5").withTags("friends").build();
    public static final Recipe ELLE = new RecipeBuilder().withName("Elle Meyer").withCooktime("PT23M")
            .withDifficulty("5").build();
    public static final Recipe FIONA = new RecipeBuilder().withName("Fiona Kunz").withCooktime("PT23M")
            .withDifficulty("5").build();
    public static final Recipe GEORGE = new RecipeBuilder().withName("George Best").withCooktime("PT23M")
            .withDifficulty("5").build();

    // Manually added
    public static final Recipe HOON = new RecipeBuilder().withName("Hoon Meier").withCooktime("PT23M")
            .withDifficulty("5").build();
    public static final Recipe IDA = new RecipeBuilder().withName("Ida Mueller").withCooktime("PT23M")
            .withDifficulty("5").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe AMY = new RecipeBuilder().withName(VALID_NAME_AMY).withDifficulty(VALID_DIFFICULTY_AMY)
            .withCooktime(VALID_COOKTIME_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Recipe BOB = new RecipeBuilder().withName(VALID_NAME_BOB).withDifficulty(VALID_DIFFICULTY_BOB)
            .withCooktime(VALID_COOKTIME_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
