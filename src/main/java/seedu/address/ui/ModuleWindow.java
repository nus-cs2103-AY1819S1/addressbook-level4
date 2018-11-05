package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * The moduleWindow that allows the user to interact with modules
 * within this addressbook.
 */
public class ModuleWindow extends MainWindow {

    private static final String FXML = "ModuleWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ModuleBrowserPanel browserPanel;
    private ModuleListPanel moduleListPanel;
    private Config config;
    private UserPrefs prefs;

    public ModuleWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
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
        ObservableList<Person> firstModuleAttendaceList = logic.getFilteredModuleList()
                                                    .get(0).getStudents().asUnmodifiableObservableList();
        browserPanel = new ModuleBrowserPanel(firstModuleAttendaceList);
        getBrowserPlaceholder().getChildren().add(browserPanel.getRoot());

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        getModuleListPanelPlaceholder().getChildren().add(moduleListPanel.getRoot());

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

    public ModuleListPanel getModuleListPanel() {
        return moduleListPanel;
    }

    void releaseResources() {}

}
