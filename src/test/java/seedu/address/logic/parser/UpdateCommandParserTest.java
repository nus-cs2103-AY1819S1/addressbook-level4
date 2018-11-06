package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.HEAD_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.HEAD_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUDGET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEAD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_BASKETBALL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VICE_HEAD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_NUM_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_NUM_DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSACTION_NUM_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VICE_HEAD_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VICE_HEAD_DESC_BASKETBALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.testutil.EditCcaDescriptorBuilder;

//@@author ericyjw
public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no cca tag specified
        assertParseFailure(parser, " " + VALID_CCA_NAME_BASKETBALL, UpdateCommand.MESSAGE_NO_SPECIFIC_CCA);

        // no field specified
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL, UpdateCommand.MESSAGE_NOT_UPDATED);

        // no cca tag and no field specified
        assertParseFailure(parser, "", UpdateCommand.MESSAGE_NO_SPECIFIC_CCA);

        // no transaction field but with transaction tag
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + PREFIX_TRANSACTION + "1",
            UpdateCommand.MESSAGE_INVALID_TRANSACTION_UPDATE);
    }

    @Test
    public void parse_invalidFields_failure() {
        // invalid Cca
        assertParseFailure(parser, " " + PREFIX_TAG + INVALID_NAME_BASKETBALL_DESC, CcaName.MESSAGE_NAME_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", UpdateCommand.MESSAGE_NO_SPECIFIC_CCA);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", UpdateCommand.MESSAGE_NO_SPECIFIC_CCA);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_NAME_BASKETBALL_DESC,
            CcaName.MESSAGE_NAME_CONSTRAINTS); // invalid cca name
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_HEAD_DESC,
            Name.MESSAGE_NAME_CONSTRAINTS); // invalid head name
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_VICE_HEAD_DESC,
            Name.MESSAGE_NAME_CONSTRAINTS); // invalid vice head name
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_BUDGET_DESC,
            Budget.MESSAGE_BUDGET_CONSTRAINTS); // invalid vice head name
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_DATE_DESC,
            Date.MESSAGE_DATE_CONSTRAINTS); // invalid date format
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_AMOUNT_DESC,
            Amount.MESSAGE_AMOUNT_CONSTRAINTS); // invalid amount

        // invalid name followed by valid head name
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_NAME_BASKETBALL_DESC + HEAD_DESC_BASKETBALL,
            CcaName.MESSAGE_NAME_CONSTRAINTS);

        // invalid name followed by valid vice head name
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_NAME_BASKETBALL_DESC + VICE_HEAD_DESC_BASKETBALL,
            CcaName.MESSAGE_NAME_CONSTRAINTS);

        // invalid name followed by valid budget
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_NAME_BASKETBALL_DESC + BUDGET_DESC_BASKETBALL,
            CcaName.MESSAGE_NAME_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        // invalid name followed by valid budget
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + INVALID_NAME_BASKETBALL_DESC + INVALID_HEAD_DESC + INVALID_VICE_HEAD_DESC + INVALID_BUDGET_DESC + NAME_DESC_BADMINTON,
            Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + NAME_DESC_BASKETBALL + HEAD_DESC_BASKETBALL + VICE_HEAD_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL
                + TRANSACTION_NUM_DESC_BASKETBALL + DATE_DESC_BASKETBALL + AMOUNT_DESC_BASKETBALL + REMARKS_DESC_BASKETBALL;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .withHead(VALID_NAME_BOB)
            .withViceHead(VALID_NAME_CARL)
            .withBudget(VALID_BUDGET_BASKETBALL)
            .withEntryNum(Integer.valueOf(VALID_TRANSACTION_NUM_BASKETBALL))
            .withDate(VALID_DATE_BASKETBALL)
            .withAmount(VALID_AMOUNT_BASKETBALL)
            .withRemarks(VALID_REMARKS_BASKETBALL)
            .build();

        UpdateCommand expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + NAME_DESC_BASKETBALL + VICE_HEAD_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL
                + TRANSACTION_NUM_DESC_BASKETBALL + AMOUNT_DESC_BASKETBALL + REMARKS_DESC_BASKETBALL;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .withViceHead(VALID_NAME_CARL)
            .withBudget(VALID_BUDGET_BASKETBALL)
            .withEntryNum(Integer.valueOf(VALID_TRANSACTION_NUM_BASKETBALL))
            .withAmount(VALID_AMOUNT_BASKETBALL)
            .withRemarks(VALID_REMARKS_BASKETBALL)
            .build();

        UpdateCommand expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // new cca name
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + NAME_DESC_BASKETBALL;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .build();

        UpdateCommand expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // head
        userInput =
            targetCca + HEAD_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withHead(VALID_NAME_BOB)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // vice-head
        userInput =
            targetCca + VICE_HEAD_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withViceHead(VALID_NAME_CARL)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // budget
        userInput =
            targetCca + BUDGET_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withBudget(VALID_BUDGET_BASKETBALL)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput =
            targetCca + TRANSACTION_NUM_DESC_BASKETBALL + DATE_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withEntryNum(Integer.valueOf(VALID_TRANSACTION_NUM_BASKETBALL))
            .withDate(VALID_DATE_BASKETBALL)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput =
            targetCca + TRANSACTION_NUM_DESC_BASKETBALL + AMOUNT_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withEntryNum(Integer.valueOf(VALID_TRANSACTION_NUM_BASKETBALL))
            .withAmount(VALID_AMOUNT_BASKETBALL)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput =
            targetCca + TRANSACTION_NUM_DESC_BASKETBALL + REMARKS_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withEntryNum(Integer.valueOf(VALID_TRANSACTION_NUM_BASKETBALL))
            .withRemarks(VALID_REMARKS_BASKETBALL)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleCcaRepeatedFields_acceptsLast() {
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + NAME_DESC_BASKETBALL + HEAD_DESC_BASKETBALL + VICE_HEAD_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL
                + NAME_DESC_BADMINTON + HEAD_DESC_BADMINTON + VICE_HEAD_DESC_BADMINTON + BUDGET_DESC_BADMINTON;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BADMINTON)
            .withHead(VALID_NAME_AMY)
            .withViceHead(VALID_NAME_DAVID)
            .withBudget(VALID_BUDGET_BADMINTON)
            .build();

        UpdateCommand expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTransactionRepeatedFields_failure() {
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + TRANSACTION_NUM_DESC_BASKETBALL + DATE_DESC_BASKETBALL + AMOUNT_DESC_BASKETBALL + REMARKS_DESC_BASKETBALL
                + TRANSACTION_NUM_DESC_BADMINTON + DATE_DESC_BADMINTON + AMOUNT_DESC_BADMINTON + REMARKS_DESC_BADMINTON;

        assertParseFailure(parser,
            userInput, UpdateCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + INVALID_NAME_BASKETBALL_DESC + NAME_DESC_BASKETBALL;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .build();

        UpdateCommand expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // valid values specified follows
        userInput =
            targetCca + INVALID_NAME_BASKETBALL_DESC + NAME_DESC_BASKETBALL + HEAD_DESC_BASKETBALL + BUDGET_DESC_BASKETBALL;

        descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .withHead(VALID_NAME_BOB)
            .withBudget(VALID_BUDGET_BASKETBALL)
            .build();

        expectedCommand = new UpdateCommand(new CcaName(VALID_CCA_NAME_BADMINTON), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }
    @Test
    public void parse_invalidTransactionValueFollowedByValidValue_failure() {
        // no other valid transaction values specified
        String targetCca = " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON;
        String userInput =
            targetCca + TRANSACTION_NUM_DESC_BASKETBALL + INVALID_DATE_DESC + DATE_DESC_BASKETBALL;

        assertParseFailure(parser, userInput, UpdateCommand.MESSAGE_USAGE);


    }
}
