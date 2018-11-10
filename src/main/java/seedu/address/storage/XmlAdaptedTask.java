package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Label;
import seedu.address.model.task.Dependencies;
import seedu.address.model.task.Description;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Name;
import seedu.address.model.task.PriorityValue;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dueDate;
    @XmlElement(required = true)
    private String priorityValue;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedLabel> labelled = new ArrayList<>();
    @XmlElement
    private String status;
    @XmlElement
    private Set<String> dependencies = new HashSet<>();
    @XmlElement
    private String hash;
    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String dueDate, String priorityValue,
                          String description, List<XmlAdaptedLabel> labelled, List<String> dependencies) {
        this.name = name;
        this.dueDate = dueDate;
        this.priorityValue = priorityValue;
        this.description = description;
        if (labelled != null) {
            this.labelled = new ArrayList<>(labelled);
        }
        this.status = Status.IN_PROGRESS.toString();
        if (dependencies != null) {
            this.dependencies = new HashSet<>(dependencies);
        }
    }

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String dueDate, String priorityValue,
                          String description, List<XmlAdaptedLabel> labelled, Status status) {
        this.name = name;
        this.dueDate = dueDate;
        this.priorityValue = priorityValue;
        this.description = description;
        if (labelled != null) {
            this.labelled = new ArrayList<>(labelled);
        }
        this.status = status.toString();
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName().fullName;
        dueDate = source.getDueDate().value;
        priorityValue = source.getPriorityValue().value;
        description = source.getDescription().value;
        labelled = source.getLabels().stream()
                .map(XmlAdaptedLabel::new)
                .collect(Collectors.toList());
        status = source.getStatus().toString();
        hash = Integer.toString(source.hashCode());
        dependencies = source.getDependency().getHashes();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Label> taskLabels = new ArrayList<>();
        for (XmlAdaptedLabel label : labelled) {
            taskLabels.add(label.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName()));
        }
        if (!DueDate.isValidDueDateFormat(dueDate)) {
            throw new IllegalValueException(DueDate.MESSAGE_DUEDATE_CONSTRAINTS);
        }
        final DueDate modelDueDate = new DueDate(dueDate);

        if (priorityValue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PriorityValue.class.getSimpleName()));
        }
        if (!PriorityValue.isValidPriorityValue(priorityValue)) {
            throw new IllegalValueException(PriorityValue.MESSAGE_PRIORITY_VALUE_CONSTRAINTS);
        }
        final PriorityValue modelPriorityValue = new PriorityValue(priorityValue);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Label> modelLabels = new HashSet<>(taskLabels);
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_STATUS_CONSTRAINTS);
        }
        final Status modelStatus = Status.fromString(status);
        final Dependencies dependency = new Dependencies(dependencies);

        return new Task(modelName, modelDueDate, modelPriorityValue, modelDescription, modelLabels, modelStatus,
                dependency);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(name, otherTask.name)
                && Objects.equals(dueDate, otherTask.dueDate)
                && Objects.equals(priorityValue, otherTask.priorityValue)
                && Objects.equals(description, otherTask.description)
                && labelled.equals(otherTask.labelled)
                && Objects.equals(status, otherTask.status);
    }
}
