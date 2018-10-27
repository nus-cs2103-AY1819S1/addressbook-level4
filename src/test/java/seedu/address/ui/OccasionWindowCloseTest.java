package seedu.address.ui;

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

import static org.junit.Assert.assertTrue;

public class OccasionWindowCloseTest extends GuiUnitTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private OccasionWindow occasionWindow;
    private OccasionWindowCloseTest.EmptyMainWindowHandle mainWindowHandle;
    private Stage stage;

    @Before
    public void setUp() throws Exception {
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            occasionWindow = new OccasionWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            mainWindowHandle = new OccasionWindowCloseTest.EmptyMainWindowHandle(stage);

            stage.setScene(occasionWindow.getRoot().getScene());
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
     * A handle for an empty {@code OccasionWindow}. The components in {@code OccasionWindow} are not initialized.
     */
    private class EmptyMainWindowHandle extends StageHandle {

        private EmptyMainWindowHandle(Stage stage) {
            super(stage);
        }

        /**
         * Closes the {@code OccasionWindow} by clicking on the menu bar's exit button.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Closes the {@code OccasionWindow} through an external request {@code OccasionWindow} (e.g pressing the 'X' button on
         * the {@code OccasionWindow} or closing the app through the taskbar).
         */
        private void closeMainWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }
    }
}
