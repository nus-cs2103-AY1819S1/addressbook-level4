package seedu.souschef.model;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;

/**
 * Represents the in-memory recipeModel of the application content data.
 */
public class ModelSetCoordinator implements ModelSet {
    private static final Logger logger = LogsCenter.getLogger(ModelSetCoordinator.class);

    private final Model<Recipe> recipeModel;
    private final Model<Tag> tagModel;

    private final VersionedAppContent versionedAppContent;

    /**
     * Initializes all ModelManagers with the given appContent and userPrefs.
     */
    public ModelSetCoordinator(ReadOnlyAppContent appContent, UserPrefs userPrefs) {
        requireAllNonNull(appContent, userPrefs);
        logger.fine("Initializing with application content: " + appContent + " and user prefs " + userPrefs);
        versionedAppContent = new VersionedAppContent(appContent);

        recipeModel = new ModelManager<>(versionedAppContent, versionedAppContent.getRecipes());
        tagModel = new ModelManager<>(versionedAppContent, versionedAppContent.getTags());
        // More to be added
    }

    public ModelSetCoordinator() {
        this(new AppContent(), new UserPrefs());
    }

    public ReadOnlyAppContent getAppContent() {
        return versionedAppContent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelSetCoordinator)) {
            return false;
        }

        // state check
        ModelSetCoordinator other = (ModelSetCoordinator) obj;
        return versionedAppContent.equals(other.versionedAppContent)
                && recipeModel.equals(other.recipeModel);
    }

    public Model<Recipe> getRecipeModel() {
        return recipeModel;
    }

    public Model<Tag> getTagModel() {
        return tagModel;
    }
    // More to be added
}
