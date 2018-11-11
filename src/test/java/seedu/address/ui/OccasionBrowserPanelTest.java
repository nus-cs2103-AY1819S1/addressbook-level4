package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_ONE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.OccasionBrowserPanelHandle;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.OccasionPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class OccasionBrowserPanelTest extends GuiUnitTest {
    private OccasionPanelSelectionChangedEvent selectionChangedEventStub;

    private OccasionBrowserPanel browserPanel;
    private OccasionBrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new OccasionPanelSelectionChangedEvent(TYPICAL_OCCASION_ONE);

        guiRobot.interact(() -> browserPanel = new OccasionBrowserPanel(TYPICAL_OCCASION_ONE
                                            .getAttendanceList().asUnmodifiableObservableList()));

        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new OccasionBrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() {
        ObservableList<Person> personList = browserPanelHandle.getPersonItems();
        assertEquals(personList, TYPICAL_OCCASION_ONE.getAttendanceList().asUnmodifiableObservableList());
    }

}
