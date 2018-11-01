package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalWishes.getTypicalWishTransaction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WishTransaction;

public class XmlWishTransactionsTest extends XmlWishTransactions {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private XmlWishTransactions emptyXmlWishTransactions;
    private XmlWishTransactions populatedXmlWishTransactions;
    private WishTransaction wishTransaction;

    @Before
    public void init() {
        this.wishTransaction = getTypicalWishTransaction();
        this.emptyXmlWishTransactions = new XmlWishTransactions();
        this.populatedXmlWishTransactions = new XmlWishTransactions(wishTransaction);
    }

    @Test
    public void shouldConvertCorrectlyXmlWishTransactions() throws IllegalValueException {
        WishTransaction wishTransaction = emptyXmlWishTransactions.toModelType();
        assertEquals(wishTransaction, new WishTransaction());
        assertEquals(populatedXmlWishTransactions.toModelType(), wishTransaction);

        WishTransaction typicalWishTransaction = getTypicalWishTransaction();
        XmlWishTransactions xmlWishTransactions = new XmlWishTransactions(typicalWishTransaction);
        assertEquals(xmlWishTransactions.toModelType(), typicalWishTransaction);
    }

    @Test
    public void shouldBeEqual() {
        assertTrue(emptyXmlWishTransactions.equals(new XmlWishTransactions()));
        assertTrue(populatedXmlWishTransactions.equals(new XmlWishTransactions(getTypicalWishTransaction())));
    }

}
