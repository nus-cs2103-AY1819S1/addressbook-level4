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
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

public class XmlWishTransactionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Wish wish1;
    private XmlWishTransactions xmlWishTransactions;

    @Before
    public void init() {
        xmlWishTransactions = new XmlWishTransactions();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("wish1"));
        this.wish1 = new Wish(new Name("wish1"),
                new Price("81320902"),
                new Email("wish1@gmail.com"),
                new Url("https://redmart.com/marketplace/lw-roasted-meat"),
                new SavedAmount("0"),
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
