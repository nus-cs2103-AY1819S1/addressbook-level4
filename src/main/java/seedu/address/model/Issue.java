package seedu.address.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.issue.Description;
import seedu.address.model.issue.IssueSearchFrequency;
import seedu.address.model.issue.IssueStatement;
import seedu.address.model.issue.Solution;
import seedu.address.model.issue.Tag;

/**
 * Represents a Issue in the remark book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Issue {

    // Identity fields
    private final IssueStatement statement;
    private final IssueSearchFrequency frequency;

    // Data fields
    private final List<Solution> solutions = new ArrayList<>();
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Issue(IssueStatement statement, Description description, List<Solution> solutions, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(statement, description, solutions, tags);
        this.statement = statement;
        this.description = description;
        this.solutions.addAll(solutions);
        this.tags.addAll(tags);
        this.frequency = new IssueSearchFrequency(0);
    }

    /**
     * Overloaded constructor with additional {@code frequency} field
     */
    public Issue(IssueStatement statement, Description description, List<Solution> solutions,
                 Set<Tag> tags, IssueSearchFrequency frequency) {
        CollectionUtil.requireAllNonNull(statement, description, solutions, tags);
        this.statement = statement;
        this.description = description;
        this.solutions.addAll(solutions);
        this.tags.addAll(tags);
        this.frequency = frequency;
    }

    public IssueStatement getStatement() {
        return statement;
    }

    /**
     * Returns an immutable solution List, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Solution> getSolutions() {
        return Collections.unmodifiableList(solutions);
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the IssueSearchFrequency of the Issue
     */
    public IssueSearchFrequency getFrequency() {
        return frequency;
    }

    /**
     * Updates the search frequency of the current issue
     */
    public void updateFrequency() {
        frequency.increment();
    }

    /**
     * Returns true if both issues of the same statement have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two issues.Solution
     */
    public boolean isSameIssue(Issue otherIssue) {
        if (otherIssue == this) {
            return true;
        }

        return otherIssue != null
                && otherIssue.getStatement().equals(getStatement())
                && otherIssue.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both issues have the same identity and data fields.
     * This defines a stronger notion of equality between two issues.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Issue)) {
            return false;
        }

        Issue otherIssue = (Issue) other;
        return otherIssue.getStatement().equals(getStatement())
                && otherIssue.getSolutions().equals(getSolutions())
                && otherIssue.getDescription().equals(getDescription())
                && otherIssue.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(statement, description, solutions, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getStatement())
                .append(" Description: ")
                .append(getDescription())
                .append(" Solutions: ");
        getSolutions().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
