package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CreateApplyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;

public class CreateApplyCommandParserTest {

    private CreateApplyCommandParser parser = new CreateApplyCommandParser();
    private String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateApplyCommand.MESSAGE_USAGE);

    @Test
    public void parseCommandSuccessfully() throws ParseException {
        String input = "create blurSample blur|0x8";
        List<Transformation> list = new ArrayList<>();
        Transformation transformation = new Transformation("blur", "0x8");
        list.add(transformation);
        assertParseSuccess(parser, input, new CreateApplyCommand("blurSample", list));
        //test case with multiple operations
        String input2 = "create blurR blur|0x8 rotate|90";
        Transformation transformation2 = new Transformation("rotate", "90");
        list.add(transformation2);
        assertParseSuccess(parser, input2, new CreateApplyCommand("blurR", list));
        Command command = new PiconsoParser().parseCommand(input2);
        assertTrue(command.equals(new CreateApplyCommand("blurR",list)));
    }

    @Test
    public void parseCommandUnsuccessfully() {
        String input = "create blurSample";
        assertParseFailure(parser, input, errorMessage);
        String input2 = "create blur(0x8)";
        assertParseFailure(parser, input2, errorMessage);
        String input3 = "create blur|0x8 rotate|90";
        assertParseFailure(parser, input3, errorMessage);
    }
}
