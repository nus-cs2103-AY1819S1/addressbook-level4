package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISITOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISITOR_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.VisitorinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.visitor.Visitor;



public class VisitorInCommandParserTest {
    private VisitorinCommandParser parser;
    private Nric patientNric;
    private Visitor visitor;

    @Before
    public void setUp() throws IllegalValueException {
        parser = new VisitorinCommandParser();
        patientNric = new Nric(VALID_NRIC_AMY);
        visitor = new Visitor(VALID_VISITOR);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        assertParseSuccess(parser, NRIC_DESC_AMY + VALID_VISITOR_DESC,
                new VisitorinCommand(patientNric, visitor));
    }
}
