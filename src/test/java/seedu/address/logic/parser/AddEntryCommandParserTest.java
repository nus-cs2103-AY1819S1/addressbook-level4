package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.CAT_DESC_WORK;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBTITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EntryBuilder;

public class AddEntryCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddEntryCommandParser parser = new AddEntryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ResumeEntry expectedEntry = new EntryBuilder().build();


        // whitespace only preamble with majorEntry
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CAT_DESC_WORK + TITLE_DESC_NUS
                + SUBTITLE_DESC + DURATION_DESC, new AddEntryCommand(expectedEntry));
        // minor entry, i.e. excluding entryInfo
        ResumeEntry expected = new ResumeEntry(new Category("EDUCATION"), new EntryInfo(), new HashSet<Tag>());
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CAT_DESC_WORK,
                new AddEntryCommand(expected));

        // multiple tags - all accepted
        ResumeEntry expectedEntryMultipleTags = new EntryBuilder().withTags(VALID_TAG_JAVA, VALID_TAG_SE)
                .build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CAT_DESC_WORK + TITLE_DESC_NUS + SUBTITLE_DESC + DURATION_DESC
                + TAG_DESC_JAVA + TAG_DESC_SE, new AddEntryCommand(expectedEntryMultipleTags));


    }

    @Test
    public void parse_invalidArgument_exception() throws Exception {
        thrown.expect(ParseException.class);
        parser.parse(CAT_DESC_WORK + TITLE_DESC_NUS);
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, PREAMBLE_WHITESPACE + CAT_DESC_WORK + INVALID_TITLE_DESC
                + SUBTITLE_DESC + DURATION_DESC, EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS);
    }
}
