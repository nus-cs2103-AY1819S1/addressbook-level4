package seedu.address.storage.entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EntryDescription;

public class XmlAdaptedResumeEntryDescriptionTest {

    private static final List<String> VALID_DESCRIPTION = Arrays.asList("Bullet1 !@^$%, Bullet2    ");
    private static final List<String> EMPTY_BULLET = Arrays.asList("Bullet1", "");
    private static final List<String> WHITESPACE_BULLET = Arrays.asList("Bullet1", "       ");


    @Test
    public void toModelType_validEntryDescription() throws Exception {

        XmlAdaptedEntryDescription entryDescription = new XmlAdaptedEntryDescription(VALID_DESCRIPTION);
        EntryDescription actual = entryDescription.toModelType();

        EntryDescription expected = new EntryDescription();
        VALID_DESCRIPTION.forEach(bullet -> expected.addBullet(bullet));

        assertEquals(actual, expected);

    }

    @Test
    public void toModelType_emptyBullet_throwsIllegalValueException() {
        XmlAdaptedEntryDescription entryDescription = new XmlAdaptedEntryDescription(EMPTY_BULLET);

        assertThrows(IllegalValueException.class, EntryDescription.MESSAGE_ENTRYDESC_CONSTRAINTS,
                             entryDescription::toModelType);
    }

    @Test
    public void toModelType_whitespaceBullet_throwsIllegalValueException() {
        XmlAdaptedEntryDescription entryDescription = new XmlAdaptedEntryDescription(WHITESPACE_BULLET);

        assertThrows(IllegalValueException.class, EntryDescription.MESSAGE_ENTRYDESC_CONSTRAINTS,
                             entryDescription::toModelType);
    }

    @Test
    public void toModelType_nullBulletList_throwsIllegalValueException() {

        List<String> bullets = null;

        XmlAdaptedEntryDescription entryDescription = new XmlAdaptedEntryDescription(bullets);

        assertThrows(IllegalValueException.class, XmlAdaptedEntryDescription.MESSAGE_MISSING_BULLETS,
                             entryDescription::toModelType);
    }


}
