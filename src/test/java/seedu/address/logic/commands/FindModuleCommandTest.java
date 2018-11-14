package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MODULES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_FIVE;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_FOUR;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_ONE;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_SIX;
import static seedu.address.testutil.TypicalModules.getTypicalModulesAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.AcademicYearContainsKeywordsPredicate;
import seedu.address.model.module.ModuleCodeContainsKeywordsPredicate;
import seedu.address.model.module.ModuleTitleContainsKeywordsPredicate;
import seedu.address.model.module.SemesterContainsKeywordsPredicate;

public class FindModuleCommandTest {
    private Model model = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        //test ModuleCodeContainsKeywordsPredicate
        ModuleCodeContainsKeywordsPredicate firstModuleCodePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("cs2103"));
        ModuleCodeContainsKeywordsPredicate secondModuleCodePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("cs2102"));

        FindModuleCommand findFirstModuleCodeCommand = new FindModuleCommand(firstModuleCodePredicate);
        FindModuleCommand findSecondModuleCodeCommand = new FindModuleCommand(secondModuleCodePredicate);

        // same object -> returns true
        assertTrue(findFirstModuleCodeCommand.equals(findFirstModuleCodeCommand));

        // same values -> returns true
        FindModuleCommand findFirstModuleCodeCommandCopy = new FindModuleCommand(firstModuleCodePredicate);
        assertTrue(findFirstModuleCodeCommand.equals(findFirstModuleCodeCommandCopy));

        // different types -> returns false
        assertFalse(findFirstModuleCodeCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstModuleCodeCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstModuleCodeCommand.equals(findSecondModuleCodeCommand));

        //test ModuleTitleContainsKeywordsPredicate
        ModuleTitleContainsKeywordsPredicate firstModuleTitlePredicate =
                new ModuleTitleContainsKeywordsPredicate(Collections.singletonList("software engineering"));
        ModuleTitleContainsKeywordsPredicate secondPhonePredicate =
                new ModuleTitleContainsKeywordsPredicate(Collections.singletonList("database"));

        FindModuleCommand findFirstModuleTitleCommand = new FindModuleCommand(firstModuleTitlePredicate);
        FindModuleCommand findSecondModuleTitleCommand = new FindModuleCommand(secondPhonePredicate);

        // same object -> returns true
        assertTrue(findFirstModuleTitleCommand.equals(findFirstModuleTitleCommand));

        // same values -> returns true
        FindModuleCommand findFirstModuleTitleCommandCopy = new FindModuleCommand(firstModuleTitlePredicate);
        assertTrue(findFirstModuleTitleCommand.equals(findFirstModuleTitleCommandCopy));

        // different types -> returns false
        assertFalse(findFirstModuleTitleCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstModuleTitleCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstModuleTitleCommand.equals(findSecondModuleTitleCommand));

        //test ModuleLocationContainsKeywordsPredicate
        AcademicYearContainsKeywordsPredicate firstAcademicYearPredicate =
                new AcademicYearContainsKeywordsPredicate(Collections.singletonList("1819"));
        AcademicYearContainsKeywordsPredicate secondAcademicYearPredicate =
                new AcademicYearContainsKeywordsPredicate(Collections.singletonList("1718"));

        FindModuleCommand findFirstAcademicYearCommand = new FindModuleCommand(firstAcademicYearPredicate);
        FindModuleCommand findSecondAcademicYearCommand = new FindModuleCommand(secondAcademicYearPredicate);

        // same object -> returns true
        assertTrue(findFirstAcademicYearCommand.equals(findFirstAcademicYearCommand));

        // same values -> returns true
        FindModuleCommand findFirstAcademicYearCommandCopy = new FindModuleCommand(firstAcademicYearPredicate);
        assertTrue(findFirstAcademicYearCommand.equals(findFirstAcademicYearCommandCopy));

        // different types -> returns false
        assertFalse(findFirstAcademicYearCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstAcademicYearCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstAcademicYearCommand.equals(findSecondAcademicYearCommand));

        //test SemesterContainsKeywordsPredicate
        SemesterContainsKeywordsPredicate firstSemesterPredicate =
                new SemesterContainsKeywordsPredicate(Collections.singletonList("1"));
        SemesterContainsKeywordsPredicate secondSemesterPredicate =
                new SemesterContainsKeywordsPredicate(Collections.singletonList("2"));

        FindModuleCommand findFirstAddressCommand = new FindModuleCommand(firstSemesterPredicate);
        FindModuleCommand findSecondAddressCommand = new FindModuleCommand(secondSemesterPredicate);

        // same object -> returns true
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommand));

        // same values -> returns true
        FindModuleCommand findFirstSemesterCommandCopy = new FindModuleCommand(firstSemesterPredicate);
        assertTrue(findFirstAddressCommand.equals(findFirstSemesterCommandCopy));

        // different types -> returns false
        assertFalse(findFirstAddressCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstAddressCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstAddressCommand.equals(findSecondAddressCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 0);

        //test findModule using moduleCode
        ModuleCodeContainsKeywordsPredicate moduleCodePredicate = prepareModuleCodePredicate(" ");
        FindModuleCommand moduleCodeCommand = new FindModuleCommand(moduleCodePredicate);
        expectedModel.updateFilteredModuleList(moduleCodePredicate);
        assertCommandSuccess(moduleCodeCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());

        //test findPerson using ModuleTitle
        ModuleTitleContainsKeywordsPredicate moduleTitlePredicate = prepareModuleTitlePredicate(" ");
        FindModuleCommand moduleTitleCommand = new FindModuleCommand(moduleTitlePredicate);
        expectedModel.updateFilteredModuleList(moduleTitlePredicate);
        assertCommandSuccess(moduleTitleCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());

        //test findPerson using AcademicYear
        AcademicYearContainsKeywordsPredicate academicYearPredicate = prepareAcademicYearPredicate(" ");
        FindModuleCommand academicYearCommand = new FindModuleCommand(academicYearPredicate);
        expectedModel.updateFilteredModuleList(academicYearPredicate);
        assertCommandSuccess(academicYearCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());

        //test findPerson using Semester
        SemesterContainsKeywordsPredicate semesterPredicate = prepareSemesterPredicate(" ");
        FindModuleCommand semesterCommand = new FindModuleCommand(semesterPredicate);
        expectedModel.updateFilteredModuleList(semesterPredicate);
        assertCommandSuccess(semesterCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());
    }

    @Test
    public void execute_multipleKeywords_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 3);

        //test findPerson using ModuleCode
        ModuleCodeContainsKeywordsPredicate moduleCodePredicate =
                prepareModuleCodePredicate("CS1101S CS3233 GER1000");
        FindModuleCommand moduleCodeCommand = new FindModuleCommand(moduleCodePredicate);
        expectedModel.updateFilteredModuleList(moduleCodePredicate);
        assertCommandSuccess(moduleCodeCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_MODULE_ONE, TYPICAL_MODULE_FOUR, TYPICAL_MODULE_SIX),
                model.getFilteredModuleList());

        //test findPerson using ModuleTitle
        ModuleTitleContainsKeywordsPredicate moduleTitlePredicate =
                prepareModuleTitlePredicate("methodology competitive quantitative");
        FindModuleCommand moduleTitleCommand = new FindModuleCommand(moduleTitlePredicate);
        expectedModel.updateFilteredModuleList(moduleTitlePredicate);
        assertCommandSuccess(moduleTitleCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_MODULE_ONE, TYPICAL_MODULE_FOUR, TYPICAL_MODULE_SIX),
                model.getFilteredModuleList());

        //test findPerson using AcademicYear
        AcademicYearContainsKeywordsPredicate academicYearPredicate =
                prepareAcademicYearPredicate("1920 1718 1516");
        FindModuleCommand academicYearCommand = new FindModuleCommand(academicYearPredicate);
        expectedModel.updateFilteredModuleList(academicYearPredicate);
        assertCommandSuccess(academicYearCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_MODULE_FOUR, TYPICAL_MODULE_FIVE, TYPICAL_MODULE_SIX),
                model.getFilteredModuleList());

        //test findPerson using Semester
        expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 2);
        SemesterContainsKeywordsPredicate semesterPredicate = prepareSemesterPredicate("2");
        FindModuleCommand semesterCommand = new FindModuleCommand(semesterPredicate);
        expectedModel.updateFilteredModuleList(semesterPredicate);
        assertCommandSuccess(semesterCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_MODULE_FOUR, TYPICAL_MODULE_SIX), model.getFilteredModuleList());
    }

    /**
     * Parses {@code userInput} into a {@code ModuleCodeContainsKeywordsPredicate}.
     */
    private ModuleCodeContainsKeywordsPredicate prepareModuleCodePredicate(String userInput) {
        return new ModuleCodeContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code ModuleTitleContainsKeywordsPredicate}.
     */
    private ModuleTitleContainsKeywordsPredicate prepareModuleTitlePredicate(String userInput) {
        return new ModuleTitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code AcademicYearContainsKeywordsPredicate}.
     */
    private AcademicYearContainsKeywordsPredicate prepareAcademicYearPredicate(String userInput) {
        return new AcademicYearContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code SemesterContainsKeywordsPredicate}.
     */
    private SemesterContainsKeywordsPredicate prepareSemesterPredicate(String userInput) {
        return new SemesterContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
