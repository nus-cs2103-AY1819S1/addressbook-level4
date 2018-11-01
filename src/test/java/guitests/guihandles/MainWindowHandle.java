package guitests.guihandles;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;
    private StackPane browserPlaceholder;
    private LoginHandle loginHandle;

    public MainWindowHandle(Stage stage) {
        super(stage);

        attemptLogIn();

        refreshAllQueries();
    }

    /**
     * Refreshes all set items (browserPlaceholder, personListPanel etc.) by querying the DOM again.
     * This should be called if the objects was removed and then recreated, i.e. if a logout then login even was
     * performed.
     */
    public void refreshAllQueries() {
        browserPlaceholder = getChildNode("#browserPlaceholder");
        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }

    public StackPane getBrowserPlaceholder() {
        return browserPlaceholder;
    }

    /**
     * Attempts to get the login handle in the UI.
     * Call only when the login window is showing (i.e. user is logged out)
     * @return the login handle
     */
    public LoginHandle getLoginHandle() {
        loginHandle = new LoginHandle(getChildNode(LoginHandle.GRIDPANE_ID));
        return loginHandle;
    }
}
