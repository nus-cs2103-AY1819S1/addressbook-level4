package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import jdk.javadoc.doclet.Doclet;
import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.util.SampleDataUtil;

/**
 * Manages storage of AppContent data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FeatureStorage featureStorage;
    private UserPrefsStorage userPrefsStorage;
    private ArrayList <FeatureStorage> listOfFeatureStorage;
    private AppContent appContent;

    public StorageManager(UserPrefsStorage userPrefsStorage, AppContent appContent){
        super();
        this.appContent = appContent;
        this.userPrefsStorage = userPrefsStorage;
        this.listOfFeatureStorage = new ArrayList<>();
    }


    public StorageManager(FeatureStorage featureStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.featureStorage = featureStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.appContent = featureStorage.getAppContent();
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

    @Override
    public AppContent getAppContent() {
        return this.getAppContent();
    }

    public void setMainFeatureStorage(FeatureStorage featureStorage){
        this.featureStorage=featureStorage;
    }

    /**
     * this function is for reading specific features only
     */
    @Override
    public Optional<ReadOnlyAppContent> readFeature() throws DataConversionException, IOException {
        return readFeature(featureStorage.getFeatureFilePath());
    }


    @Override
    public Optional<ReadOnlyAppContent> readAll() throws DataConversionException, IOException{
        ArrayList<FeatureStorage> temp = this.listOfFeatureStorage;
        AppContent readOnlyAppContent = this.appContent;
        for(FeatureStorage f: temp){
            //to implement changes for specific feature storage types.
            readOnlyAppContent.includeData(readFeature(f.getFeatureFilePath())
                    .orElseGet(SampleDataUtil::getSampleAddressBook));
        }
        return Optional.of(readOnlyAppContent);

    }



    @Override
    public Optional<ReadOnlyAppContent> readFeature(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to readAppContent data from file: " + filePath);
        return featureStorage.readFeature(filePath);
    }


        @Override
    public void saveFeature(ReadOnlyAppContent appContent) throws IOException {
            saveFeature(appContent, featureStorage.getFeatureFilePath());
    }

    @Override
    public void saveFeature(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        featureStorage.saveFeature(appContent, filePath);
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
