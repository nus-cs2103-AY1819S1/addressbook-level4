package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertPanelDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StudentPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class StudentPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangeEventStub;

    private StudentPanel studentPanel;

    @Before
    public void setUp() {
        selectionChangeEventStub = new PersonPanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> studentPanel = new StudentPanel());
        uiPartRule.setUiPart(studentPanel);
    }

    @Test
    public void display() {
        postNow(selectionChangeEventStub);
        assertPanelDisplay(ALICE, studentPanel);

        selectionChangeEventStub = new PersonPanelSelectionChangedEvent(BENSON);
        guiRobot.interact(() -> studentPanel = new StudentPanel());
        uiPartRule.setUiPart(studentPanel);

        postNow(selectionChangeEventStub);
        assertPanelDisplay(BENSON, studentPanel);
    }

    /**
     * Asserts that {@code studentPanel} displays the details of {@code expectedPerson} correctly
     */
    private void assertPanelDisplay(Person expectedPerson, StudentPanel studentPanel) {
        guiRobot.pauseForHuman();
        StudentPanelHandle studentPanelHandle = new StudentPanelHandle(studentPanel.getRoot());

        assertPanelDisplaysPerson(expectedPerson, studentPanelHandle);
    }
}
