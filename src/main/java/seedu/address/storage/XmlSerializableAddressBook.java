package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyTaskManager src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code TaskManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedPerson}.
     */
    public TaskManager toModelType() throws IllegalValueException {
        TaskManager taskManager = new TaskManager();
        for (XmlAdaptedTask t : tasks) {
            Task task = t.toModelType();
            if (taskManager.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            taskManager.addTask(task);
        }
        return taskManager;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return tasks.equals(((XmlSerializableAddressBook) other).tasks);
    }
}
