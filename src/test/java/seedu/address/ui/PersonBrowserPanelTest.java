package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import guitests.guihandles.PersonBrowserPanelHandle;
import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.UniqueOccasionList;

public class PersonBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private PersonBrowserPanel personBrowserPanel;
    private PersonBrowserPanelHandle personBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);

        // Load up the panel with an empty occasions and modules table.
        guiRobot.interact(() -> personBrowserPanel = new PersonBrowserPanel(new UniqueModuleList()
                                                                .asUnmodifiableObservableList(),
                                                        new UniqueOccasionList().asUnmodifiableObservableList()));
        uiPartRule.setUiPart(personBrowserPanel);

        personBrowserPanelHandle = new PersonBrowserPanelHandle(personBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception { // TODO COMPLETE THIS TEST.
        postNow(selectionChangedEventStub);

    }
}
