package seedu.souschef.testutil;

import seedu.souschef.model.AppContent;
import seedu.souschef.model.recipe.Recipe;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AppContent ab = new AppContentBuilder().withRecipe("John", "Doe").build();}
 */
public class AppContentBuilder {

    private AppContent appContent;

    public AppContentBuilder() {
        appContent = new AppContent();
    }

    public AppContentBuilder(AppContent appContent) {
        this.appContent = appContent;
    }

    /**
     * Adds a new {@code Recipe} to the {@code AppContent} that we are building.
     */
    public AppContentBuilder withRecipe(Recipe recipe) {
        appContent.addRecipe(recipe);
        return this;
    }

    public AppContent build() {
        return appContent;
    }
}
