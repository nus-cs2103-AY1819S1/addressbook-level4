package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * The occasionWindow that allows a user to interact with an occasion
 * within this addressbook.
 */
public class OccasionWindow extends MainWindow {
    private static final String FXML = "OccasionWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private OccasionBrowserPanel occasionBrowserPanel;
    private OccasionListPanel occasionListPanel;
    private Config config;
    private UserPrefs prefs;

    public OccasionWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
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
        ObservableList<Person> firstPersonList = logic.getFilteredOccasionList()
                                                    .get(0).getAttendanceList().asUnmodifiableObservableList();
        occasionBrowserPanel = new OccasionBrowserPanel(firstPersonList);
        getBrowserPlaceholder().getChildren().add(occasionBrowserPanel.getRoot());

        occasionListPanel = new OccasionListPanel(logic.getFilteredOccasionList());
        getOccasionListPanelPlaceholder().getChildren().add(occasionListPanel.getRoot());

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

    public OccasionListPanel getPersonListPanel() {
        return occasionListPanel;
    }

    void releaseResources() {

    }
}
