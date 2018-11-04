package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;
import static seedu.address.testutil.TypicalModules.getTypicalTranscript;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the {@code Model},
 * {@code UndoCommand} and {@code RedoCommand}) and unit tests for
 * {@code EditModuleCommandTest}.
 */
public class EditModuleCommandTest {

    private Model model = new ModelManager(getTypicalTranscript(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Target should be in the transcript.
     */
    @Test
    public void executeTargetNotInTranscriptFail() {/*
        EditModuleCommand editModuleCommand = new EditModuleCommand(
                ASKING_QUESTIONS.getCode(),
                null,
                null,
                DISCRETE_MATH.getCode(),
                null,
                null,
                null,
                null);

        assertCommandFailure(editModuleCommand,
                model, commandHistory,
                EditModuleCommand.MESSAGE_NO_SUCH_MODULE);*/
    }

    /**
     * If there is a change in grade, target should not be incomplete
     */
    @Test
    public void executeTargetIsIncompleteGradeChangeFail() {/*
        Grade gradeGeneratedByTarget = DISCRETE_MATH.getGrade()
                .targetGrade(TypicalModules.GRADE_F);

        Module discreteMathIncomplete = new ModuleBuilder(DISCRETE_MATH)
                .withGrade(gradeGeneratedByTarget)
                .withCompleted(false)
                .build();

        model.updateModule(DISCRETE_MATH, discreteMathIncomplete);
        model.commitTranscript();

        EditModuleCommand editModuleCommand = new EditModuleCommand(
                DISCRETE_MATH.getCode(),
                null,
                null,
                null,
                null,
                null,
                null,
                ASKING_QUESTIONS.getGrade());

        assertCommandFailure(editModuleCommand,
                model, commandHistory,
                EditModuleCommand.MESSAGE_INCOMPLETE_MODULE_GRADE_CHANGE);

        model.updateModule(discreteMathIncomplete, DISCRETE_MATH);
        model.commitTranscript();*/
    }

    /**
     * Edited module can only share the same code, year, and semester as target
     * module. Edited module cannot share the same code, year, and semester with
     * any other module.
     */
    @Test
    public void executeEditedModuleAlreadyExistFail() {/*
        model.addModule(ASKING_QUESTIONS);
        model.commitTranscript();

        EditModuleCommand editModuleCommand = new EditModuleCommand(
                ASKING_QUESTIONS.getCode(),
                null,
                null,
                DISCRETE_MATH.getCode(),
                DISCRETE_MATH.getYear(),
                DISCRETE_MATH.getSemester(),
                DISCRETE_MATH.getCredits(),
                DISCRETE_MATH.getGrade());

        assertCommandFailure(editModuleCommand,
                model, commandHistory,
                EditModuleCommand.MESSAGE_MODULE_ALREADY_EXIST);

        model.deleteModule(ASKING_QUESTIONS);
        model.commitTranscript();*/
    }

    @Test
    public void executeEditCommandSuccess() {/*
        EditModuleCommand editModuleCommand = new EditModuleCommand(
                DISCRETE_MATH.getCode(),
                null,
                null,
                null,
                null,
                null,
                null,
                ASKING_QUESTIONS.getGrade());

        Module editedDiscreteMath = new ModuleBuilder(DISCRETE_MATH)
                .withGrade(ASKING_QUESTIONS.getGrade())
                .build();

        String expectedMessage = String.format(
                EditModuleCommand.MESSAGE_EDIT_SUCCESS,
                editedDiscreteMath);

        ModelManager expectedModel = new ModelManager(model.getTranscript(),
                new UserPrefs());
        expectedModel.updateModule(DISCRETE_MATH, editedDiscreteMath);
        expectedModel.commitTranscript();

        assertCommandSuccess(editModuleCommand,
                model, commandHistory,
                expectedMessage, expectedModel);*/
    }
}
