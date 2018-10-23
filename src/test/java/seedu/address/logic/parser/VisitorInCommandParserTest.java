package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISITOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISITOR_DESC;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.VisitorinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.visitor.Visitor;



public class VisitorInCommandParserTest {
    private VisitorinCommandParser parser;
    private Name patientName;
    private Visitor visitor;

    @Before
    public void setUp() throws IllegalValueException {
        parser = new VisitorinCommandParser();
        patientName = new Name(VALID_NAME_AMY);
        visitor = new Visitor(VALID_VISITOR);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        assertParseSuccess(parser, NAME_DESC_AMY + VALID_VISITOR_DESC,
                new VisitorinCommand(patientName, visitor));
    }
}
