package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final PersonListPanelHandle personListPanel;
    private ModuleListPanelHandle moduleListPanel;
    private OccasionListPanelHandle occasionListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    //private final BrowserPanelHandle browserPanel;
    private final PersonBrowserPanelHandle personBrowserPanel;
    private final OccasionBrowserPanelHandle occasionBrowserPanel;
    private final ModuleBrowserPanelHandle moduleBrowserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        //browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        personBrowserPanel = new PersonBrowserPanelHandle(getChildNode(PersonBrowserPanelHandle.BROWSER_ID));
        occasionBrowserPanel = new OccasionBrowserPanelHandle(getChildNode(OccasionBrowserPanelHandle.BROWSER_ID));
        moduleBrowserPanel = new ModuleBrowserPanelHandle(getChildNode(ModuleBrowserPanelHandle.BROWSER_ID));
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ModuleListPanelHandle getModuleListPanel() {
        moduleListPanel = new ModuleListPanelHandle(getChildNode(ModuleListPanelHandle.MODULE_LIST_VIEW_ID));
        return moduleListPanel;
    }

    public OccasionListPanelHandle getOccasionListPanel() {
        occasionListPanel = new OccasionListPanelHandle(getChildNode(OccasionListPanelHandle.OCCASION_LIST_VIEW_ID));
        return occasionListPanel;
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

//    public BrowserPanelHandle getBrowserPanel() {
//        return browserPanel;
//    }

    public PersonBrowserPanelHandle getPersonBrowserPanel() {
        return personBrowserPanel;
    }

    public ModuleBrowserPanelHandle getModuleBrowserPanel() {
        return moduleBrowserPanel;
    }

    public OccasionBrowserPanelHandle getOccasionBrowserPanel() {
        return occasionBrowserPanel;
    }
}
