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
import static seedu.address.logic.commands.CommandModuleTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandModuleTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandModuleTestUtil.SEMESTER_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.SEMESTER_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.TAG_DESC_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.TAG_DESC_CALCULUS;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_CALCULUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.ST2131;

import org.junit.Test;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleBuilder;


public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS2100).withTags(VALID_TAG_CALCULUS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                new AddModuleCommand(expectedModule));

        // multiple moduleCode - last moduleCode accepted
        assertParseSuccess(parser, MODULECODE_DESC_ST2131 + MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                new AddModuleCommand(expectedModule));

        // multiple moduleTitle - last moduleTitle accepted
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_ST2131 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                new AddModuleCommand(expectedModule));

        // multiple academicYear - last academicYear accepted
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_ST2131 + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100
                        + TAG_DESC_CALCULUS, new AddModuleCommand(expectedModule));

        // multiple semester - last semester accepted
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_ST2131 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                new AddModuleCommand(expectedModule));

        // multiple tags - all accepted
        Module expectedModuleMultipleTags = new ModuleBuilder(CS2100).withTags(VALID_TAG_BINARY, VALID_TAG_CALCULUS)
                .build();
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_BINARY + TAG_DESC_CALCULUS,
                new AddModuleCommand(expectedModuleMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Module expectedModule = new ModuleBuilder(ST2131).withTags().build();
        assertParseSuccess(parser, MODULECODE_DESC_ST2131 + MODULETITLE_DESC_ST2131
                        + ACADEMICYEAR_DESC_ST2131 + SEMESTER_DESC_ST2131, new AddModuleCommand(expectedModule));

        // missing moduleTitle prefix
        expectedModule = new ModuleBuilder(CS2100).withoutModuleTitle().build();
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100
                + TAG_DESC_BINARY, new AddModuleCommand(expectedModule));

        // missing academicYear prefix
        expectedModule = new ModuleBuilder(CS2100).withoutAcademicYear().build();
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100 + SEMESTER_DESC_CS2100
                + TAG_DESC_BINARY, new AddModuleCommand(expectedModule));

        // missing semester prefix
        expectedModule = new ModuleBuilder(CS2100).withoutSemester().build();
        assertParseSuccess(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + TAG_DESC_BINARY, new AddModuleCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // missing moduleCode prefix
        assertParseFailure(parser, VALID_MODULECODE_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid moduleCode
        assertParseFailure(parser, INVALID_MODULECODE_DESC + MODULETITLE_DESC_CS2100
                + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);

        // invalid moduleTitle
        assertParseFailure(parser, MODULECODE_DESC_CS2100 + INVALID_MODULETITLE_DESC
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);

        // invalid academicYear
        assertParseFailure(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + INVALID_ACADEMICYEAR_DESC + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                AcademicYear.MESSAGE_ACADEMICYEAR_CONSTRAINTS);

        // invalid semester
        assertParseFailure(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + INVALID_SEMESTER_FIVE + TAG_DESC_CALCULUS,
                Semester.MESSAGE_SEMESTER_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + INVALID_TAG_DESC + TAG_DESC_CALCULUS,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MODULECODE_DESC + INVALID_MODULETITLE_DESC
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULECODE_DESC_CS2100 + MODULETITLE_DESC_CS2100
                        + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_CALCULUS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
    }
}
