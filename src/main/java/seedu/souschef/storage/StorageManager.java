package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.util.SampleDataUtil;
import seedu.souschef.storage.healthplan.XmlHealthPlanStorage;
import seedu.souschef.storage.ingredient.XmlIngredientStorage;
import seedu.souschef.storage.recipe.XmlRecipeStorage;

/**
 * Manages storage of AppContent data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FeatureStorage featureStorage;
    private UserPrefsStorage userPrefsStorage;
    private ArrayList<FeatureStorage> listOfFeatureStorage;
    private AppContent appContent;

    public StorageManager(UserPrefsStorage userPrefsStorage, UserPrefs userPrefs, AppContent appContent) {
        super();
        this.appContent = appContent;
        this.userPrefsStorage = userPrefsStorage;
        this.listOfFeatureStorage = new ArrayList<>();

        FeatureStorage recipeStorage = new XmlRecipeStorage(userPrefs.getRecipeFilePath());
        FeatureStorage ingredientStorage = new XmlIngredientStorage(userPrefs.getIngredientFilePath());
        FeatureStorage healthPlanStorage = new XmlHealthPlanStorage(userPrefs.getHealthplanPath());
        listOfFeatureStorage.add(recipeStorage);
        listOfFeatureStorage.add(ingredientStorage);
        listOfFeatureStorage.add(healthPlanStorage);
        this.featureStorage = recipeStorage;
    }

    //TODO: Constructor redundant
    public StorageManager(FeatureStorage featureStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.featureStorage = featureStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.listOfFeatureStorage = new ArrayList<>();
    }

    public StorageManager () {
        this.listOfFeatureStorage = new ArrayList<>();


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
    public Storage include(FeatureStorage feature) {
        this.listOfFeatureStorage.add(feature);
        return this;
    }

    public ArrayList<FeatureStorage> getListOfFeatureStorage() {
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


    @Override
    public Optional<ReadOnlyAppContent> readAll() throws DataConversionException, IOException {
        ArrayList<FeatureStorage> temp = this.listOfFeatureStorage;
        AppContent readOnlyAppContent = this.appContent;

        for (FeatureStorage f: temp) {
            if (f instanceof XmlRecipeStorage) {
                this.featureStorage = f;
                //to implement changes for specific feature storage types.
                readOnlyAppContent.includeData(readFeature(f.getFeatureFilePath())
                        .orElseGet(SampleDataUtil::getSampleAddressBook));
            } else if (f instanceof XmlIngredientStorage) {
                this.featureStorage = f;
                readOnlyAppContent.includeData(readFeature(f.getFeatureFilePath()).get());
            } else if (f instanceof XmlHealthPlanStorage) {
                this.featureStorage = f;
                readOnlyAppContent.includeData(readFeature(f.getFeatureFilePath()).get());
            }
            //reset the first to main
            this.featureStorage = temp.get(0);
        }
        return Optional.of(readOnlyAppContent);
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
