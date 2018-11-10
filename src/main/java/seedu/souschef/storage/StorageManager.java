package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.util.SampleDataUtil;
import seedu.souschef.storage.healthplan.XmlHealthPlanStorage;
import seedu.souschef.storage.ingredient.XmlIngredientStorage;
import seedu.souschef.storage.mealplanner.XmlMealPlanStorage;
import seedu.souschef.storage.recipe.XmlRecipeStorage;

/**
 * Manages storage of AppContent data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FeatureStorage featureStorage;
    private UserPrefsStorage userPrefsStorage;
    private Map<Context, FeatureStorage> listOfFeatureStorage;
    private AppContent appContent;

    public StorageManager(UserPrefsStorage userPrefsStorage, UserPrefs userPrefs, AppContent appContent) {
        super();
        this.appContent = appContent;
        this.userPrefsStorage = userPrefsStorage;
        this.listOfFeatureStorage = new HashMap<>();

        FeatureStorage recipeStorage = new XmlRecipeStorage(userPrefs.getRecipeFilePath());
        FeatureStorage ingredientStorage = new XmlIngredientStorage(userPrefs.getIngredientFilePath());
        FeatureStorage healthPlanStorage = new XmlHealthPlanStorage(userPrefs.getHealthplanPath());
        FeatureStorage mealPlanStorage = new XmlMealPlanStorage(userPrefs.getMealPlanPath());

        listOfFeatureStorage.put(Context.RECIPE, recipeStorage);
        listOfFeatureStorage.put(Context.INGREDIENT, ingredientStorage);
        listOfFeatureStorage.put(Context.HEALTH_PLAN, healthPlanStorage);
        listOfFeatureStorage.put(Context.MEAL_PLAN, mealPlanStorage);
        this.featureStorage = recipeStorage;
    }

    public StorageManager(FeatureStorage featureStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.featureStorage = featureStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.listOfFeatureStorage = new HashMap<>();
    }

    public StorageManager () {
        this.listOfFeatureStorage = new HashMap<>();
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AppContent methods ==============================

    @Override
    public Path getFeatureFilePath() {
        return featureStorage.getFeatureFilePath();
    }

    @Override
    public Map<Context, FeatureStorage> getListOfFeatureStorage() {
        return listOfFeatureStorage;
    }

    public void setMainFeatureStorage(FeatureStorage featureStorage) {
        this.featureStorage = featureStorage;
    }

    /**
     * this function is for reading specific features only
     */
    @Override
    public Optional<ReadOnlyAppContent> readFeature() throws DataConversionException, IOException {
        return readFeature(featureStorage.getFeatureFilePath());
    }

    @Override
    public Optional<ReadOnlyAppContent> readFeature(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return featureStorage.readFeature(filePath);
    }

    /**
     * Read data for a specific feature if available in its default file path.
     * If not, retrieve sample data from the supplier.
     * @param context Specify the feature to be read.
     * @param sampleSupplier Supply sample data as backup.
     */
    private void readFeature(Context context, Supplier<ReadOnlyAppContent> sampleSupplier) throws
            DataConversionException, IOException {
        if (listOfFeatureStorage.containsKey(context)) {
            this.featureStorage = listOfFeatureStorage.get(context);
            this.appContent.includeData(readFeature(this.featureStorage.getFeatureFilePath())
                    .orElseGet(sampleSupplier));
        }
    }

    @Override
    public Optional<ReadOnlyAppContent> readAll() throws DataConversionException, IOException {
        readFeature(Context.RECIPE, SampleDataUtil::getSampleRecipes);
        readFeature(Context.INGREDIENT, SampleDataUtil::getSampleIngredients);
        readFeature(Context.HEALTH_PLAN, SampleDataUtil::getSampleHealthPlans);
        readFeature(Context.MEAL_PLAN, SampleDataUtil::getSampleDays);
        featureStorage = listOfFeatureStorage.get(Context.RECIPE);

        return Optional.of(this.appContent);
    }

    @Override
    public void saveFeature(ReadOnlyAppContent appContent) throws IOException {
        saveFeature(appContent, this.featureStorage.getFeatureFilePath());
    }

    @Override
    public void saveFeature(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        if (this.featureStorage instanceof XmlRecipeStorage) {
            XmlRecipeStorage temp = new XmlRecipeStorage(filePath);
            temp.saveFeature(appContent, filePath);
        } else if (this.featureStorage instanceof XmlIngredientStorage) {
            XmlIngredientStorage temp = new XmlIngredientStorage(filePath);
            temp.saveFeature(appContent, filePath);
        } else if (this.featureStorage instanceof XmlHealthPlanStorage) {
            XmlHealthPlanStorage temp = new XmlHealthPlanStorage(filePath);
            temp.saveFeature(appContent, filePath);
        } else if (this.featureStorage instanceof XmlMealPlanStorage) {
            XmlMealPlanStorage temp = new XmlMealPlanStorage(filePath);
            temp.saveFeature(appContent, filePath);
        }
    }

    @Override
    @Subscribe
    public void handleAppContentChangedEvent(AppContentChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveFeature(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


}
