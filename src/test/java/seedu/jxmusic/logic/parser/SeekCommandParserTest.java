//package seedu.jxmusic.logic.parser;
//
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseSuccess;
//
//import org.junit.Test;
//
//import javafx.util.Duration;
//import seedu.jxmusic.logic.commands.SeekCommand;
//
//public class SeekCommandParserTest {
//    private SeekCommandParser parser = new SeekCommandParser();
//
//    @Test
//    public void parse_emptyArg_throwsParseException() {
//        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//                SeekCommand.MESSAGE_USAGE));
//
//    }
//
//    @Test
//    public void parse_invalidArg_throwsParseException() {
//        assertParseFailure(parser, "t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//                SeekCommand.MESSAGE_USAGE));
//
//    }
//
//    @Test
//    public void parse_validArgs_returnTrue() {
//        assertParseSuccess(parser, " d/2 ", new SeekCommand(new Duration(2000)));
//    }
//    @Test
//    public void parse_invalidValue_throwsParseException() {
//    invalid number of parameters
//    invalid format of parameters
//}
//
