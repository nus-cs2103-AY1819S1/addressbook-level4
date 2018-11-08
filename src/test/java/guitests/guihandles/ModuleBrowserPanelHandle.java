package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;

/**
 * The browser panel handle responsible for acquiring information of the current
 * list displays when the UI is switched to the module panel.
 */
public class ModuleBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#moduleBrowserPanel";
    public static final String TABLE_ID_PERSONS = "#personTableView";

    // The currently selected person whose module and occasion information is displayed
    // on the panel.
    private Module lastRememberedModule;
    private TableView<Person> tableOfPersons;
    private ObservableList<Person> personItems;

    public ModuleBrowserPanelHandle(Node moduleBrowserPanelNode) {
        super(moduleBrowserPanelNode);
        tableOfPersons = getChildNode(TABLE_ID_PERSONS);
        personItems = tableOfPersons.getItems();
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberModule(Module module) {
        lastRememberedModule = module;
        personItems = module.getStudents().asUnmodifiableObservableList();
    }

    /**
     * Gets the person whose details are currently being displayed on the panel.
     */
    public Module getLastRememberedModule() {
        return lastRememberedModule;
    }

    public ObservableList<Person> getPersonItems() {
        return this.personItems;
    }
}
