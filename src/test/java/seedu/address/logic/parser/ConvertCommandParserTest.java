package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;

public class ConvertCommandParserTest {

    private ConvertCommandParser parser = new ConvertCommandParser();

    @Test
    public void parseConvertArgument() throws ParseException {
        Transformation transformationBlur = new Transformation("blur", "0x8");
        assertParseSuccess(parser, "convert " + transformationBlur.toString(),
                new ConvertCommand(transformationBlur));
        Transformation transformationRotate = new Transformation("rotate", "90");
        assertParseSuccess(parser, "convert " + transformationRotate.toString(),
                new ConvertCommand(transformationRotate));
        Transformation transformationResize = new Transformation("resize", "50%");
        assertParseSuccess(parser, "convert " + transformationResize.toString(),
                new ConvertCommand(transformationResize));
        Transformation transformationContrast = new Transformation("contrast");
        assertParseSuccess(parser, "convert " + transformationContrast.toString(),
                new ConvertCommand(transformationContrast));
        Transformation transformationSigmoidalContrast = new Transformation("sigmoidal-contrast", "10x10%");
        assertParseSuccess(parser, "convert " + transformationSigmoidalContrast.toString(),
                new ConvertCommand(transformationSigmoidalContrast));
    }

    @Test
    public void parseConvertArgumentFail() throws ParseException {
        Transformation transformationBlur = new Transformation("blur", "0x8");
        assertParseSuccess(parser, "convert " + transformationBlur.toString(),
                new ConvertCommand(transformationBlur));
    }

}
