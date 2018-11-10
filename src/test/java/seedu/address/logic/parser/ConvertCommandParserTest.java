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
        assertParseSuccess(parser, transformationBlur.toString(),
                new ConvertCommand(transformationBlur));
        Transformation transformationRotate = new Transformation("rotate", "90");
        assertParseSuccess(parser, transformationRotate.toString(),
                new ConvertCommand(transformationRotate));
        Transformation transformationResize = new Transformation("resize", "50%");
        assertParseSuccess(parser, transformationResize.toString(),
                new ConvertCommand(transformationResize));
        Transformation transformationContrast = new Transformation("contrast");
        assertParseSuccess(parser, transformationContrast.toString(),
                new ConvertCommand(transformationContrast));
        Transformation transformationSigmoidalContrast = new Transformation("sigmoidal-contrast", "10x10%");
        assertParseSuccess(parser, transformationSigmoidalContrast.toString(),
                new ConvertCommand(transformationSigmoidalContrast));
    }

    @Test
    public void parseRawConvertArgument() throws ParseException {
        Transformation transformation = new Transformation("+noise gaussian");
        assertParseSuccess(parser, "raw " + transformation.toString(),
                new ConvertCommand(transformation.getOperation().split(" ")));
    }

    @Test
    public void parseConvertArgumentFail() throws ParseException {
        Transformation transformationBlur = new Transformation("blur", "0x8");
        assertParseSuccess(parser, transformationBlur.toString(),
                new ConvertCommand(transformationBlur));
    }

}
