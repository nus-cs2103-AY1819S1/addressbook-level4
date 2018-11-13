package seedu.souschef.ui;

import javafx.scene.layout.Region;
import seedu.souschef.model.UniqueType;

/**
 * An UI component that displays information of a {@code UniqueType}.
 */
public abstract class GenericCard<T extends UniqueType> extends UiPart<Region> {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AppContent level 4</a>
     */

    // Add on other attributes of your UniqueType (e.g. as Label, FlowPane, etc...)

    public GenericCard(String fxmlFileName) {
        super(fxmlFileName);
    }

    @Override
    public abstract boolean equals(Object other);
}
