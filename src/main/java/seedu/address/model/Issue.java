package seedu.address.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.issue.IssueStatement;
import seedu.address.model.issue.Remark;
import seedu.address.model.issue.Solution;
import seedu.address.model.issue.Tag;

/**
 * Represents a Issue in the remark book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Issue {

    // Identity fields
    private final IssueStatement statement;

    // Data fields
    private final Remark remark;
    private final Set<Solution> solutions = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Issue(IssueStatement statement, Remark remark, Set<Solution> solutions, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(statement, remark, solutions, tags);
        this.statement = statement;
        this.remark = remark;
        this.solutions.addAll(solutions);
        this.tags.addAll(tags);
    }

    public IssueStatement getStatement() {
        return statement;
    }

    public Set<Solution> getSolutions() {
        return Collections.unmodifiableSet(solutions);
    }

    public Remark getAddress() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same statement have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.Solution
     */
    public boolean isSameIssue(Issue otherIssue) {
        if (otherIssue == this) {
            return true;
        }

        return otherIssue != null
                && otherIssue.getStatement().equals(getStatement());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
                && otherIssue.getAddress().equals(getAddress())
                && otherIssue.getSolutions().equals(getSolutions())
                && otherIssue.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(statement, remark, solutions, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getStatement())
                .append(" Phone: ")
                .append(" Remark: ")
                .append(getAddress())
                .append(" Tags: ");
        getSolutions().forEach(builder::append);
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
