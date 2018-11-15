package seedu.planner.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.commons.util.CollectionUtil.areEqualIgnoreOrder;
import static seedu.planner.commons.util.CollectionUtil.convertCollectionToString;
import static seedu.planner.commons.util.CollectionUtil.formatMessage;
import static seedu.planner.commons.util.CollectionUtil.getAnyOne;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CollectionUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void requireAllNonNullVarargs() {
        // no arguments
        assertNullPointerExceptionNotThrown();

        // any non-empty argument list
        assertNullPointerExceptionNotThrown(new Object(), new Object());
        assertNullPointerExceptionNotThrown("test");
        assertNullPointerExceptionNotThrown("");

        // argument lists with just one null at the beginning
        assertNullPointerExceptionThrown((Object) null);
        assertNullPointerExceptionThrown(null, "", new Object());
        assertNullPointerExceptionThrown(null, new Object(), new Object());

        // argument lists with nulls in the middle
        assertNullPointerExceptionThrown(new Object(), null, null, "test");
        assertNullPointerExceptionThrown("", null, new Object());

        // argument lists with one null as the last argument
        assertNullPointerExceptionThrown("", new Object(), null);
        assertNullPointerExceptionThrown(new Object(), new Object(), null);

        // null reference
        assertNullPointerExceptionThrown((Object[]) null);

        // confirms nulls inside lists in the argument list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(containingNull, new Object());
    }

    @Test
    public void requireAllNonNullCollection() {
        // lists containing nulls in the front
        assertNullPointerExceptionThrown(Arrays.asList((Object) null));
        assertNullPointerExceptionThrown(Arrays.asList(null, new Object(), ""));

        // lists containing nulls in the middle
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, new Object()));
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, "eggs", null, new Object()));

        // lists containing nulls at the end
        assertNullPointerExceptionThrown(Arrays.asList("spam", new Object(), null));
        assertNullPointerExceptionThrown(Arrays.asList(new Object(), null));

        // null reference
        assertNullPointerExceptionThrown((Collection<Object>) null);

        // empty list
        assertNullPointerExceptionNotThrown(Collections.emptyList());

        // list with all non-null elements
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object(), "ham", Integer.valueOf(1)));
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object()));

        // confirms nulls inside nested lists are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(Arrays.asList(containingNull, new Object()));
    }

    @Test
    public void isAnyNonNull() {
        assertFalse(CollectionUtil.isAnyNonNull());
        assertFalse(CollectionUtil.isAnyNonNull((Object) null));
        assertFalse(CollectionUtil.isAnyNonNull((Object[]) null));
        assertTrue(CollectionUtil.isAnyNonNull(new Object()));
        assertTrue(CollectionUtil.isAnyNonNull(new Object(), null));
    }

    @Test
    public void getAnyOne_nonEmptyCollection_returnAnyOneElement() {
        Set<Integer> set = Set.of(1, 2);
        Optional<Integer> one = Optional.of(1);
        Optional<Integer> two = Optional.of(2);
        Optional<Integer> any = getAnyOne(set);
        assertTrue(one.equals(any) || two.equals(any));
    }

    @Test
    public void getAnyOne_emptyCollection_returnEmptyOptional() {
        assertEquals(Optional.empty(), getAnyOne(Set.of()));
    }

    @Test
    public void areEqualIgnoreOrder_orderedIdenticalCollections_success() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);
        assertTrue(areEqualIgnoreOrder(list1, list2));
    }

    @Test
    public void areEqualIgnoreOrder_unorderedIdenticalCollections_success() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(2, 1);
        assertTrue(areEqualIgnoreOrder(list1, list2));
    }

    @Test
    public void areEqualIgnoreOrder_differentCollections_failure() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(2, 3);
        assertFalse(areEqualIgnoreOrder(list1, list2));
    }

    @Test
    public void areEqualIgnoreOrder_emptyCollections_success() {
        assertTrue(areEqualIgnoreOrder(List.of(), Set.of()));
    }

    @Test
    public void areEqualIgnoreOrder_notIdenticalCollections_failure() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(3);
        assertFalse(areEqualIgnoreOrder(list1, list2));
    }

    @Test
    public void convertCollectionToString_nonEmptyCollection_returnsString() {
        assertEquals(convertCollectionToString(List.of(1, 2, 3, 4)), "1 2 3 4");
    }

    @Test
    public void convertCollectionToString_emptyCollection_returnsEmptyString() {
        assertEquals(convertCollectionToString(List.of()), "");
    }

    @Test
    public void convertCollectionToString_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        convertCollectionToString(null);
    }

    @Test
    public void formatMessage_nonNullArgsGiven() {
        List<Integer> list = List.of(1, 2, 3);
        assertEquals("Hello 1 2 3", formatMessage("Hello %1$s", list));
        assertEquals("Hello", formatMessage("Hello", list));
    }

    @Test
    public void formatMessage_nullMessageGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        formatMessage(null, List.of(1, 2));
    }

    @Test
    public void formatMessage_nullCollectionGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        formatMessage("Hello", null);
    }

    @Test
    public void formatMessage_allNullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        formatMessage(null, null);
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Object...)} throw {@code NullPointerException}
     * if {@code objects} or any element of {@code objects} is null.
     */
    private void assertNullPointerExceptionThrown(Object... objects) {
        try {
            requireAllNonNull(objects);
            throw new AssertionError("The expected NullPointerException was not thrown.");
        } catch (NullPointerException npe) {
            // expected behavior
        }
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Collection<?>)} throw {@code NullPointerException}
     * if {@code collection} or any element of {@code collection} is null.
     */
    private void assertNullPointerExceptionThrown(Collection<?> collection) {
        try {
            requireAllNonNull(collection);
            throw new AssertionError("The expected NullPointerException was not thrown.");
        } catch (NullPointerException npe) {
            // expected behavior
        }
    }

    private void assertNullPointerExceptionNotThrown(Object... objects) {
        requireAllNonNull(objects);
    }

    private void assertNullPointerExceptionNotThrown(Collection<?> collection) {
        requireAllNonNull(collection);
    }
}
