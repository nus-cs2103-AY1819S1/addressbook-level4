package guitests.guihandles;

import javafx.scene.Node;
import seedu.address.model.person.Person;

public class PersonBrowserPanelHandle extends NodeHandle<Node>{

    // The currently selected person whose module and occasion information is displayed
    // on the panel.
    private Person lastRememberedPerson;

    public PersonBrowserPanelHandle(Node personBrowserPanelNode) {
        super(personBrowserPanelNode);
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberPerson(Person person) {
        lastRememberedPerson = person;
    }

    /**
     * Gets the person whose details are currently being displayed on the panel.
     */
    public Person getLastRememberedPerson() {
        return lastRememberedPerson;
    }
}
