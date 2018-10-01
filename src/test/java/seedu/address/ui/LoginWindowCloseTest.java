package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import guitests.guihandles.StageHandle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import seedu.address.commons.core.Config;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains tests for closing of the {@code LoginWindow}.
 */
public class LoginWindowCloseTest extends GuiUnitTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private LoginWindow loginWindow;
    private EmptyLoginWindowHandle loginWindowHandle;
    private Stage stage;

    @Before
    public void setUp() throws Exception {
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            loginWindow = new LoginWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            loginWindowHandle = new EmptyLoginWindowHandle(stage);

            stage.setScene(loginWindow.getRoot().getScene());
            loginWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void close_menuBarExitButton_exitAppRequestEventPosted() {
        loginWindowHandle.clickOnMenuExitButton();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void close_externalRequest_exitAppRequestEventPosted() {
        loginWindowHandle.closeLoginWindowExternally();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * A handle for an empty {@code LoginWindow}. The components in {@code LoginWindow} are not initialized.
     */
    private class EmptyLoginWindowHandle extends StageHandle {

        private EmptyLoginWindowHandle(Stage stage) {
            super(stage);
        }

        /**
         * Closes the {@code LoginWindow} by clicking on the menu bar's exit button.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Closes the {@code LoginWindow} through an external request {@code LoginWindow} (e.g pressing the 'X' button on
         * the {@code LoginWindow} or closing the app through the taskbar).
         */
        private void closeLoginWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }
    }
}
