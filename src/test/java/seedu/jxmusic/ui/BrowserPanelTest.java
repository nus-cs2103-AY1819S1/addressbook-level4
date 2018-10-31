//package seedu.jxmusic.ui;
//
//import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
//import static org.junit.Assert.assertEquals;
//import static seedu.jxmusic.testutil.EventsUtil.postNow;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.ui.BrowserPanel.DEFAULT_PAGE;
//import static seedu.jxmusic.ui.UiPart.FXML_FILE_FOLDER;
//
//import java.net.URL;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import guitests.guihandles.BrowserPanelHandle;
//import seedu.jxmusic.MainApp;
//import seedu.jxmusic.commons.events.ui.PlaylistPanelSelectionChangedEvent;
//
//public class BrowserPanelTest extends GuiUnitTest {
//    private PlaylistPanelSelectionChangedEvent selectionChangedEventStub;
//
//    private BrowserPanel browserPanel;
//    private BrowserPanelHandle browserPanelHandle;
//
//    @Before
//    public void setUp() {
//        selectionChangedEventStub = new PlaylistPanelSelectionChangedEvent(SFX);
//
//        guiRobot.interact(() -> browserPanel = new BrowserPanel());
//        uiPartRule.setUiPart(browserPanel);
//
//        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
//    }
//
//    @Test
//    public void display() throws Exception {
//        // default web page
//        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
//        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());
//
//        // associated web page of a playlist
//        postNow(selectionChangedEventStub);
//        URL expectedPersonUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + SFX.getName()
//                .nameString.replaceAll(" ", "%20"));
//
//        waitUntilBrowserLoaded(browserPanelHandle);
//        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
//    }
//}
