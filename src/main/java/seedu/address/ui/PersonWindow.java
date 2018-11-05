package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;

/**
 * The Person Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class PersonWindow extends MainWindow {

    private static final String FXML = "PersonWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonBrowserPanel personBrowserPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    public PersonWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage, config, prefs, logic);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);
    }



    /**
     * Fills up all the placeholders of this window.
     */
    @Override
    void fillInnerParts() {
        ObservableList<Module> moduleList = logic.getFilteredPersonList().get(0)
                                                .getModuleList().asUnmodifiableObservableList();
        ObservableList<Occasion> occasionList = logic.getFilteredPersonList().get(0)
                                                .getOccasionList().asUnmodifiableObservableList();

        personBrowserPanel = new PersonBrowserPanel(moduleList, occasionList);
        getBrowserPlaceholder().getChildren().add(personBrowserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        getPersonListPanelPlaceholder().getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        getResultDisplayPlaceholder().getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        getStatusbarPlaceholder().getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        getCommandBoxPlaceholder().getChildren().add(commandBox.getRoot());
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    void show() {
        primaryStage.show();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    void releaseResources() {}
}
