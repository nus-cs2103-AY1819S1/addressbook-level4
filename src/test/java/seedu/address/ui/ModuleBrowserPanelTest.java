package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_ONE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ModuleBrowserPanelHandle;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class ModuleBrowserPanelTest extends GuiUnitTest {
    private ModulePanelSelectionChangedEvent selectionChangedEventStub;

    private ModuleBrowserPanel browserPanel;
    private ModuleBrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ModulePanelSelectionChangedEvent(TYPICAL_MODULE_ONE);

        guiRobot.interact(() -> browserPanel = new ModuleBrowserPanel(TYPICAL_MODULE_ONE
                .getStudents().asUnmodifiableObservableList()));

        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new ModuleBrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() {
        ObservableList<Person> personList = browserPanelHandle.getPersonItems();
        assertEquals(personList, TYPICAL_MODULE_ONE.getStudents().asUnmodifiableObservableList());
    }
}
