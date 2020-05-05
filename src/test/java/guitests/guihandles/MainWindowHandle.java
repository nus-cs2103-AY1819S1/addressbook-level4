package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final TaskListPanelHandle taskListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final SidebarPanelHandle sidebarPanel;
    private final ProgressBarPanelHandle progressBarPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        taskListPanel = new TaskListPanelHandle(getChildNode(TaskListPanelHandle.TASK_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        sidebarPanel = new SidebarPanelHandle(getChildNode(SidebarPanelHandle.SIDEBAR_ID));
        progressBarPanel = new ProgressBarPanelHandle(getChildNode(ProgressBarPanelHandle.PROGRESSBAR_ID));
    }

    public TaskListPanelHandle getTaskListPanel() {
        return taskListPanel;
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

    public SidebarPanelHandle getSidebarPanel() {
        return sidebarPanel;
    }

    public ProgressBarPanelHandle getProgressBarPanel() {
        return progressBarPanel;
    }
}
