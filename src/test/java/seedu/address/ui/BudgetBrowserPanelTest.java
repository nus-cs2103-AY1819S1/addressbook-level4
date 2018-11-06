package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BudgetBrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.CcaPanelSelectionChangedEvent;
import seedu.address.model.cca.CcaName;

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

    //TODO: find the correct file path
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, budgetBrowserPanelHandle.getLoadedUrl());

        // associated web page of a cca
        postNow(selectionChangedEventStub);
        URL expectedCcaUrl = new URL("file:/" + BudgetBrowserPanel.BUDGET_PAGE.replaceFirst(".", "Users/ericyjw"
            + "/Hallper"));

        waitUntilBrowserLoaded(budgetBrowserPanelHandle);
        assertEquals(expectedCcaUrl, budgetBrowserPanelHandle.getLoadedUrl());
    }

    @Test
    public void display_cca_directly() throws Exception {
        selectionChangedEventStub = new CcaPanelSelectionChangedEvent(BASKETBALL);

        guiRobot.interact(() -> budgetBrowserPanel = new BudgetBrowserPanel(new CcaName(VALID_CCA_NAME_BASKETBALL)));
        uiPartRule.setUiPart(budgetBrowserPanel);

        budgetBrowserPanelHandle = new BudgetBrowserPanelHandle(budgetBrowserPanel.getRoot());

        // associated web page of a cca
        postNow(selectionChangedEventStub);
        URL expectedCcaUrl = new URL("file:/" + BudgetBrowserPanel.BUDGET_PAGE.replaceFirst(".", "Users/ericyjw"
            + "/Hallper"));

        waitUntilBrowserLoaded(budgetBrowserPanelHandle);
        assertEquals(expectedCcaUrl, budgetBrowserPanelHandle.getLoadedUrl());
    }
}
