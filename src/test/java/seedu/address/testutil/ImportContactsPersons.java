package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalTags.getTypicalTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class ImportContactsPersons {
    public static final Person JACK = new PersonBuilder().withName("Jack")
            .withAddress("12 Computing Drive")
            .withEmail("jack@gmail.com")
            .withPhone("91234567")
            .withFaculty("SOC").build();
    public static final Person KAITING = new PersonBuilder().withName("Kai Ting")
            .withAddress("12 Computing Dr")
            .withEmail("kaiting@gmail.com")
            .withPhone("98765432")
            .withFaculty("SOC").build();
    public static final Person PRATYAY = new PersonBuilder().withName("Pratyay")
            .withAddress("12 Computing Dr")
            .withEmail("pratyay@gmail.com")
            .withPhone("97777777")
            .withFaculty("SOC").build();
    public static final Person RYAN = new PersonBuilder().withName("Ryan")
            .withAddress("12 Computing Dr")
            .withEmail("ryan@gmail.com")
            .withPhone("98888888")
            .withFaculty("SOC").build();
    public static final Person YUWEI = new PersonBuilder().withName("Yu Wei")
            .withAddress("12 Computing Dr")
            .withEmail("yuwei@gmail.com")
            .withPhone("96666666")
            .withFaculty("SOC").build();

    private ImportContactsPersons() {}

    /**
     * Returns an {@code AddressBook} with all the typical persons and events.
     */
    public static AddressBook getImportContactsAddressBook() {
        AddressBook ab = new AddressBook();

        for (Person person : getImportContactsPersons()) {
            ab.addPerson(person);
        }

        return ab;
    }

    public static List<Person> getImportContactsPersons() {
        return new ArrayList<>(Arrays.asList(JACK, KAITING, PRATYAY, RYAN, YUWEI));
    }
}
