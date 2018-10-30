package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Project;

/**
 * JAXB-friendly adapted version of the Project.
 */
public class XmlAdaptedProject {

    @XmlValue
    private String projectName;

    /**
     * Constructs an XmlAdaptedProject.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProject() {}

    /**
     * Constructs a {@code XmlAdaptedProject} with the given {@code projectName}.
     */
    public XmlAdaptedProject(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Converts a given Project into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedProject(Project source) {
        projectName = source.getProjectName();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Project toModelType() throws IllegalValueException {
        if (!Project.isValidProjectName(projectName)) {
            throw new IllegalValueException(Project.MESSAGE_PROJECT_CONSTRAINTS);
        }
        return new Project(projectName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProject)) {
            return false;
        }

        return projectName.equals(((XmlAdaptedProject) other).projectName);
    }
}
