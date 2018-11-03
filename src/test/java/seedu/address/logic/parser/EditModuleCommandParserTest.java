package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandModuleTestUtil.ACADEMICYEAR_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.ACADEMICYEAR_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_ACADEMICYEAR_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_MODULECODE_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_MODULETITLE_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_SEMESTER_FIVE;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULECODE_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULECODE_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULETITLE_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULETITLE_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.SEMESTER_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.SEMESTER_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.TAG_DESC_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.TAG_DESC_CALCULUS;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_SEMESTER_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_SEMESTER_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_CALCULUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MODULE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleDescriptorBuilder;

public class EditModuleCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE);

    private EditModuleCommandParser parser = new EditModuleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_MODULECODE_CS2100, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditModuleCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + MODULECODE_DESC_CS2100, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + MODULECODE_DESC_CS2100, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_MODULECODE_DESC, ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);
        // invalid name
        assertParseFailure(parser, "1" + INVALID_MODULETITLE_DESC, ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);
        // invalid moduleTitle
        assertParseFailure(parser, "1" + INVALID_ACADEMICYEAR_DESC, AcademicYear.MESSAGE_ACADEMICYEAR_CONSTRAINTS);
        // invalid academicYear
        assertParseFailure(parser, "1" + INVALID_SEMESTER_FIVE, Semester.MESSAGE_SEMESTER_CONSTRAINTS);
        // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);
        // invalid tag

        // invalid moduleTitle followed by valid academicYear
        assertParseFailure(parser, "1" + INVALID_MODULETITLE_DESC + ACADEMICYEAR_DESC_CS2100,
                ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);

        // valid moduleTitle followed by invalid moduleTitle. The test case for invalid moduleTitle
        // followed by valid moduleTitle is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + MODULETITLE_DESC_CS2100 + INVALID_MODULETITLE_DESC,
                ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Module} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_BINARY + TAG_DESC_CALCULUS + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_BINARY + TAG_EMPTY + TAG_DESC_CALCULUS, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_BINARY + TAG_DESC_CALCULUS, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_MODULECODE_DESC + INVALID_MODULETITLE_DESC
                        + INVALID_SEMESTER_FIVE + VALID_ACADEMICYEAR_ST2131,
               ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MODULE;
        String userInput = targetIndex.getOneBased() + MODULECODE_DESC_ST2131 + TAG_DESC_CALCULUS
                + MODULETITLE_DESC_ST2131 + SEMESTER_DESC_ST2131 + ACADEMICYEAR_DESC_ST2131;

        ModuleDescriptor descriptor = new ModuleDescriptorBuilder().withModuleCode(VALID_MODULECODE_ST2131)
                .withAcademicYear(VALID_ACADEMICYEAR_ST2131).withModuleTitle(VALID_MODULETITLE_ST2131)
                .withSemester(VALID_SEMESTER_ST2131).withTags(VALID_TAG_CALCULUS).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + MODULETITLE_DESC_CS2100 + SEMESTER_DESC_CS2100;

        ModuleDescriptor descriptor = new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_CS2100)
                .withSemester(VALID_SEMESTER_CS2100).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // moduleCode
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + MODULECODE_DESC_CS2100;
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULECODE_CS2100).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // moduleTitle
        userInput = targetIndex.getOneBased() + MODULETITLE_DESC_CS2100;
        descriptor = new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_CS2100).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // academicYear
        userInput = targetIndex.getOneBased() + ACADEMICYEAR_DESC_CS2100;
        descriptor = new ModuleDescriptorBuilder().withAcademicYear(VALID_ACADEMICYEAR_CS2100).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // semester
        userInput = targetIndex.getOneBased() + SEMESTER_DESC_CS2100;
        descriptor = new ModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2100).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_CALCULUS;
        descriptor = new ModuleDescriptorBuilder().withTags(VALID_TAG_CALCULUS).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + MODULETITLE_DESC_CS2100 + ACADEMICYEAR_DESC_CS2100
                + MODULECODE_DESC_CS2100 + TAG_DESC_CALCULUS + MODULETITLE_DESC_CS2100 + ACADEMICYEAR_DESC_CS2100
                + MODULECODE_DESC_ST2131 + TAG_DESC_CALCULUS + MODULETITLE_DESC_ST2131 + ACADEMICYEAR_DESC_ST2131
                + SEMESTER_DESC_ST2131 + TAG_DESC_BINARY;

        ModuleDescriptor descriptor = new ModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULECODE_ST2131).withModuleTitle(VALID_MODULETITLE_ST2131)
                .withAcademicYear(VALID_ACADEMICYEAR_ST2131).withSemester(VALID_SEMESTER_ST2131)
                .withTags(VALID_TAG_CALCULUS, VALID_TAG_BINARY)
                .build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + INVALID_MODULECODE_DESC + MODULECODE_DESC_CS2100;
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULECODE_CS2100).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + ACADEMICYEAR_DESC_CS2100 + INVALID_MODULECODE_DESC
                + SEMESTER_DESC_CS2100 + MODULECODE_DESC_CS2100;
        descriptor = new ModuleDescriptorBuilder().withAcademicYear(VALID_ACADEMICYEAR_CS2100)
                .withSemester(VALID_SEMESTER_CS2100)
                .withModuleCode(VALID_MODULECODE_CS2100).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        ModuleDescriptor descriptor = new ModuleDescriptorBuilder().withTags().build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

