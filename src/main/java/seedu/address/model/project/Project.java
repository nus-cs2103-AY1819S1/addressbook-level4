package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Project in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidProjectName(String)}
 */
public class Project {

    public static final String MESSAGE_PROJECT_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String PROJECT_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String projectName;

    /**
     * Constructs a {@code Project}.
     *
     * @param projectName A valid project name.
     */
    public Project(String projectName) {
      requireNonNull(projectName);
      checkArgument(isValidProjectName(projectName), MESSAGE_PROJECT_CONSTRAINTS);
      this.projectName = projectName;
    }

    /**
     * Returns true if a given string is a valid project name.
     */
    public static boolean isValidProjectName(String test) {
    return test.matches(PROJECT_VALIDATION_REGEX);
  }

    @Override
    public boolean equals(Object other) {
      return other == this // short circuit if same object
              || (other instanceof Project // instanceof handles nulls
              && projectName.equals(((Project) other).projectName)); // state check
    }

    @Override
    public int hashCode() {
    return projectName.hashCode();
  }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
    return '[' + projectName + ']';
  }

}

