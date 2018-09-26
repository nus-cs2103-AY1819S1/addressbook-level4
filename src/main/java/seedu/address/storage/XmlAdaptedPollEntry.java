package seedu.address.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of a single poll entry.
 */
public class XmlAdaptedPollEntry {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Poll option's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = false)
    private List<XmlAdaptedPerson> personList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPollEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPollEntry() {}

    /**
     * Constructs an {@code XmlAdaptedPollEntry} with the given poll entry details.
     */
    public XmlAdaptedPollEntry(String name, LinkedList<Person> personList) {
        this.name = name;
        if (personList != null) {
            this.personList = personList.stream()
                    .map(XmlAdaptedPerson::new)
                    .collect(Collectors.toList());
        }
    }

    public String getOptionName() {
        return name;
    }

    public LinkedList<Person> getPersonList() throws IllegalValueException {
        LinkedList<Person> persons = new LinkedList<>();
        for (XmlAdaptedPerson p : personList) {
            try {
                persons.add(p.toModelType());
            } catch (IllegalValueException e) {
                throw e;
            }
        }
        return persons;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPollEntry)) {
            return false;
        }

        XmlAdaptedPollEntry otherPollEntry = (XmlAdaptedPollEntry) other;
        return Objects.equals(name, otherPollEntry.name)
                && personList.equals(otherPollEntry.personList);
    }
}
