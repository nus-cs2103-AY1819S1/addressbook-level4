package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.AddMedicalHistoryCommand;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Condition;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class AddMedicalHistoryCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicalHistoryCommand.MESSAGE_USAGE);

    private ArrayList<Allergy> emptyAllergy = new ArrayList<>();
    private ArrayList<Condition> emptyCondition = new ArrayList<>();
    private AddMedicalHistoryCommandParser parser = new AddMedicalHistoryCommandParser();


    @Test
    public void parse_fieldSpecified_success() {

        //input is not null for phone, allergy and condition
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));

        String userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " " + PREFIX_PHONE + VALID_PHONE_BOB + " "
                + PREFIX_ALLERGY + VALID_ALLERGY_1 + "," + VALID_ALLERGY_2 + " " + PREFIX_CONDITION
                + VALID_CONDITION_1 + "," + VALID_CONDITION_2;

        AddMedicalHistoryCommand expectedCommand = new AddMedicalHistoryCommand(
                new Name(VALID_NAME_ALICE), new Phone(VALID_PHONE_BOB), allergies, conditions);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input is null for phone
        userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " " + PREFIX_ALLERGY + VALID_ALLERGY_1 + ","
                + VALID_ALLERGY_2 + " " + PREFIX_CONDITION + VALID_CONDITION_1 + "," + VALID_CONDITION_2;
        expectedCommand = new AddMedicalHistoryCommand(
                new Name(VALID_NAME_ALICE), null, allergies, conditions);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input for allergy is left blank
        userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " "
                + PREFIX_CONDITION + VALID_CONDITION_1 + "," + VALID_CONDITION_2;
        expectedCommand = new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, emptyAllergy, conditions);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input for condition is left blank
        userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " "
                + PREFIX_ALLERGY + VALID_ALLERGY_1 + "," + VALID_ALLERGY_2;
        expectedCommand = new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, emptyCondition);
        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_missingParts_failure() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));

        //no name specified
        assertParseFailure(parser, PREFIX_ALLERGY + VALID_ALLERGY_1 + "," + VALID_ALLERGY_2 + " "
                        + PREFIX_CONDITION + VALID_CONDITION_1 + "," + VALID_CONDITION_2,
                MESSAGE_INVALID_FORMAT);

        // no name and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "a/Alice Pauline i/string u/string", MESSAGE_INVALID_FORMAT);
    }
}
