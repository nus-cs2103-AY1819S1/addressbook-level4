package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableTaskManager.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(ReadOnlyAddressBook src) {
        this();
        tasks.addAll(src.getPersonList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this taskmanager into the model's {@code TaskManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook taskManager = new AddressBook();
        for (XmlAdaptedTask p : tasks) {
            Person task = p.toModelType();
            if (taskManager.hasPerson(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            taskManager.addPerson(task);
        }
        return taskManager;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTaskManager)) {
            return false;
        }
        return tasks.equals(((XmlSerializableTaskManager) other).tasks);
    }
}
