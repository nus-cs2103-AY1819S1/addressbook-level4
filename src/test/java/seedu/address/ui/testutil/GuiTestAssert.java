package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ModuleCardHandle;
import guitests.guihandles.ModuleListPanelHandle;
import guitests.guihandles.OccasionCardHandle;
import guitests.guihandles.OccasionListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
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
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ModuleCardHandle expectedCard, ModuleCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(OccasionCardHandle expectedCard, OccasionCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedModule}
     */
    public static void assertCardDisplaysModule(Module expectedModule, ModuleCardHandle actualCard) {
        assertEquals(expectedModule.getModuleTitle().toString(), actualCard.getName());
        assertEquals(expectedModule.getModuleCode().toString() + ":", actualCard.getCode());
        assertEquals("Academic Year: " + expectedModule.getAcademicYear().toString(), actualCard.getAcademicYear());
        assertEquals("Semester: " + expectedModule.getSemester().toString(), actualCard.getSemester());
        assertEquals(expectedModule.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedOccasion}
     */
    public static void assertCardDisplaysOccasion(Occasion expectedOccasion, OccasionCardHandle actualCard) {
        assertEquals(expectedOccasion.getOccasionName().toString(), actualCard.getName());
        assertEquals("Date: " + expectedOccasion.getOccasionDate().toString(), actualCard.getDate());
        assertEquals(expectedOccasion.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
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
     * Asserts that the list in {@code moduleListPanelHandle} displays the details of {@code modules} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ModuleListPanelHandle moduleListPanelHandle, Module... modules) {
        for (int i = 0; i < modules.length; i++) {
            moduleListPanelHandle.navigateToCard(i);
            assertCardDisplaysModule(modules[i], moduleListPanelHandle.getModuleCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code occasionListPanelHandle} displays the details of {@code occasions} correctly and
     * in the correct order.
     */
    public static void assertListMatching(OccasionListPanelHandle occasionListPanelHandle, Occasion... occasions) {
        for (int i = 0; i < occasions.length; i++) {
            occasionListPanelHandle.navigateToCard(i);
            assertCardDisplaysOccasion(occasions[i], occasionListPanelHandle.getOccasionCardHandle(i));
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
     * Asserts that the list in {@code moduleListPanelHandle} displays the details of {@code modules} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ModuleListPanelHandle moduleListPanelHandle, List<Module> modules) {
        assertListMatching(moduleListPanelHandle, modules.toArray(new Module[0]));
    }

    /**
     * Asserts that the list in {@code occasionListPanelHandle} displays the details of {@code occasions} correctly and
     * in the correct order.
     */
    public static void assertListMatching(OccasionListPanelHandle occasionListPanelHandle, List<Occasion> occasions) {
        assertListMatching(occasionListPanelHandle, occasions.toArray(new Occasion[0]));
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
