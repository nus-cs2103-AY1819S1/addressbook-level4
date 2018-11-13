package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalModules.MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP;
import static seedu.address.testutil.TypicalModules.getModulesWithNonGradeAffectingModules;
import static seedu.address.testutil.TypicalModules.getModulesWithoutNonGradeAffectingModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.ModuleCompletedException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

//@@author jeremiah-ang
/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

    public static final String DELIMITER = " ";

    private static final Module GRADE_BMINUS_4MC_A = new ModuleBuilder()
            .withCode("BMINUSA")
            .withCredit(4)
            .withGrade("B-")
            .build();
    private static final Module GRADE_A_4MC_A = new ModuleBuilder()
            .withCode("AA")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module INCOMPLETE_4MC_A = new ModuleBuilder()
            .withCode("INCOMPLETEA")
            .withCredit(4)
            .withCompleted(false)
            .noGrade()
            .build();
    private static final Module INCOMPLETE_4MC_B = new ModuleBuilder()
            .withCode("INCOMPLETEB")
            .withCredit(4)
            .withCompleted(false)
            .noGrade()
            .build();
    private static final Module INCOMPLETE_4MC_C = new ModuleBuilder()
            .withCode("INCOMPLETEC")
            .withCredit(4)
            .withCompleted(false)
            .noGrade()
            .build();
    private static final Module INCOMPLETE_5MC_A = new ModuleBuilder()
            .withCode("INCOMPLETE5A")
            .withCredit(5)
            .withCompleted(false)
            .noGrade()
            .build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void assertAdjustAdjustedModuleSuccess() {
        Transcript transcript = new Transcript();
        Module adjustedModuleBPlus = TypicalModules.duplicateWithGradesAdjusted(
                new ModuleBuilder(INCOMPLETE_4MC_A).withGrade(TypicalModules.GRADE_B_PLUS).build());

        transcript.addModule(GRADE_A_4MC_A);
        transcript.addModule(GRADE_BMINUS_4MC_A);
        transcript.addModule(adjustedModuleBPlus);
        transcript.addModule(INCOMPLETE_4MC_B);
        transcript.setCapGoal(4.0);
        assertTargetGradesEquals(transcript, "B+");
        transcript.adjustModule(adjustedModuleBPlus, new Grade(TypicalModules.GRADE_B_MINUS));
        assertTargetGradesEquals(transcript, "A");
    }

    @Test
    public void assertAdjustToFSuccess() {
        double capGoal = new Grade(TypicalModules.GRADE_B_MINUS).getPoint();
        Module targetModule = INCOMPLETE_4MC_A.updateTargetGrade(capGoal);
        Grade grade = new Grade(TypicalModules.GRADE_F);
        assertAdjustCapGoalImpossible(capGoal, targetModule, grade, INCOMPLETE_4MC_A, GRADE_BMINUS_4MC_A);

        capGoal = 1.0;
        targetModule = INCOMPLETE_4MC_A.updateTargetGrade(capGoal);
        assertAdjustCapGoalPossible(capGoal, targetModule, grade, INCOMPLETE_4MC_A, GRADE_BMINUS_4MC_A);
    }

    @Test
    public void assertAdjustToCsSuccess() {
        Module targetModule = INCOMPLETE_4MC_A.updateTargetGrade(5.0);
        Grade grade = new Grade(TypicalModules.GRADE_CS);
        assertAdjustCapGoalImpossible(4.0, targetModule, grade, INCOMPLETE_4MC_A, GRADE_BMINUS_4MC_A);

        targetModule = INCOMPLETE_4MC_A.updateTargetGrade(3.0);
        assertAdjustCapGoalPossible(3.0, targetModule, grade, INCOMPLETE_4MC_A, GRADE_BMINUS_4MC_A);
    }

    /**
     * Asserts CAP Goal is impossible
     * @param transcript
     */
    private void assertCapGoalIsImpossible(Transcript transcript) {
        assertTrue(transcript.isCapGoalImpossible());
        List<Module> targetedModules = transcript.getTargetedModulesList();
        assertTrue(targetedModules.isEmpty());
    }

    /**
     * Asserts CAP Goal is possible
     * @param transcript
     */
    private void assertCapGoalIsPossible(Transcript transcript) {
        assertFalse(transcript.isCapGoalImpossible());
    }

    /**
     * Asserts CAP Goal is possible after adjustment
     * @param capGoal
     * @param targetModule
     * @param grade
     * @param modules
     */
    private void assertAdjustCapGoalPossible(double capGoal, Module targetModule, Grade grade, Module... modules) {
        assertAdjustCapGoalBehavior(capGoal, targetModule, grade, false, modules);
    }

    /**
     * Asserts CAP Goal is impossible after adjustment
     * @param capGoal
     * @param targetModule
     * @param grade
     * @param modules
     */
    private void assertAdjustCapGoalImpossible(double capGoal, Module targetModule, Grade grade, Module... modules) {
        assertAdjustCapGoalBehavior(capGoal, targetModule, grade, true, modules);
    }

    /**
     * Asserts whether CAP Goal is impossible nor not after adjustment
     * @param capGoal
     * @param targetModule
     * @param grade
     * @param isImpossible
     * @param modules
     */
    private void assertAdjustCapGoalBehavior(
            double capGoal, Module targetModule, Grade grade, boolean isImpossible, Module... modules) {
        Transcript transcript = new Transcript();
        transcript.setModules(List.of(modules));
        transcript.setCapGoal(capGoal);
        transcript.adjustModule(targetModule, grade);
        if (isImpossible) {
            assertCapGoalIsImpossible(transcript);
        } else {
            assertCapGoalIsPossible(transcript);
        }
    }

    @Test
    public void assertAdjustCompletedModuleFailure() {
        Transcript transcript = new Transcript();
        transcript.addModule(GRADE_A_4MC_A);
        thrown.expect(ModuleCompletedException.class);
        transcript.adjustModule(GRADE_A_4MC_A, new Grade(TypicalModules.GRADE_B_MINUS));
    }

    @Test
    public void assertAdjustLastTargetModuleHigherSuccess() {
        double capGoal = new Grade(TypicalModules.GRADE_B_MINUS).getPoint();
        Module targetModule = INCOMPLETE_4MC_A.updateTargetGrade(capGoal);
        Grade grade = new Grade(TypicalModules.GRADE_A_PLUS);
        assertAdjustCapGoalPossible(capGoal, targetModule, grade, INCOMPLETE_4MC_A);
    }

    @Test
    public void assertAdjustLastTargetModuleLowerCapGoalImpossibleSuccess() {
        double capGoal = new Grade(TypicalModules.GRADE_B_PLUS).getPoint();
        Module targetModule = INCOMPLETE_4MC_A.updateTargetGrade(capGoal);
        Grade grade = new Grade(TypicalModules.GRADE_B_MINUS);
        assertAdjustCapGoalImpossible(capGoal, targetModule, grade, INCOMPLETE_4MC_A);
    }

    @Test
    public void assertAdjustNotExistModuleFailure() {
        List<Module> modules = new ArrayList<>(Arrays.asList(GRADE_A_4MC_A, INCOMPLETE_4MC_A));
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        thrown.expect(ModuleNotFoundException.class);
        transcript.adjustModule(
                new ModuleBuilder().withCode("INVALID").noGrade().build(),
                new Grade().adjustGrade(TypicalModules.GRADE_B_PLUS));
    }

    @Test
    public void assertTypicalModulesCapScoreSuccess() {
        List<Module> modules = getModulesWithoutNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);
    }

    @Test
    public void assertCapScoreWithSuModuleSuccess() {
        List<Module> modules = getModulesWithNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);
    }

    @Test
    public void assertAddingIncompleteModuleWithoutCapGoalNoTargetGrade() {
        Transcript transcript = new Transcript();
        transcript.addModule(INCOMPLETE_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_B);
        transcript.addModule(INCOMPLETE_4MC_C);
        assertTargetGradesEquals(transcript, "");
    }

    @Test
    public void assertTargetGradesIsNotHigherThanMinimumRequirementSuccess() {
        Transcript transcript = new Transcript();
        transcript.addModule(INCOMPLETE_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_B);
        transcript.addModule(INCOMPLETE_4MC_C);
        transcript.setCapGoal(4.0);
        assertTargetGradesEquals(transcript, "B+ B+ B+");

        transcript = new Transcript();
        transcript.addModule(INCOMPLETE_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_B);
        transcript.addModule(INCOMPLETE_5MC_A);
        transcript.setCapGoal(3.0);
        assertTargetGradesEquals(transcript, "B- B- B-");

        transcript = new Transcript();
        transcript.addModule(INCOMPLETE_4MC_A);
        transcript.setCapGoal(2.0);
        assertTargetGradesEquals(transcript, "C");

        transcript = new Transcript();
        transcript.addModule(GRADE_A_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_B);
        transcript.setCapGoal(4.0);
        assertTargetGradesEquals(transcript, "B B");
    }

    @Test
    public void assertAdjustIncreaseTargetGradeWillReduceAnotherSuccess() {
        assertAdjustWillAffectAnotherSuccess(TypicalModules.GRADE_A_PLUS, "B- B-");
    }

    @Test
    public void assertAdjustDecreaseTargetGradeWillIncreaseAnotherSuccess() {
        assertAdjustWillAffectAnotherSuccess(TypicalModules.GRADE_B_MINUS, "B+ B+");
    }

    /**
     * Asserts that adjusting a target module will affect the others.
     * @param adjustTo
     * @param expectedTargetGrades
     */
    private void assertAdjustWillAffectAnotherSuccess(String adjustTo, String expectedTargetGrades) {
        Transcript transcript;
        transcript = new Transcript();
        transcript.addModule(GRADE_A_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_A);
        transcript.addModule(INCOMPLETE_4MC_B);
        transcript.addModule(INCOMPLETE_4MC_C);
        transcript.setCapGoal(4.0);
        assertTargetGradesEquals(transcript, "B+ B B");
        transcript.adjustModule(
                INCOMPLETE_4MC_A.updateTargetGrade(4.0), new Grade().adjustGrade(adjustTo));
        assertTargetGradesEquals(transcript, expectedTargetGrades);
    }

    @Test
    public void testTriggersForTargetGradeCalculation() {
        Transcript transcript = new Transcript();
        transcript.addModule(GRADE_A_4MC_A);
        assertTargetGradesEquals(transcript, "");

        transcript.addModule(GRADE_BMINUS_4MC_A);
        assertTargetGradesEquals(transcript, "");

        transcript.setCapGoal(5.0);
        assertTargetGradesEquals(transcript, "");

        transcript.addModule(INCOMPLETE_4MC_A);
        assertCapGoalIsImpossible(transcript);

        transcript.setCapGoal(4.0);
        assertTargetGradesEquals(transcript, "B+");

        transcript.addModule(INCOMPLETE_4MC_B);
        assertTargetGradesEquals(transcript, "B+ B+");
    }

    @Test
    public void assertSetImpossibleGoalSuccess() {
        Transcript transcript = new Transcript();
        transcript.addModule(GRADE_BMINUS_4MC_A);
        transcript.setCapGoal(5.0);
        assertCapGoalIsImpossible(transcript);

        transcript = new Transcript();
        transcript.setCapGoal(5.0);
        assertCapGoalIsImpossible(transcript);
    }

    /**
     * Assert that the modules will have the CAP score of expectedCapScore
     * @param modules
     * @param expectedCapScore
     */
    private void assertCapScoreEquals(List<Module> modules, Double expectedCapScore) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        double cap = transcript.getCurrentCap();
        assertEquals(Double.valueOf(cap), expectedCapScore);
    }

    /**
     * Gets target grades as a delimited String
     * @param transcript
     * @return
     */
    private String getTargetGradesStringFromTranscript(Transcript transcript) {
        ObservableList<Module> targetModules = transcript.getTargetedModulesList();
        List<String> targetGrades = new ArrayList<>();
        targetModules.forEach(module -> targetGrades.add(module.getGrade().value));
        String targetGradesString = String.join(DELIMITER, targetGrades);
        return targetGradesString;
    }

    /**
     * Asserts that the given transcript will result in expected target grades
     * @param transcript
     * @param expectedTargetGrades
     */
    public void assertTargetGradesEquals(Transcript transcript, String expectedTargetGrades) {
        String targetGrades = getTargetGradesStringFromTranscript(transcript);
        assertEquals(targetGrades, expectedTargetGrades);
    }

}
