package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.StageHandle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.commons.core.Config;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ModuleWindowCloseTest extends GuiUnitTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ModuleWindow moduleWindow;
    private ModuleWindowCloseTest.EmptyMainWindowHandle mainWindowHandle;
    private Stage stage;

    @Before
    public void setUp() throws Exception {
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            moduleWindow = new ModuleWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            mainWindowHandle = new ModuleWindowCloseTest.EmptyMainWindowHandle(stage);

            stage.setScene(moduleWindow.getRoot().getScene());
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void close_menuBarExitButton_exitAppRequestEventPosted() {
        mainWindowHandle.clickOnMenuExitButton();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void close_externalRequest_exitAppRequestEventPosted() {
        mainWindowHandle.closeMainWindowExternally();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * A handle for an empty {@code ModuleWindow}. The components in {@code ModuleWindow} are not initialized.
     */
    private class EmptyMainWindowHandle extends StageHandle {

        private EmptyMainWindowHandle(Stage stage) {
            super(stage);
        }

        /**
         * Closes the {@code ModuleWindow} by clicking on the menu bar's exit button.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Closes the {@code ModuleWindow} through an external request {@code ModuleWindow}
         * (e.g pressing the 'X' button on
         * the {@code ModuleWindow} or closing the app through the taskbar).
         */
        private void closeMainWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }
    }
}
