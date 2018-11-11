package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_NAME_BASKETBALL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BADMINTON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEntries.getEntryCompetition1;

import org.junit.Test;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;
import seedu.address.testutil.EntryBuilder;

//@@author ericyjw
public class AddTransactionCommandParserTest {

    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CcaName targetCca = new CcaName(VALID_CCA_NAME_BADMINTON);
        Entry expectedEntry = new EntryBuilder(getEntryCompetition1()).build();

        assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee",
            new AddTransactionCommand(targetCca, expectedEntry.getDate(), expectedEntry.getAmount(),
                expectedEntry.getRemarks()));

        assertParseSuccess(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee",
            new AddTransactionCommand(targetCca, expectedEntry.getDate(), expectedEntry.getAmount(),
                expectedEntry.getRemarks()));
    }

    @Test
    public void parse_missingParts_failure() {
        // no cca name specified
        assertParseFailure(parser, " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee",
            AddTransactionCommand.MESSAGE_NO_SPECIFIC_CCA + "\n" + AddTransactionCommand.MESSAGE_USAGE);


        // no date specified
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS
                + "Competition Fee",
            AddTransactionCommand.MESSAGE_NOT_UPDATED + "\n" + AddTransactionCommand.MESSAGE_USAGE);

        // no amount specified
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
            + "01.02.2018 " + PREFIX_REMARKS + "Competition Fee", AddTransactionCommand.MESSAGE_NOT_UPDATED
            + "\n" + AddTransactionCommand.MESSAGE_USAGE);


        // no remarks
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
            + "01.02.2018 " + PREFIX_AMOUNT + "-100 ", AddTransactionCommand.MESSAGE_NOT_UPDATED
            + "\n" + AddTransactionCommand.MESSAGE_USAGE);

        // no remarks
        assertParseFailure(parser, "",
            AddTransactionCommand.MESSAGE_NO_SPECIFIC_CCA + "\n" + AddTransactionCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidFields_failure() {
        // invalid cca name
        assertParseFailure(parser, INVALID_CCA_NAME_BASKETBALL_DESC + " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee",
            CcaName.MESSAGE_NAME_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
            AddTransactionCommand.MESSAGE_NO_SPECIFIC_CCA + "\n" + AddTransactionCommand.MESSAGE_USAGE);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string",
            AddTransactionCommand.MESSAGE_NO_SPECIFIC_CCA + "\n" + AddTransactionCommand.MESSAGE_USAGE);

        // invalid date
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
                + "01/02/2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee",
            Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-$100 " + PREFIX_REMARKS + "Competition Fee",
            Amount.MESSAGE_AMOUNT_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee - $",
            Remarks.MESSAGE_REMARKS_CONSTRAINTS);
    }


    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {

        // invalid date followed by valid date
        assertParseFailure(parser,
            " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE + "01/02/2018 " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee",
            AddTransactionCommand.MESSAGE_NOT_UPDATED + "\n" + AddTransactionCommand.MESSAGE_USAGE);

        // invalid amount followed by valid amount
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
            + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_AMOUNT + "-$100 " + PREFIX_REMARKS + "Competition"
            + " Fee", AddTransactionCommand.MESSAGE_NOT_UPDATED + "\n" + AddTransactionCommand.MESSAGE_USAGE);

        // invalid amount followed by valid amount
        assertParseFailure(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BADMINTON + " " + PREFIX_DATE
                + "01.02.2018 " + PREFIX_AMOUNT + "-100 " + PREFIX_REMARKS + "Competition Fee " + PREFIX_REMARKS
                + "Competition Fee - $",
            AddTransactionCommand.MESSAGE_NOT_UPDATED + "\n" + AddTransactionCommand.MESSAGE_USAGE);
    }
}
