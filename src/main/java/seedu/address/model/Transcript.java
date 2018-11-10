package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.exceptions.CapGoalIsImpossibleException;
import seedu.address.model.exceptions.NoTargetableModulesException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleCompletedException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.module.exceptions.MultipleModuleEntryFoundException;

//@@author alexkmj
/**
 * Wraps all data at the transcript level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class Transcript implements ReadOnlyTranscript {
    private static final Logger logger = LogsCenter.getLogger(Transcript.class);

    private final UniqueModuleList modules;
    private CapGoal capGoal;
    private double currentCap;

    /*
     * The 'unusual' code block below is an non-static initialization block,
     * sometimes used to avoid duplication between constructors.
     * See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are
     * other ways to avoid duplication among constructors.
     */
    {
        modules = new UniqueModuleList();
    }

    public Transcript() {
        capGoal = new CapGoal();
        currentCap = 0;
    }

    /**
     * Creates an Transcript using the Modules in the {@code toBeCopied}
     */
    public Transcript(ReadOnlyTranscript toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
        modulesUpdated();
    }

    /**
     * Resets the existing data of this {@code Transcript} with {@code newData}.
     */
    public void resetData(ReadOnlyTranscript newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
        setCapGoal(newData.getCapGoal());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists
     * in the transcript.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the transcript.
     * The module must not already exist in the transcript.
     */
    public void addModule(Module p) {
        modules.add(p);
        modulesUpdated();
    }

    /**
     * Replaces the given module {@code target} in the list with
     * {@code editedModule}. {@code target} must exist in the transcript.
     * The module identity of {@code editedModule} must not be the same as
     * another existing module in the transcript.
     */
    public void updateModule(Module target, Module editedModule) {
        requireNonNull(editedModule);
        modules.setModule(target, editedModule);
        modulesUpdated();
    }

    /**
     * Removes {@code key} from this {@code Transcript}.
     * {@code key} must exist in the transcript.
     */
    public void removeModule(Module key) {
        modules.remove(key);
        modulesUpdated();
    }

    //@@author jeremiah-ang
    @Override
    public double getCurrentCap() {
        return currentCap;
    }

    @Override
    public ObservableList<Module> getCompletedModuleList() {
        return getModuleList().filtered(Module::hasCompleted);
    }

    @Override
    public ObservableList<Module> getIncompleteModuleList() {
        return getModuleList().filtered(module -> !module.hasCompleted());
    }

    private void updateCurrentCap() {
        logger.info("Updating Current CAP");
        currentCap = calculateCap();
    }

    /**
     * Calculate CAP Score based on modules with scores
     *
     * @return cap: cap score
     */
    private double calculateCap() {

        ObservableList<Module> gradedModulesList = getGradedModulesList();
        double totalModulePoint = calculateTotalModulePoint(gradedModulesList);
        double totalModuleCredit = calculateTotalModuleCredit(gradedModulesList);

        double cap = 0;
        if (totalModuleCredit > 0) {
            cap = totalModulePoint / totalModuleCredit;
        }

        return cap;
    }

    /**
     * Calculates the total module point from the list of modules
     * @param modules
     * @return
     */
    private double calculateTotalModulePoint(ObservableList<Module> modules) {
        double totalPoint = 0;
        for (Module module : modules) {
            totalPoint += module.getGrade().getPoint() * module.getCredits().value;
        }
        return totalPoint;
    }

    /**
     * Calculates the total module credit from the list of modules
     * @param modules
     * @return
     */
    private double calculateTotalModuleCredit(ObservableList<Module> modules) {
        int totalModuleCredit = 0;
        for (Module module : modules) {
            totalModuleCredit += module.getCredits().value;
        }
        return totalModuleCredit;
    }

    /**
     * Filters for modules that is to be used for CAP calculation
     *
     * @return list of modules used for CAP calculation
     */
    private ObservableList<Module> getGradedModulesList() {
        return modules.getFilteredModules(this::moduleIsUsedForCapCalculation);
    }

    /**
     * Filters for modules that is to be assigned a target grade
     * @return gradedModulesList: a list of modules used for CAP calculation
     */
    private ObservableList<Module> getTargetableModulesList() {
        return modules.getFilteredModules(Module::isTargetable);
    }

    /**
     * Filters for modules that have target grades
     * @return gradedModulesList: a list of modules used for CAP calculation
     */
    protected ObservableList<Module> getTargetedModulesList() {
        return modules.getFilteredModules(Module::isTargetted);
    }

    /**
     * Check if the given module should be considered for CAP Calculation
     *
     * @param module
     * @return true if yes, false otherwise
     */
    private boolean moduleIsUsedForCapCalculation(Module module) {
        return module.hasCompleted() && module.isAffectCap();
    }

    /**
     * Calls relevant methods when the modules list is updated
     */
    private void modulesUpdated() {
        logger.info("Modules Updated... Updating Target Grades and Current CAP");
        updateTargetModuleGrades();
        updateCurrentCap();
    }

    /**
     * Replaces targetable module with an updated target grade
     */
    private void updateTargetModuleGrades() {
        logger.info("Updating Target Grades...");
        boolean shouldSkip = !capGoal.isSet();
        if (shouldSkip) {
            logger.info("No CAP Goal set, stopping target grades calculation.");
            return;
        }
        ObservableList<Module> targetableModules = getTargetableModulesList();

        try {
            List<Module> newTargetModules = getNewTargetModuleGrade(targetableModules);
            makeCapGoalPossible();
            replaceTargetModules(targetableModules, newTargetModules);
        } catch (CapGoalIsImpossibleException cgiie) {
            logger.info("CAP Goal is impossible to achieve.");
            makeCapGoalImpossible();
        } catch (NoTargetableModulesException ntme) {
            logger.info("No targetable modules.");
            makeCapGoalPossible();
        }
    }

    /**
     * Replaces Modules used to calculate target grade with new Modules with those target grades
     * @param targetableModules
     * @param newTargetModules
     */
    private void replaceTargetModules(
            List<Module> targetableModules, List<Module> newTargetModules) {
        if (targetableModules.isEmpty()) {
            return;
        }
        modules.removeAll(targetableModules);
        modules.addAll(newTargetModules);
    }


    /**
     * Calculates target module grade in order to achieve target goal
     * @return a list of modules with target grade if possible. null otherwise
     */
    private List<Module> getNewTargetModuleGrade(ObservableList<Module> targetableModules)
            throws CapGoalIsImpossibleException, NoTargetableModulesException {
        ObservableList<Module> gradedModules = getGradedModulesList();
        ObservableList<Module> adjustedModules = getGradedAdjustedModulesList();
        ObservableList<Module> sortedTargetableModules = targetableModules.sorted(
                Comparator.comparingInt(Module::getCreditsValue));

        double totalUngradedModuleCredit = calculateTotalModuleCredit(sortedTargetableModules);
        double totalMc = calculateTotalModuleCredit(gradedModules)
                + calculateTotalModuleCredit(adjustedModules) + totalUngradedModuleCredit;
        double currentTotalPoint = calculateTotalModulePoint(gradedModules)
                + calculateTotalModulePoint(adjustedModules);

        if (totalUngradedModuleCredit == 0) {
            if (totalMc == 0 || capGoal.getValue() > currentTotalPoint / totalMc) {
                throw new CapGoalIsImpossibleException();
            }
            throw new NoTargetableModulesException();
        }

        return createNewTargetModuleGrade(
                sortedTargetableModules,
                totalUngradedModuleCredit, totalMc, currentTotalPoint);
    }

    /**
     * Creates the new list of modules with target grade
     * @param sortedTargetableModules
     * @param totalUngradedModuleCredit
     * @param totalMc
     * @param currentTotalPoint
     * @return the new list of modules with target grade
     * @throws IllegalArgumentException  if totalUngradedModuleCredit is zero or negative
     */
    private List<Module> createNewTargetModuleGrade(
            ObservableList<Module> sortedTargetableModules,
            double totalUngradedModuleCredit, double totalMc, double currentTotalPoint)
            throws CapGoalIsImpossibleException {

        if (totalUngradedModuleCredit <= 0) {
            throw new IllegalArgumentException("totalUngradedModuleCredit cannot be zero or negative");
        }
        double totalScoreToAchieve = capGoal.getValue() * totalMc - currentTotalPoint;
        return calculateAndCreateNewTargetModuleGrade(
                sortedTargetableModules,
                totalUngradedModuleCredit, totalScoreToAchieve);
    }

    /**
     * Calculates and creates the new list of modules with target grade
     * @param sortedTargetableModules
     * @param givenTotalUngradedModuleCredit
     * @param givenTotalScoreToAchieve
     * @return the new list of modules with target grade
     * @throws IllegalArgumentException  if totalUngradedModuleCredit is zero or negative
     */
    private List<Module> calculateAndCreateNewTargetModuleGrade(
            ObservableList<Module> sortedTargetableModules,
            double givenTotalUngradedModuleCredit, double givenTotalScoreToAchieve)
            throws CapGoalIsImpossibleException {

        double totalUngradedModuleCredit = givenTotalUngradedModuleCredit;
        double totalScoreToAchieve = givenTotalScoreToAchieve;
        double unitScoreToAchieve;

        List<Module> targetModules = new ArrayList<>();
        Module newTargetModule;
        for (Module targetedModule : sortedTargetableModules) {
            unitScoreToAchieve = getUnitScoreToAchieve(totalUngradedModuleCredit, totalScoreToAchieve);
            newTargetModule = targetedModule.updateTargetGrade(unitScoreToAchieve);
            targetModules.add(newTargetModule);
            totalScoreToAchieve -= newTargetModule.getCreditsValue() * unitScoreToAchieve;
            totalUngradedModuleCredit -= newTargetModule.getCreditsValue();
        }

        return targetModules;
    }

    private double getUnitScoreToAchieve(double totalUngradedModuleCredit, double totalScoreToAchieve)
            throws CapGoalIsImpossibleException {
        if (totalUngradedModuleCredit <= 0) {
            logger.warning("Total Amount of ungraded Module Credit is 0 or lesser.");
            throw new IllegalArgumentException("totalUngradedModuleCredit cannot be zero or negative.");
        }
        double unitScoreToAchieve = Math.ceil(totalScoreToAchieve / totalUngradedModuleCredit * 2) / 2.0;
        if (unitScoreToAchieve > 5) {
            throw new CapGoalIsImpossibleException();
        }
        if (unitScoreToAchieve <= 0.5) {
            logger.info("Unit score to achieve is the minimum");
            return 0.1;
        }
        return unitScoreToAchieve;
    }

    private ObservableList<Module> getGradedAdjustedModulesList() {
        return modules.getFilteredModules(module -> module.isAdjusted() && module.isAffectCap());
    }

    @Override
    public CapGoal getCapGoal() {
        return capGoal;
    }

    private void setCapGoal(CapGoal capGoal) {
        this.capGoal = capGoal;
    }

    public void setCapGoal(double capGoal) {
        this.capGoal = new CapGoal(capGoal);
        updateTargetModuleGrades();
    }

    /**
     * Sets the capGoal impossible
     */
    private void makeCapGoalImpossible() {
        capGoal = capGoal.makeIsImpossible();
        removeTargetFromTargetedModules();
    }

    /**
     * Removes the given target grades to incomplete modules
     */
    private void removeTargetFromTargetedModules() {
        List<Module> targetedModules = getTargetedModulesList();
        List<Module> targetRemovedModules = new ArrayList<>();
        for (Module targetedModule : targetedModules) {
            targetRemovedModules.add(new Module(targetedModule, new Grade()));
        }
        modules.removeAll(targetedModules);
        modules.addAll(targetRemovedModules);
    }

    /**
     * Sets the capGoal as possible
     */
    private void makeCapGoalPossible() {
        assert capGoal.getValue() > 0;
        capGoal = new CapGoal(capGoal.getValue());
    }

    /**
     * Tells if the value is no longer possible
     * @return true if yes, false otherwise
     */
    public boolean isCapGoalImpossible() {
        return capGoal.isImpossible();
    }

    /**
     * Returns the matching module entry.
     * <p>
     * Finds the module with {@code targetCode}, {@code targetYear} if
     * {@code targetYear} is not null, and {@code targetSemester} if
     * {@code targetSemester} is not null.
     *
     * @param targetCode code to match
     * @param targetYear year to match if not null
     * @param targetSemester semester to match if not null
     * @return the matching module
     * @throws ModuleNotFoundException thrown when no entries match the
     * parameters.
     * @throws MultipleModuleEntryFoundException thrown when multiple entries
     * match the parameters.
     */
    public Module getOnlyOneModule(Code targetCode, Year targetYear,
            Semester targetSemester)
            throws ModuleNotFoundException, MultipleModuleEntryFoundException {
        return modules.getOnlyOneModule(targetCode, targetYear, targetSemester);
    }

    /**
     * Adjust the target Module to the desired Grade
     * @param targetModule
     * @param adjustGrade
     * @return adjusted Module
     */
    public Module adjustModule(Module targetModule, Grade adjustGrade) {
        requireNonNull(targetModule);
        requireNonNull(adjustGrade);
        if (targetModule.hasCompleted()) {
            throw new ModuleCompletedException();
        }

        Module adjustedModule = targetModule.adjustGrade(adjustGrade);
        //TODO: Use updateModule when fixed
        modules.remove(targetModule);
        modules.add(adjustedModule);
        modulesUpdated();
        return adjustedModule;
    }

    //@@author
    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Transcript // instanceof handles nulls
                && modules.equals(((Transcript) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
