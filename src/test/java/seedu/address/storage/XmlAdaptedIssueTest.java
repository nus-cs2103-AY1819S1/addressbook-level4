package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedIssue.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.issue.Description;
import seedu.address.model.issue.IssueStatement;
import seedu.address.testutil.Assert;

public class XmlAdaptedIssueTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SOLUTION = "&StackπOverflow *remark";

    private static final String VALID_NAME = BENSON.getStatement().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final List<XmlAdaptedSolution> VALID_SOLUTIONS = BENSON.getSolutions().stream()
            .map(XmlAdaptedSolution::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(XmlAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedIssue issue = new XmlAdaptedIssue(BENSON);
        assertEquals(BENSON, issue.toModelType());
    }

    @Test
    public void toModelType_invalidStatement_throwsIllegalValueException() {
        XmlAdaptedIssue issue =
                new XmlAdaptedIssue(INVALID_NAME, VALID_DESCRIPTION, VALID_SOLUTIONS, VALID_TAGS);
        String expectedMessage = IssueStatement.MESSAGE_ISSUE_STATEMENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, issue::toModelType);
    }

    @Test
    public void toModelType_nullStatement_throwsIllegalValueException() {
        XmlAdaptedIssue issue = new XmlAdaptedIssue(null, VALID_DESCRIPTION, VALID_SOLUTIONS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IssueStatement.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, issue::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedIssue issue =
            new XmlAdaptedIssue(VALID_NAME, INVALID_DESCRIPTION, VALID_SOLUTIONS, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, issue::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedIssue issue = new XmlAdaptedIssue(VALID_NAME, null, VALID_SOLUTIONS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, issue::toModelType);
    }

    @Test
    public void toModelType_invalidSolutions_throwsIllegalValueException() {
        List<XmlAdaptedSolution> invalidSolutions = new ArrayList<>(VALID_SOLUTIONS);
        invalidSolutions.add(new XmlAdaptedSolution(INVALID_SOLUTION));
        XmlAdaptedIssue issue =
            new XmlAdaptedIssue(VALID_NAME, VALID_DESCRIPTION, invalidSolutions, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, issue::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedIssue issue =
            new XmlAdaptedIssue(VALID_NAME, VALID_DESCRIPTION, VALID_SOLUTIONS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, issue::toModelType);
    }

}
