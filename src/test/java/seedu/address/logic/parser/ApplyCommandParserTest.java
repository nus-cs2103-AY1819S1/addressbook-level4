package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ApplyCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;

public class ApplyCommandParserTest {

    private ApplyCommandParser parser = new ApplyCommandParser();

    @Test
    public void parseConvertArgument() throws ParseException {
        Transformation transformationBlur = new Transformation("blur", "0x8");
        assertParseSuccess(parser, transformationBlur.toString(),
                new ApplyCommand(transformationBlur));
        Transformation transformationRotate = new Transformation("rotate", "90");
        assertParseSuccess(parser, transformationRotate.toString(),
                new ApplyCommand(transformationRotate));
        Transformation transformationResize = new Transformation("resize", "50%");
        assertParseSuccess(parser, transformationResize.toString(),
                new ApplyCommand(transformationResize));
        Transformation transformationContrast = new Transformation("contrast");
        assertParseSuccess(parser, transformationContrast.toString(),
                new ApplyCommand(transformationContrast));
        Transformation transformationSigmoidalContrast = new Transformation("sigmoidal-contrast", "10x10%");
        assertParseSuccess(parser, transformationSigmoidalContrast.toString(),
                new ApplyCommand(transformationSigmoidalContrast));
        Command command = new PiconsoParser().parseCommand("apply blur 0x8");
        assertTrue(command.equals(new ApplyCommand(transformationBlur)));
    }

    @Test
    public void parseRawConvertArgument() {
        Transformation transformation = new Transformation("+noise gaussian");
        assertParseSuccess(parser, "raw " + transformation.toString(),
                new ApplyCommand(transformation.getOperation().split(" ")));
    }

    @Test
    public void parseConvertArgumentFail() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "raw", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplyCommand.MESSAGE_USAGE_RAW));
    }

}
