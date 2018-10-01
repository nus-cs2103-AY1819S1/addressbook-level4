package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Anakin_ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Represents the in-memory model of Anakin data.
 */
public class Anakin_ModelManager extends Anakin_ComponentManager implements Anakin_Model {
    public static final Logger logger = LogsCenter.getLogger(Anakin_ModelManager.class);

    private final Anakin_VersionedAnakin versionedAnakin;
    private final FilteredList<Anakin_Deck> filteredDecks;

    /**
     * Initializes a Anakin_ModelManager with the given Anakin and userPrefs.
     */
    public Anakin_ModelManager(Anakin_ReadOnlyAnakin anakin, UserPrefs userPrefs) {
        super();
        requireAllNonNull(anakin, userPrefs);

        logger.fine("Initializing with anakin: " + anakin + " and user prefs " + userPrefs);

        versionedAnakin = new Anakin_VersionedAnakin(anakin);
        filteredDecks = new FilteredList<>(versionedAnakin.getDeckList());
    }

    public Anakin_ModelManager() {
        this(new Anakin(), new UserPrefs());
    }

    @Override
    public void resetData(Anakin_ReadOnlyAnakin newData) {

    }

    @Override
    public Anakin_ReadOnlyAnakin getAnakin() {
        return null;
    }

    @Override
    public boolean hasDeck(Anakin_Deck deck) {
        return false;
    }

    @Override
    public void addDeck(Anakin_Deck deck) {

    }

    @Override
    public void deleteDeck(Anakin_Deck deck) {

    }

    @Override
    public void updateDeck(Anakin_Deck target, Anakin_Deck editedDeck) {

    }

    @Override
    public ObservableList<Anakin_Deck> getFilteredDeckList() {
        return null;
    }

    @Override
    public void updateFilteredDeckList(Predicate<Anakin_Deck> predicate) {

    }

    @Override
    public boolean canUndoAnakin() {
        return false;
    }

    @Override
    public boolean canRedoAnakin() {
        return false;
    }

    @Override
    public void undoAnakin() {

    }

    @Override
    public void redoAnakin() {

    }

    @Override
    public void commitAnakin() {

    }
}
