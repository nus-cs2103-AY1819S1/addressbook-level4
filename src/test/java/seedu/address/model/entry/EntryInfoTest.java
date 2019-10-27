package seedu.address.model.entry;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.model.util.EntryBuilder;

/**
 * a class to test EntryInfo
 */
public class EntryInfoTest {

    /**
     * Builder for EntryInfo
     * @return EntryInfo with default values
     */
    public EntryInfo buildEntryInfo() {
        List<String> list = Arrays.asList(EntryBuilder.DEFAULT_TITLE, EntryBuilder.DEFAULT_CAT,
                EntryBuilder.DEFAULT_DURATION);
        return new EntryInfo(list);
    }

    @Test
    public void constructorFromListTest() {
        // correct constructor no error returned
        buildEntryInfo();
    }

    @Test
    public void gettersTest() {
        buildEntryInfo().getEntryInfo();
    }

    @Test
    public void hashCodeTest() {
        assertTrue(buildEntryInfo().hashCode() == buildEntryInfo().hashCode());
    }
}
