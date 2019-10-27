package seedu.address.model.entry;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEntryDescription.getTypicalEntryDescription;

import org.junit.Test;

/**
 * test class for EntryDescription.
 */
public class EntryDescriptionTest {

    /**
     * @return a default instance of EntryDescription
     */
    public EntryDescription createDefault() {
        EntryDescription description = new EntryDescription();
        description.addBullet("this is a line of description");
        description.addBullet("this is another line of description");
        return description;
    }

    @Test
    public void equals() {
        EntryDescription description = createDefault();
        // same bullets
        assertTrue(createDefault().equals(createDefault()));
    }

    @Test
    public void toStringTest() {
        EntryDescription description = getTypicalEntryDescription();
        //System.out.println(description);

        assertTrue(createDefault().toString()
                .equals("1. this is a line of description\n2. this is another line of description\n"));
    }
}
