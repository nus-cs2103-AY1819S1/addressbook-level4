package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Issue;
import seedu.address.model.issue.Description;
import seedu.address.model.issue.IssueStatement;
import seedu.address.model.issue.Solution;
import seedu.address.model.issue.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Issue objects.
 */
public class IssueBuilder {

    public static final String DEFAULT_STATEMENT = "Java Problem";
    public static final String DEFAULT_DESCRIPTION = "new bug";

    private IssueStatement statement;
    private Description description;
    private List<Solution> solutions;
    private Set<Tag> tags;

    public IssueBuilder() {
        statement = new IssueStatement(DEFAULT_STATEMENT);
        description = new Description(DEFAULT_DESCRIPTION);;
        solutions = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the IssueBuilder with the data of {@code issueToCopy}.
     */
    public IssueBuilder(Issue issueToCopy) {
        statement = issueToCopy.getStatement();
        description = issueToCopy.getDescription();
        solutions = new ArrayList<>(issueToCopy.getSolutions());
        tags = new HashSet<>(issueToCopy.getTags());
    }

    /**
     * Sets the {@code IssueStatement} of the {@code Issue} that we are building.
     */
    public IssueBuilder withStatement(String statement) {
        this.statement = new IssueStatement(statement);
        return this;
    }

    /**
     * Parses the {@code solutions} into a {@code Set<Solution>} and set it to the {@code Issue} that we are building.
     */
    public IssueBuilder withSolutions (String ... solutions) {
        this.solutions = SampleDataUtil.getSolutionSet(solutions);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Issue} that we are building.
     */
    public IssueBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Issue} that we are building.
     */
    public IssueBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Issue build() {
        return new Issue(statement, description, solutions, tags);
    }

}
