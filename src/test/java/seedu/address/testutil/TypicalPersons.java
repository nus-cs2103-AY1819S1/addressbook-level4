package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATEMENT_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Issue;
import seedu.address.model.SaveIt;

/**
 * A utility class containing a list of {@code Issue} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Issue ALICE = new IssueBuilder().withStatement("Alice Pauline")
            .withDescription("94351253")
            .withTags("syntax").build();
    public static final Issue BENSON = new IssueBuilder().withStatement("Benson Meier")
            .withDescription("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Issue CARL = new IssueBuilder().withStatement("Carl Kurz").withDescription("95352563")
            .withSolutions("wall street").build();
    public static final Issue DANIEL = new IssueBuilder().withStatement("Daniel Meier").withDescription("87652533")
            .withSolutions("StackOverflow newSolution", "ZhiHu newSol").withTags("friends").build();
    public static final Issue ELLE = new IssueBuilder().withStatement("Elle Meyer").withDescription("9482224")
            .withSolutions("michegan ave").build();
    public static final Issue FIONA = new IssueBuilder().withStatement("Fiona Kunz").withDescription("9482427")
            .withSolutions("little tokyo").build();

    public static final Issue GEORGE = new IssueBuilder().withStatement("George Best").withDescription("9482442")
            .withSolutions("4th street").build();

    // Manually added
    public static final Issue HOON = new IssueBuilder().withStatement("Hoon Meier").withDescription("8482424")
            .withSolutions("little india").build();
    public static final Issue IDA = new IssueBuilder().withStatement("Ida Mueller").withDescription("8482131")
            .withSolutions("chicago ave").build();

    // Manually added - Issue's details found in {@code CommandTestUtil}
    public static final Issue AMY = new IssueBuilder().withStatement(VALID_STATEMENT_JAVA)
            .withDescription(VALID_DESCRIPTION_JAVA)
            .withSolutions(VALID_SOLUTION_JAVA).withTags(VALID_TAG_UI).build();
    public static final Issue BOB = new IssueBuilder().withStatement(VALID_STATEMENT_C)
            .withDescription(VALID_DESCRIPTION_C)
            .withSolutions(VALID_SOLUTION_C).withTags(VALID_TAG_UI).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code SaveIt} with all the typical persons.
     */
    public static SaveIt getTypicalSaveIt() {
        SaveIt ab = new SaveIt();
        for (Issue issue : getTypicalPersons()) {
            ab.addIssue(issue);
        }
        return ab;
    }

    public static List<Issue> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
