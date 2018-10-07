package seedu.souschef.model;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;

/**
 * Represents the in-memory model of the application content data.
 */
public class ModelSetCoordinator implements ModelSet {
    public static final int RECIPE_INDEX = 0;
    private static final int NUM_UNIQUE_LIST = 1;
    private static final Logger logger = LogsCenter.getLogger(ModelSetCoordinator.class);

    private Model[] models;

    private VersionedAppContent versionedAppContent;

    /**
     * Initializes all ModelManagers with the given appContent and userPrefs.
     */
    public ModelSetCoordinator(ReadOnlyAppContent appContent, UserPrefs userPrefs) {
        requireAllNonNull(appContent, userPrefs);
        logger.fine("Initializing with application content: " + appContent + " and user prefs " + userPrefs);
        versionedAppContent = new VersionedAppContent(appContent);
        modelsInit();
    }

    public ModelSetCoordinator() {
        this(new AppContent(), new UserPrefs());
        modelsInit();
    }

    public Model[] getModels() {
        return models;
    }

    /**
     * Initializes each ModelManager with the given appContent and return all ModelManagers as a array.
     */
    private void modelsInit() {
        models = new Model[NUM_UNIQUE_LIST];

        ModelManager recipeModelManager = new ModelManager(versionedAppContent, versionedAppContent.getRecipes());
        models[RECIPE_INDEX] = recipeModelManager;
    }
}
