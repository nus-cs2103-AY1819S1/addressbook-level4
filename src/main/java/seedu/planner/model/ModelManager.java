package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.model.ModulePlanner.MAX_NUMBER_SEMESTERS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.SortedList;

import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.ModulePlannerChangedEvent;
import seedu.planner.model.course.DegreeRequirement;
import seedu.planner.model.course.FocusArea;
import seedu.planner.model.course.Major;
import seedu.planner.model.module.Module;
import seedu.planner.model.module.ModuleInfo;
import seedu.planner.model.module.ModuleType;
import seedu.planner.model.user.UserProfile;

/**
 * Represents the in-memory model of the planner book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedModulePlanner versionedModulePlanner;

    private final List<SortedList<Module>> takenModulesPerSemester;

    private final SortedList<Module> takenModules;
    private final SortedList<Module> availableModules;

    /**
     * Initializes a ModelManager with the given modulePlanner and userPrefs.
     */
    public ModelManager(ReadOnlyModulePlanner modulePlanner, UserPrefs userPrefs) {
        super();
        requireAllNonNull(modulePlanner, userPrefs);

        logger.fine("Initializing with planner: " + modulePlanner + " and user prefs " + userPrefs);

        versionedModulePlanner = new VersionedModulePlanner(modulePlanner);

        takenModulesPerSemester = new ArrayList<>();
        for (int i = 0; i < MAX_NUMBER_SEMESTERS; i++) {
            ObservableList<Module> takenModules = versionedModulePlanner.getTakenModulesForIndex(i);
            SortedList<Module> sortedTakenModules = new SortedList<>(takenModules, (x, y) -> x.compareTo(y));
            takenModulesPerSemester.add(sortedTakenModules);
        }

        takenModules = new SortedList<>(versionedModulePlanner.getTakenModules(), (x, y) -> x.compareTo(y));
        availableModules = new SortedList<>(versionedModulePlanner.getAvailableModules());
    }

    public ModelManager() {
        this(new ModulePlanner(), new UserPrefs());
    }

    @Override
    public void setUpUserProfile(Major major, Set<FocusArea> focusAreas) {
        versionedModulePlanner.setUserProfile(new UserProfile(major, focusAreas));
        indicateModulePlannerChanged();
    }

    @Override
    public void resetData(ReadOnlyModulePlanner newData) {
        versionedModulePlanner.resetData(newData);
        indicateModulePlannerChanged();
    }

    @Override
    public ReadOnlyModulePlanner getModulePlanner() {
        return versionedModulePlanner;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateModulePlannerChanged() {
        raise(new ModulePlannerChangedEvent(versionedModulePlanner));
    }

    //=========== Module methods =============================================================================

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedModulePlanner.hasModule(module);
    }

    @Override
    public void deleteModules(Set<Module> moduleCodes) {
        versionedModulePlanner.deleteModules(moduleCodes);
        indicateModulePlannerChanged();
    }

    @Override
    public boolean isModuleOffered(Module module) {
        return ModuleInfo.getFromModuleCode(module.getCode()).isPresent();
    }

    /**
     * Retrieves the actual module information of the {@code module}
     * from {@code moduleInfo} and {@code finalizes} that module
     * with the actual module information.
     *
     * @param module The module to be finalized
     * @return The module with the actual module information
     */
    public Module finalizeModule(Module module) {
        Optional<ModuleInfo> optModuleInfo = ModuleInfo.getFromModuleCode(module.getCode());
        if (optModuleInfo.isPresent()) {
            return new Module(ModuleType.PROGRAMME_REQUIREMENTS, optModuleInfo.get());
        }
        return new Module("Unknown");
    }

    @Override
    public Set<Module> finalizeModules(Set<Module> modules) {
        Set<Module> finalizedModules = new HashSet<>();
        for (Module m : modules) {
            finalizedModules.add(finalizeModule(m));
        }
        return finalizedModules;
    }

    @Override
    public void addModules(Set<Module> modules, int index) {
        Set<Module> finalizedModules = finalizeModules(modules);
        versionedModulePlanner.addModules(finalizedModules, index);
        indicateModulePlannerChanged();
    }

    public ObservableMap<DegreeRequirement, int[]> getStatus() {
        return versionedModulePlanner.getStatus();
    }

    //@@author Hilda-Ang

    @Override
    public void suggestModules(int index) {
        versionedModulePlanner.suggestModules(index);
    }

    //=========== Module List Accessors =============================================================

    @Override
    public void listTakenModulesAll() {
        versionedModulePlanner.listTakenModulesAll();
    }

    @Override
    public void listTakenModulesForYear(int year) {
        versionedModulePlanner.listTakenModulesForYear(year);
    }

    @Override
    public ObservableList<Module> getTakenModules() {
        return FXCollections.unmodifiableObservableList(takenModules);
    }

    @Override
    public ObservableList<Module> getTakenModulesForIndex(int index) {
        return FXCollections.unmodifiableObservableList(takenModulesPerSemester.get(index));
    }

    @Override
    public ObservableList<Module> getAvailableModules() {
        return FXCollections.unmodifiableObservableList(availableModules);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoModulePlanner() {
        return versionedModulePlanner.canUndo();
    }

    @Override
    public boolean canRedoModulePlanner() {
        return versionedModulePlanner.canRedo();
    }

    @Override
    public void undoModulePlanner() {
        versionedModulePlanner.undo();
        indicateModulePlannerChanged();
    }

    @Override
    public void redoModulePlanner() {
        versionedModulePlanner.redo();
        indicateModulePlannerChanged();
    }

    @Override
    public void commitModulePlanner() {
        versionedModulePlanner.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedModulePlanner.equals(other.versionedModulePlanner);
    }
}
