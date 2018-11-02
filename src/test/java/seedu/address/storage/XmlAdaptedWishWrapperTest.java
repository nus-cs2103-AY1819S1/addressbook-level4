package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWishes.getTypicalWishes;

import java.util.LinkedList;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

/**
 * This class performs a unit test on {@code XmlAdaptedWishWrapper} class.
 */
public class XmlAdaptedWishWrapperTest {

    private XmlAdaptedWishWrapper emptyWrapper;
    private XmlAdaptedWishWrapper populatedWrapper;

    private LinkedList<XmlAdaptedWish> xmlAdaptedWishes;

    @Before
    public void init() {
        this.xmlAdaptedWishes = getTypicalWishes().stream()
                .map(wish -> new XmlAdaptedWish(wish))
                .collect(Collectors.toCollection(LinkedList::new));
        this.populatedWrapper = new XmlAdaptedWishWrapper(xmlAdaptedWishes);
        this.emptyWrapper = new XmlAdaptedWishWrapper(new LinkedList<>());
    }

    @Test(expected = NullPointerException.class)
    public void createWrapperShouldThrowException() {
        new XmlAdaptedWishWrapper(null);
    }

    @Test
    public void shouldRetrieveXmlAdaptedWishes() {
        assertEquals(emptyWrapper.getXmlAdaptedWishes(), new LinkedList<>());
        assertEquals(populatedWrapper.getXmlAdaptedWishes(), xmlAdaptedWishes);
    }

    @Test
    public void shouldBeEqual() {
        assertTrue(emptyWrapper.equals(new XmlAdaptedWishWrapper()));
        assertTrue(populatedWrapper.equals(new XmlAdaptedWishWrapper(xmlAdaptedWishes)));
    }
}
