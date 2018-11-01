package seedu.address.testutil;

import seedu.address.model.ArchiveList;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building ArchiveList objects.
 * Example usage: <br>
 *     {@code ArchiveList al = new ArchiveListBuilder().withPerson("John", "Doe").build();}
 */
public class ArchiveListBuilder {

    private ArchiveList archiveList;

    public ArchiveListBuilder() {
        archiveList = new ArchiveList();
    }

    public ArchiveListBuilder(ArchiveList archiveList) {
        this.archiveList = archiveList;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public ArchiveListBuilder withPerson(Person person) {
        archiveList.addPerson(person);
        return this;
    }

    public ArchiveList build() {
        return archiveList;
    }
}
