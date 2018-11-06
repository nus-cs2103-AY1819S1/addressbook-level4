package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.CreateConvertCommand;
import seedu.address.model.transformation.Transformation;

public class CreateConvertCommandParserTest {

    private CreateConvertCommandParser parser = new CreateConvertCommandParser();
    private String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateConvertCommand.MESSAGE_USAGE);

    @Test
    public void parseCommandSuccessfully() {
        String input = "create blurSample blur|0x8";
        List<Transformation> list = new ArrayList<>();
        Transformation transformation = new Transformation("blur", "0x8");
        list.add(transformation);
        assertParseSuccess(parser, input, new CreateConvertCommand("blurSample", list));
        //test case with multiple operations
        String input2 = "create blurR blur|0x8 rotate|90";
        Transformation transformation2 = new Transformation("rotate", "90");
        list.add(transformation2);
        assertParseSuccess(parser, input2, new CreateConvertCommand("blurR", list));
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
