package seedu.thanepark.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HistoryWindowHandle;
import javafx.stage.Stage;
import seedu.thanepark.commons.core.StorageFileCreatingClass;
import seedu.thanepark.commons.util.FilePathToUrl;
import seedu.thanepark.ui.browser.HistoryWindow;

public class HistoryWindowTest extends GuiUnitTest {

    private HistoryWindow historyWindow;
    private HistoryWindowHandle historyWindowHandle;
    private URL expectedHistoryPage;
    private File file;

    @Before
    public void setUp() throws Exception {
        final String testFilePath = StorageFileCreatingClass.getFilePathString("test.html");
        file = new File(testFilePath);
        assert (file.createNewFile());
        final FilePathToUrl filePathToUrl = new FilePathToUrl(testFilePath, true);
        expectedHistoryPage = filePathToUrl.filePathToUrl();

        guiRobot.interact(() -> historyWindow = new HistoryWindow());
        historyWindow.loadPage(filePathToUrl);
        FxToolkit.registerStage(historyWindow::getRoot);
        historyWindowHandle = new HistoryWindowHandle(historyWindow.getRoot());
    }

    @After
    public void tearDown() {
        assert (file.delete());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
        assertEquals(expectedHistoryPage, historyWindowHandle.getLoadedUrl());
    }

    @Test
    public void isShowing_historyWindowIsShowing_returnsTrue() {
        guiRobot.interact(historyWindow::show);
        assertTrue(historyWindow.isShowing());
    }

    @Test
    public void isShowing_historyWindowIsHiding_returnsFalse() {
        assertFalse(historyWindow.isShowing());
    }

    @Test
    public void focus_historyWindowNotFocused_focused() {
        // TODO: This test skip can be removed once this bug is fixed:
        // https://github.com/javafxports/openjdk-jfx/issues/50
        //
        // When there are two stages (stage1 and stage2) shown,
        // stage1 is in focus and stage2.requestFocus() is called,
        // we expect that stage1.isFocused() will return false while
        // stage2.isFocused() returns true. However, as reported in the bug report,
        // both stage1.isFocused() and stage2.isFocused() returns true,
        // which fails the test.
        assumeFalse("Test skipped in headless mode: Window focus behavior is buggy.", guiRobot.isHeadlessMode());
        guiRobot.interact(historyWindow::show);

        // Focus on another stage to remove focus from the historyWindow
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(historyWindow.getRoot().isFocused());

        guiRobot.interact(historyWindow::focus);
        assertTrue(historyWindow.getRoot().isFocused());
    }
}
