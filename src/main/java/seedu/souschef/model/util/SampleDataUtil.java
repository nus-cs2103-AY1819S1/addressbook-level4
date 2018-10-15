package seedu.souschef.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AppContent} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Alex Yeoh"), new Difficulty("1"), new CookTime("PT30M"),
                    getTagSet("friends")),
            new Recipe(new Name("Bernice Yu"), new Difficulty("1"), new CookTime("PT30M"),
                    getTagSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"), new Difficulty("1"), new CookTime("PT30M"),
                    getTagSet("neighbours")),
            new Recipe(new Name("David Li"), new Difficulty("1"), new CookTime("PT30M"),
                    getTagSet("family")),
            new Recipe(new Name("Irfan Ibrahim"), new Difficulty("1"), new CookTime("PT30M"),
                    getTagSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"), new Difficulty("1"), new CookTime("PT30M"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAppContent getSampleAddressBook() {
        AppContent sampleAb = new AppContent();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.getRecipes().add(sampleRecipe);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
