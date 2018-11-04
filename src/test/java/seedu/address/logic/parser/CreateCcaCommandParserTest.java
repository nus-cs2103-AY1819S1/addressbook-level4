package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUDGET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OUTSTANDING_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPENT_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_EMPTY;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CreateCcaCommand;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author ericyjw
public class CreateCcaCommandParserTest {
    private CreateCcaCommandParser parser = new CreateCcaCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Cca expectedCca = new CcaBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .withHead("-")
            .withViceHead("-")
            .withBudget(Integer.valueOf(VALID_BUDGET_BASKETBALL))
            .withSpent(Integer.valueOf(VALID_SPENT_BASKETBALL))
            .withOutstanding(Integer.valueOf(VALID_OUTSTANDING_BASKETBALL))
            .withTransaction(TRANSACTION_EMPTY)
            .build();

        CreateCcaCommand newCca = new CreateCcaCommand(expectedCca);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL,
            new CreateCcaCommand(expectedCca));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BADMINTON + NAME_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL,
            new CreateCcaCommand(expectedCca));

        // multiple budgets - last budget accepted
        assertParseSuccess(parser, NAME_DESC_BASKETBALL + BUDGET_DESC_BADMINTON + BUDGET_DESC_BASKETBALL,
            new CreateCcaCommand(expectedCca));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCcaCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_CCA_NAME_BASKETBALL + BUDGET_DESC_BASKETBALL, expectedMessage);

        // missing budget prefix
        assertParseFailure(parser, NAME_DESC_BASKETBALL + VALID_BUDGET_BASKETBALL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + BUDGET_DESC_BASKETBALL, CcaName.MESSAGE_NAME_CONSTRAINTS);

        // invalid budget
        assertParseFailure(parser, NAME_DESC_BASKETBALL + INVALID_BUDGET_DESC, Budget.MESSAGE_BUDGET_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCcaCommand.MESSAGE_USAGE));
    }
}
