package seedu.address.storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XmlAdaptedWishTransactionMapTest {
    private XmlAdaptedWishTransactionMap xmlAdaptedWishTransactionMap;

    private Wish wish1;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        xmlAdaptedWishTransactionMap = new XmlAdaptedWishTransactionMap();
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
        xmlAdaptedWishTransactionMap.addWish(wish1);
        assertTrue(xmlAdaptedWishTransactionMap.getWishMap().containsKey(getKey(wish1)));
    }

    @Test
    public void getWishList_success() throws IllegalValueException {
        xmlAdaptedWishTransactionMap.addWish(wish1);
        assertFalse(xmlAdaptedWishTransactionMap.toCurrentStateWishTransactionList().isEmpty());
    }

    @Test
    public void removeWishFromTransactionMap_success() {
        xmlAdaptedWishTransactionMap.getWishMap().clear();
        xmlAdaptedWishTransactionMap.addWish(wish1);
        xmlAdaptedWishTransactionMap.remove(wish1);
        assertTrue(xmlAdaptedWishTransactionMap.getWishMap().isEmpty());
    }

    private String getKey(Wish wish) {
        return wish.getName().fullName;
    }
}
