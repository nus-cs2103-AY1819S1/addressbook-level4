package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameIssue comparison)
 */
public class SaveIt implements ReadOnlySaveIt {

    private final UniqueIssueList issues;
    private int currentDirectory;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        issues = new UniqueIssueList();
        currentDirectory = 0;
    }

    public SaveIt() {}

    /**
     * Creates an SaveIt using the Issues in the {@code toBeCopied}
     */
    public SaveIt(ReadOnlySaveIt toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the issue list with {@code issues}.
     * {@code issues} must not contain duplicate issues.
     */
    public void setIssues(List<Issue> issues) {
        this.issues.setIssues(issues);
    }

    /**
     * Update the current directory.
     * {@code CurrentDirectory} must not exceeds the length of {@code issues}.
     */
    public void setCurrentDirectory(int directory) {
        try {
            if (currentDirectory > issues.size()) {
                throw new IllegalValueException("Refer to non-existent directory.");
            }
            currentDirectory = directory;
        } catch (IllegalValueException e) {
            e.getMessage();
        }
    }

    /**
     * Resets the existing data of this {@code SaveIt} with {@code newData}.
     */
    public void resetData(ReadOnlySaveIt newData) {
        requireNonNull(newData);

        setIssues(newData.getIssueList());
        setCurrentDirectory(newData.getCurrentDirectory());
    }

    //// issue-level operations

    /**
     * Returns true if an issue with the same identity as {@code issue} exists in the saveIt.
     */
    public boolean hasIssue(Issue issue) {
        requireNonNull(issue);
        return issues.contains(issue);
    }

    /**
     * Adds an issue to the saveIt.
     * The issue must not already exist in the saveIt.
     */
    public void addIssue(Issue p) {
        issues.add(p);
    }

    /**
     * Replaces the given issue {@code target} in the list with {@code editedIssue}.
     * {@code target} must exist in the saveIt.
     * The issue identity of {@code editedIssue} must not be the same as another existing issue in the saveIt.
     */
    public void updateIssue(Issue target, Issue editedIssue) {
        requireNonNull(editedIssue);

        issues.setIssue(target, editedIssue);
    }

    /**
     * Removes {@code key} from this {@code SaveIt}.
     * {@code key} must exist in the saveIt.
     */
    public void removeIssue(Issue key) {
        issues.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return issues.asUnmodifiableObservableList().size() + " issues";
        // TODO: refine later
    }

    @Override
    public ObservableList<Issue> getIssueList() {
        return issues.asUnmodifiableObservableList();
    }

    @Override
    public int getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveIt // instanceof handles nulls
                && issues.equals(((SaveIt) other).issues));
    }

    @Override
    public int hashCode() {
        return issues.hashCode();
    }
}
