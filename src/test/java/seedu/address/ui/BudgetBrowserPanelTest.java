package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BudgetBrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.CcaPanelSelectionChangedEvent;

//@@author ericyjw
public class BudgetBrowserPanelTest extends GuiUnitTest {
    private CcaPanelSelectionChangedEvent selectionChangedEventStub;

    private BudgetBrowserPanel budgetBrowserPanel;
    private BudgetBrowserPanelHandle budgetBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new CcaPanelSelectionChangedEvent(BASKETBALL);

        guiRobot.interact(() -> budgetBrowserPanel = new BudgetBrowserPanel());
        uiPartRule.setUiPart(budgetBrowserPanel);

        budgetBrowserPanelHandle = new BudgetBrowserPanelHandle(budgetBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, budgetBrowserPanelHandle.getLoadedUrl());
    }
}
