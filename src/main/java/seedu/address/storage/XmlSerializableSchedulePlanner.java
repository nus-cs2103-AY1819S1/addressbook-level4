package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySchedulePlanner;
import seedu.address.model.SchedulePlanner;
import seedu.address.model.task.Task;

/**
 * An Immutable SchedulePlanner that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableSchedulePlanner {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate task(s).";

    @XmlElement
    private List<XmlAdaptedTask> persons;

    /**
     * Creates an empty XmlSerializableSchedulePlanner.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSchedulePlanner() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSchedulePlanner(ReadOnlySchedulePlanner src) {
        this();
        persons.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code SchedulePlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask}.
     */
    public SchedulePlanner toModelType() throws IllegalValueException {
        SchedulePlanner addressBook = new SchedulePlanner();
        for (XmlAdaptedTask p : persons) {
            Task task = p.toModelType();
            if (addressBook.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addTask(task);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSchedulePlanner)) {
            return false;
        }
        return persons.equals(((XmlSerializableSchedulePlanner) other).persons);
    }
}
