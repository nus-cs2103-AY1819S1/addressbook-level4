package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.UpdateAssignmentCommand.MESSAGE_SUCCESS;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.jupiter.api.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.UpdateAssignmentCommand.UpdateAssignmentDescriptor;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.AssignmentBuilder;
import seedu.superta.testutil.TutorialGroupBuilder;
import seedu.superta.testutil.UpdateAssignmentDescriptorBuilder;

public class UpdateAssignmentCommandTest {

    private static final String TUTORIAL_GROUP = "04a";
    private static final String ASSIGNMENT_TITLE = "Lab 1";
    private static final Double MAX_MARKS = 40.0;
    private static Assignment assignment;

    @Test
    public void execute_assignmentAndTutorialGroupFound_success() {
        assignment = new AssignmentBuilder()
                .withTitle(ASSIGNMENT_TITLE)
                .withMaxMarks(MAX_MARKS)
                .build();
        TutorialGroup tutorialGroup = new TutorialGroupBuilder()
                .withId(TUTORIAL_GROUP)
                .build();
        UpdateAssignmentDescriptor updateAssignmentDescriptor = new UpdateAssignmentDescriptorBuilder(assignment)
                .build();

        Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        model.addTutorialGroup(tutorialGroup);
        model.addAssignment(TUTORIAL_GROUP, assignment);
        CommandHistory commandHistory = new CommandHistory();
        UpdateAssignmentCommand command = new UpdateAssignmentCommand(
                TUTORIAL_GROUP,
                assignment.getTitle(),
                updateAssignmentDescriptor);

        Assignment assignmentCopy = new AssignmentBuilder()
                .withTitle(ASSIGNMENT_TITLE)
                .build();
        TutorialGroup tutorialGroupCopy = new TutorialGroupBuilder()
                .withId(TUTORIAL_GROUP)
                .build();

        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel.addTutorialGroup(tutorialGroupCopy);
        expectedModel.addAssignment(TUTORIAL_GROUP, assignmentCopy);
        expectedModel.updateAssignment(TUTORIAL_GROUP, assignment.getTitle(), assignment);
        expectedModel.commitSuperTaClient();

        String expectedMessage = String.format(MESSAGE_SUCCESS, TUTORIAL_GROUP,
                assignment.getTitle());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
