package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static seedu.address.ui.DocumentWindow.DOCUMENT_TEMPLATE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.DocumentWindowHandle;
import javafx.stage.Stage;

public class DocumentWindowTest extends GuiUnitTest {

    private DocumentWindow documentWindow;
    private DocumentWindowHandle documentWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> documentWindow = new DocumentWindow());
        FxToolkit.registerStage(documentWindow::getRoot);
        documentWindowHandle = new DocumentWindowHandle(documentWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
        URL expectedDocumentPage = DocumentWindow.class.getResource(DOCUMENT_TEMPLATE_FILE_PATH);
        assertEquals(expectedDocumentPage, documentWindowHandle.getLoadedUrl());
    }

    @Test
    public void isShowing_helpWindowIsShowing_returnsTrue() {
        guiRobot.interact(documentWindow::showTest);
        assertTrue(documentWindow.isShowing());
    }

    @Test
    public void isShowing_helpWindowIsHiding_returnsFalse() {
        assertFalse(documentWindow.isShowing());
    }

    @Test
    public void focus_helpWindowNotFocused_focused() throws Exception {
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
        guiRobot.interact(documentWindow::showTest);

        // Focus on another stage to remove focus from the helpWindow
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(documentWindow.getRoot().isFocused());

        guiRobot.interact(documentWindow::focus);
        assertTrue(documentWindow.getRoot().isFocused());
    }
}
