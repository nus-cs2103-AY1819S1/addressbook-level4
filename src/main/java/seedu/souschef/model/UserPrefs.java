package seedu.souschef.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.souschef.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path recipeFilePath = Paths.get("data" , "recipe.xml");
    private Path ingredientPath = Paths.get("data" , "ingredient.xml");
    private Path healthplanPath = Paths.get("data" , "healthplan.xml");
    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getRecipeFilePath() {
        return recipeFilePath;
    }
    public Path getIngredientFilePath() {
        return ingredientPath;
    }
    public Path getHealthplanPath() {
        return healthplanPath;
    }

    public void setRecipeFilePath(Path recipeFilePath) {
        this.recipeFilePath = recipeFilePath;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(recipeFilePath, o.recipeFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, recipeFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + recipeFilePath);
        return sb.toString();
    }

}
