package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * The browser panel handle responsible for displaying the information
 * when the UI is switched to the Person's panel.
 */
public class PersonBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#personBrowserPanel";
    public static final String TABLE_ID_MODULES = "#moduleTableView";
    public static final String TABLE_ID_OCCASIONS = "#occasionTableView";

    // A cached value of the last remembered person who was selected.
    private Person lastRememberedPerson;
    private TableView<Module> tableOfModules;
    private ObservableList<Module> moduleItems;
    private TableView<Occasion> tableOfOccasions;
    private ObservableList<Occasion> occasionItems;

    public PersonBrowserPanelHandle(Node personBrowserPanelNode) {
        super(personBrowserPanelNode);
        tableOfModules = getChildNode(TABLE_ID_MODULES);
        moduleItems = tableOfModules.getItems();
        tableOfOccasions = getChildNode(TABLE_ID_OCCASIONS);
        occasionItems = tableOfOccasions.getItems();
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberPerson(Person person) {
        this.lastRememberedPerson = person;
        moduleItems = person.getModuleList().asUnmodifiableObservableList();
        occasionItems = person.getOccasionList().asUnmodifiableObservableList();
    }

    /**
     * Gets the person whose details are currently being displayed on the panel.
     */
    public Person getLastRememberedPerson() {
        return lastRememberedPerson;
    }

    /**
     * Gets the current table of modules of the person who is currently selected.
     */
    public TableView getTableOfModules() {
        return tableOfModules;
    }

    /**
     * Gets the current table of occasions of the person who is currently selected.
     */
    public TableView getTableOfOccasions() {
        return tableOfOccasions;
    }

    public ObservableList<Module> getCurrentModules() {
        return this.moduleItems;
    }

    public ObservableList<Occasion> getCurrentOccasions() {
        return this.occasionItems;
    }
}
