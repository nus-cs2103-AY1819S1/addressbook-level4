package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Issue;
import seedu.address.model.issue.Description;
import seedu.address.model.issue.IssueStatement;
import seedu.address.model.issue.Solution;
import seedu.address.model.issue.Tag;

/**
 * JAXB-friendly version of the Issue.
 */
public class XmlAdaptedIssue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Issue's %s field is missing!";

    @XmlElement(required = true)
    private String statement;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedSolution> solutions = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedIssue. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedIssue() {
    }

    /**
     * Constructs an {@code XmlAdaptedIssue} with the given statement details.
     */
    public XmlAdaptedIssue(String statement, String description, List<XmlAdaptedSolution> solutions,
                           List<XmlAdaptedTag> tagged) {
        this.statement = statement;
        this.description = description;
        if (solutions != null) {
            this.solutions = new ArrayList<>(solutions);
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Issue into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedIssue
     */
    public XmlAdaptedIssue(Issue source) {
        statement = source.getStatement().issue;
        description = source.getDescription().value;
        solutions = source.getSolutions().stream()
                .map(XmlAdaptedSolution::new)
                .collect(Collectors.toList());
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted statement object into the model's Issue object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted statement
     */
    public Issue toModelType() throws IllegalValueException {
        final List<Tag> issueTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            issueTags.add(tag.toModelType());
        }

        final List<Solution> issueSolutions = new ArrayList<>();
        for (XmlAdaptedSolution solution : solutions) {
            issueSolutions.add(solution.toModelType());
        }

        if (statement == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IssueStatement.class.getSimpleName()));
        }
        if (!IssueStatement.isValidIssueStatement(statement)) {
            throw new IllegalValueException(IssueStatement.MESSAGE_ISSUE_STATEMENT_CONSTRAINTS);
        }
        final IssueStatement modelName = new IssueStatement(statement);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final List<Solution> modelSolutions = new ArrayList<>(issueSolutions);

        final Set<Tag> modelTags = new HashSet<>(issueTags);
        return new Issue(modelName, modelDescription, modelSolutions, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedIssue)) {
            return false;
        }

        XmlAdaptedIssue otherPerson = (XmlAdaptedIssue) other;
        return Objects.equals(statement, otherPerson.statement)
                && Objects.equals(description, otherPerson.description)
                && solutions.equals(otherPerson.solutions)
                && tagged.equals(otherPerson.tagged);
    }
}
