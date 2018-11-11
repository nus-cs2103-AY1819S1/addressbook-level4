package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonBrowserPanelHandle;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;

public class PersonBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private PersonBrowserPanel browserPanel;
    private PersonBrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new PersonBrowserPanel(ALICE
                .getModuleList().asUnmodifiableObservableList(),
                ALICE.getOccasionList().asUnmodifiableObservableList()));

        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new PersonBrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() {
        ObservableList<Module> moduleList = browserPanelHandle.getCurrentModules();
        ObservableList<Occasion> occasionList = browserPanelHandle.getCurrentOccasions();
        assertEquals(occasionList, ALICE.getOccasionList().asUnmodifiableObservableList());
        assertEquals(moduleList, ALICE.getModuleList().asUnmodifiableObservableList());
    }
}
