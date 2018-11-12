package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StaffPanelHandle;
import seedu.address.model.person.Person;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getDepartment(), actualCard.getDepartment());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getDepartment().value, actualCard.getDepartment());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualPanel} displays the details of {@code expectedPerson}.
     */
    public static void assertPanelDisplaysPerson(Person expectedPerson, StaffPanelHandle actualPanel) {
        assertEquals(expectedPerson.getName().fullName, actualPanel.getName());
        if (expectedPerson.getPhone().isPrivate()) {
            assertEquals("Private", actualPanel.getPhone());
        } else {
            assertEquals(expectedPerson.getPhone().value, actualPanel.getPhone());
        }
        if (expectedPerson.getEmail().isPrivate()) {
            assertEquals("Private", actualPanel.getEmail());
        } else {
            assertEquals(expectedPerson.getEmail().value, actualPanel.getEmail());
        }
        if (expectedPerson.getAddress().isPrivate()) {
            assertEquals("Private", actualPanel.getAddress());
        } else {
            assertEquals(expectedPerson.getAddress().value, actualPanel.getAddress());
        }
        double salary = Double.valueOf(expectedPerson.getSalary().salary);
        double otPay = Double.valueOf(expectedPerson.getOtHours().overTimeHour)
            * Double.valueOf(expectedPerson.getOtRate().overTimeRate);
        double deductibles = Double.valueOf(expectedPerson.getDeductibles().payDeductibles);
        double expectedNetSalary = salary + otPay - deductibles;
        assertEquals(Double.toString(expectedNetSalary), actualPanel.getSalary());
        assertEquals(expectedPerson.getDepartment().value, actualPanel.getDepartment());
        assertEquals(expectedPerson.getManager().fullName, actualPanel.getManager());
        assertEquals(expectedPerson.getRating().value, actualPanel.getRating());
        assertEquals(expectedPerson.getFeedback().value, actualPanel.getFeedback());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
