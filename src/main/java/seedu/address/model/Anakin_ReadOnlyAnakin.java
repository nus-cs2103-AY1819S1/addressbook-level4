package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.Anakin_deck.Anakin_Card;
import seedu.address.model.Anakin_deck.Anakin_Deck;

public interface Anakin_ReadOnlyAnakin {
  ObservableList<Anakin_Deck> getDeckList();
  ObservableList<Anakin_Card> getCardList(Anakin_Deck deck);
}
