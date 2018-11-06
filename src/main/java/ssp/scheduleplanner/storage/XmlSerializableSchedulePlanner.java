package ssp.scheduleplanner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ssp.scheduleplanner.commons.exceptions.IllegalValueException;
import ssp.scheduleplanner.model.ReadOnlySchedulePlanner;
import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.task.Task;

/**
 * An Immutable SchedulePlanner that is serializable to XML format
 */
@XmlRootElement(name = "scheduleplanner")
public class XmlSerializableSchedulePlanner {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";
    public static final String MESSAGE_DUPLICATE_CATEGORY = "Category list contains duplicate category.";

    @XmlElement
    private List<XmlAdaptedCategory> categories;

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    @XmlElement
    private List<XmlAdaptedTask> archivedTasks;

    /**
     * Creates an empty XmlSerializableSchedulePlanner.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSchedulePlanner() {
        categories = new ArrayList<>();
        tasks = new ArrayList<>();
        archivedTasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSchedulePlanner(ReadOnlySchedulePlanner src) {
        this();
        categories.addAll(src.getCategoryList().stream().map(XmlAdaptedCategory::new).collect(Collectors.toList()));
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        archivedTasks.addAll(src.getArchivedTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this schedule planner into the model's {@code SchedulePlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask}.
     */
    public SchedulePlanner toModelType() throws IllegalValueException {
        SchedulePlanner schedulePlanner = new SchedulePlanner();
        for (XmlAdaptedCategory c: categories) {
            Category category = c.toModelType();
            String name = category.getName();
            if (name.equals("Modules") || name.equals("Others")) {
                schedulePlanner.removeCategory(name);
            }
            if (schedulePlanner.hasCategory(name)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CATEGORY);
            }
            schedulePlanner.addCategory(category);
        }

        for (XmlAdaptedTask p : tasks) {
            Task task = p.toModelType();
            if (schedulePlanner.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            schedulePlanner.addTask(task);
        }

        for (XmlAdaptedTask p : archivedTasks) {
            Task archivedTask = p.toModelType();
            schedulePlanner.addArchivedTask(archivedTask);
        }
        return schedulePlanner;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSchedulePlanner)) {
            return false;
        }
        return (tasks.equals(((XmlSerializableSchedulePlanner) other).tasks))
                && (archivedTasks.equals(((XmlSerializableSchedulePlanner) other).archivedTasks))
                && (categories.equals(((XmlSerializableSchedulePlanner) other).categories));
    }
}
