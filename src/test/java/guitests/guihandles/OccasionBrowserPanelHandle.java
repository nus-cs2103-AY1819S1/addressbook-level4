package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

public class OccasionBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#occasionBrowserPanel";
    public static final String TABLE_ID_PERSONS = "#personTableView";

    // The currently selected person whose module and occasion information is displayed
    // on the panel.
    private Occasion lastRememberedOccasion;
    private TableView<Person> tableOfPersons;
    private ObservableList<Person> personItems;

    public OccasionBrowserPanelHandle(Node occasionBrowserPanelNode) {
        super(occasionBrowserPanelNode);
        tableOfPersons = getChildNode(TABLE_ID_PERSONS);
        personItems = tableOfPersons.getItems();
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberOccasion(Occasion occasion) {
        lastRememberedOccasion = occasion;
        this.personItems = occasion.getAttendanceList().asUnmodifiableObservableList();
    }

    /**
     * Gets the person whose details are currently being displayed on the panel.
     */
    public Occasion getLastRememberedOccasion() {
        return lastRememberedOccasion;
    }

    public ObservableList<Person> getPersonItems() {
        return this.personItems;
    }
}
