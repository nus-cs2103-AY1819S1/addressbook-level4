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

public class XmlWishTransactionsTest {
    private XmlWishTransactions xmlWishTransactions;

    private Wish wish1;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        xmlWishTransactions = new XmlWishTransactions();
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
        xmlWishTransactions.addWish(wish1);
        assertTrue(xmlWishTransactions.getWishMap().containsKey(getKey(wish1)));
    }

    @Test
    public void getWishList_success() throws IllegalValueException {
        xmlWishTransactions.addWish(wish1);
        assertFalse(xmlWishTransactions.toCurrentStateWishTransactionList().isEmpty());
    }

    @Test
    public void removeWishFromTransactionMap_success() {
        xmlWishTransactions.getWishMap().clear();
        xmlWishTransactions.addWish(wish1);
        xmlWishTransactions.remove(wish1);
        assertTrue(xmlWishTransactions.getWishMap().isEmpty());
    }

    private String getKey(Wish wish) {
        return wish.getName().fullName;
    }
}
