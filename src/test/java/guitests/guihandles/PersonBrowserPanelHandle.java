package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seedu.address.model.person.Person;

public class PersonBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#personBrowserPanel";
    public static final String TABLE_ID_MODULES = "#moduleTableView";
    public static final String TABLE_ID_OCCASIONS = "#occasionTableView";

    // A cached value of the last remembered person who was selected.
    private Person lastRememberedPerson;
    private TableView tableOfModules;
    private TableView tableOfOccasions;

    public PersonBrowserPanelHandle(Node personBrowserPanelNode) {
        super(personBrowserPanelNode);

        tableOfModules = getChildNode(TABLE_ID_MODULES);
        //tableOfOccasions = getChildNode(TABLE_ID_OCCASIONS);
        // TableColumn test = (TableColumn) tableOfOccasions.getColumns().stream().findFirst().get();
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberPerson(Person person) {
        this.lastRememberedPerson = person;
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
}
