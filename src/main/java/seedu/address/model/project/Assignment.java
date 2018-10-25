package seedu.address.model.project;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Name;

/**
 * Represents a Project in the address book.
 */
public class Assignment {

    //identity field
    private final ProjectName projectName;
    private final Name author;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Assignment(ProjectName pName, Name author, Description
            description) {
        requireAllNonNull(pName, author, description);
        this.projectName = pName;
        this.author = author;
        this.description = description;
    }

    public ProjectName getProjectName() {
        return projectName;
    }

    public Name getAuthor() {
        return author;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both assignments of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                && otherAssignment.getProjectName().equals(getProjectName())
                && (otherAssignment.getAuthor().equals(getAuthor()));
    }

    /**
     * Returns true if both assignments have the same identity and data fields.
     * This defines a stronger notion of equality between two assignments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return otherAssignment.getProjectName().equals(getProjectName())
                && otherAssignment.getAuthor().equals(getAuthor())
                && otherAssignment.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(projectName, author, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getProjectName())
                .append(" Author: ")
                .append(getAuthor())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }
}
