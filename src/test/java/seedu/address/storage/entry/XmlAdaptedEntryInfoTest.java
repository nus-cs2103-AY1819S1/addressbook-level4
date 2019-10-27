package seedu.address.storage.entry;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EntryInfo;

public class XmlAdaptedEntryInfoTest {

    private static final String VALID_TITLE = "Data Science Intern";
    private static final String VALID_SUBHEADER = "Visualised traffic congestion patterns";
    private static final String VALID_DURATION = "Summer 2011";

    private static final String INVALID_SYMBOLS = "Data Science Intern! @ Google";
    private static final String EMPTY_STRING = "";
    private static final String WHITESPACE = "    ";

    @Test
    public void toModelType_validEntryInfoDetails() throws Exception {

        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(VALID_TITLE, VALID_SUBHEADER, VALID_DURATION);

        EntryInfo actual = entryInfo.toModelType();
        EntryInfo expected = new EntryInfo(VALID_TITLE, VALID_SUBHEADER, VALID_DURATION);
        assertEquals(actual, expected);

    }

    @Test
    public void toModelType_emptyTitle_throwsIllegalValueException() {

        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(EMPTY_STRING, VALID_SUBHEADER, VALID_DURATION);
        String expectedMessage = EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, entryInfo::toModelType);
    }

    @Test
    public void toModelType_whitespaceTitle_throwsIllegalValueException() {
        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(WHITESPACE, EMPTY_STRING, VALID_DURATION);
        String expectedMessage = EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, entryInfo::toModelType);
    }

    @Test
    public void toModelType_invalidSymbols_throwsIllegalValueException() {
        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(INVALID_SYMBOLS, EMPTY_STRING, VALID_DURATION);
        String expectedMessage = EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, entryInfo::toModelType);
    }

    @Test
    public void toModelType_emptySubheader_throwsIllegalValueException() {

        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(VALID_TITLE, EMPTY_STRING, VALID_DURATION);
        String expectedMessage = EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, entryInfo::toModelType);
    }

    @Test
    public void toModelType_whitespaceSubheader_throwsIllegalValueException() {
        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(VALID_TITLE, WHITESPACE, VALID_DURATION);
        String expectedMessage = EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, entryInfo::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        XmlAdaptedEntryInfo entryInfo = new XmlAdaptedEntryInfo(VALID_TITLE, EMPTY_STRING, null);
        assertThrows(IllegalValueException.class, entryInfo::toModelType);
    }

}
