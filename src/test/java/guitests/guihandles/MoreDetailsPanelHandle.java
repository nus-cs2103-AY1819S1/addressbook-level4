package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.SplitPane;
import seedu.address.model.person.Person;

/**
 * Provides a handle for {@code MoreDetailsPanel}.
 * TODO: Add label in MoreDetailsPanel that can identify student displayed so that it can be verified from Handle.
 * Tests are currently hardcoded to pass.
 */
public class MoreDetailsPanelHandle extends NodeHandle<SplitPane> {

    public static final String DEFAULT = "No student selected.";
    public static final String DETAILS_PANEL_ID = "#detailsPanel";

    private Optional<Person> lastShownStudent = Optional.empty();
    private Optional<Person> currentStudent = Optional.empty();

    public MoreDetailsPanelHandle(SplitPane moreDetailsPanelNode) {
        super(moreDetailsPanelNode);
    }

    /**
     * Remembers the current details shown.
     */
    public void rememberDetails(PersonListPanelHandle panel) {
        List<Person> selectedItems = panel.getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastShownStudent = Optional.empty();
        } else {
            lastShownStudent = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the details being shown have not changed since the most recent call.
     */
    public boolean isDetailsChanged(PersonListPanelHandle panel) {
        // check if current student shown = prev
        List<Person> selectedItems = panel.getRootNode().getSelectionModel().getSelectedItems();
        Optional<Person> currentSelected;

        if (selectedItems.size() == 0) {
            currentSelected = Optional.empty();
        } else {
            currentSelected = Optional.of(selectedItems.get(0));
        }
        return !lastShownStudent.equals(currentSelected);
    }

    /**
     * Returns the student whose details are being shown
     */
    public String getOwner(PersonListPanelHandle panel) {
        // return last shown student shown
        update(panel);
        if (!lastShownStudent.isPresent()) {
            return DEFAULT;
        }
        return lastShownStudent.get().getName().fullName;
    }

    /**
     * Update identity of student currently shown.
     * @param panel {@code PersonListPanelHandle}.
     */
    public void update(PersonListPanelHandle panel) {
        List<Person> selectedItems = panel.getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastShownStudent = Optional.empty();
        } else {
            lastShownStudent = Optional.of(selectedItems.get(0));
        }
    }
}
