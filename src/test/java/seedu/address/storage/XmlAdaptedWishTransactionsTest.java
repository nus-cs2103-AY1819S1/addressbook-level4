package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.*;

public class XmlAdaptedWishTransactionsTest {
    private XmlAdaptedWishTransactions xmlAdaptedWishTransactions;

    private Wish wish1;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        xmlAdaptedWishTransactions = new XmlAdaptedWishTransactions();
        Set<Tag> tagSet = new HashSet<>();
        ((HashSet) tagSet).add(new Tag("wish1"));

        this.wish1 = new Wish(new Name("wish1"),
                new Phone("81320902"),
                new Email("wish1@gmail.com"),
                new Address("Blk 102 Simei Avenue"),
                new Remark("e"),
                tagSet);
    }

    @Test
    public void addWishToTransactionMap_success() {
        xmlAdaptedWishTransactions.addWish(wish1);
        assertTrue(xmlAdaptedWishTransactions.getWishMap().containsKey(getKey(wish1)));
    }

    @Test
    public void getWishList_success() throws IllegalValueException {
        xmlAdaptedWishTransactions.addWish(wish1);
        assertFalse(xmlAdaptedWishTransactions.toCurrentStateWishTransactionList().isEmpty());
    }

    @Test
    public void removeWishFromTransactionMap_success() {
        xmlAdaptedWishTransactions.getWishMap().clear();
        xmlAdaptedWishTransactions.addWish(wish1);
        xmlAdaptedWishTransactions.remove(wish1);
        assertTrue(xmlAdaptedWishTransactions.getWishMap().isEmpty());
    }

    private String getKey(Wish wish) {
        return wish.getName().fullName;
    }
}
