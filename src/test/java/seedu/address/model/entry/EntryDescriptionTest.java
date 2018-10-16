package seedu.address.model.entry;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * test class for EntryDescription.
 */
public class EntryDescriptionTest {

    /**
     *
     * @return a default instance of EntryDescription
     */
    public EntryDescription createDefualt() {
        EntryDescription description = new EntryDescription();
        description.addBullet("this is a line of description");
        description.addBullet("this is another line of description");
        return description;
    }

    @Test
    public void toStringAndEquals() {
        EntryDescription description = createDefualt();
        assertTrue(createDefualt().equals(createDefualt()));
        System.out.println(description);
        assertTrue(createDefualt().toString()
                .equals("this is a line of description\nthis is another line of description\n"));
    }
}
