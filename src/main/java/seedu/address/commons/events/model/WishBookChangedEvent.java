package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyWishBook;
import seedu.address.model.WishTransaction;

/** Indicates the WishBook in the model has changed*/
public class WishBookChangedEvent extends BaseEvent {

    public final ReadOnlyWishBook data;
    public final WishTransaction wishTransaction;

    public WishBookChangedEvent(ReadOnlyWishBook readOnlyWishBook, WishTransaction wishTransaction) {
        this.data = readOnlyWishBook;
        this.wishTransaction = wishTransaction;
    }

    @Override
    public String toString() {
        return "number of wishes " + data.getWishList().size();
    }
}
