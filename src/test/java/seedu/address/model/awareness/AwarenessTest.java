package seedu.address.model.awareness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.TreeMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.SampleDataUtil;

public class AwarenessTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Dictionary dictionary = null;
        TreeMap<String, ResumeEntry> nameToEntryMappings = null;

        new Awareness(dictionary, nameToEntryMappings);
    }

    @Test
    public void getContextualResumeEntry_positiveCases() {

        Awareness awareness = SampleDataUtil.getSampleAwareness();

        // correct event name, where event name consists of a few full phrases --> match
        String multipleFullPhrases = "teaching assistant ma1101r";
        assertEquals(awareness.getContextualResumeEntry(multipleFullPhrases).get(), SampleDataUtil.MA1101R_TA);

        // correct event name, where event name is a single full phrase --> match
        String singleFullPhrase = "cs2103t";
        assertEquals(awareness.getContextualResumeEntry(singleFullPhrase).get(), SampleDataUtil.NUS_CS2103T);

    }

    @Test
    public void getContextualResumeEntry_negativeCases() {

        Awareness awareness = SampleDataUtil.getSampleAwareness();

        // incorrect event names --> no match
        String incorrectEventName = "teching assistant ma1101r";
        assertFalse(awareness.getContextualResumeEntry(incorrectEventName).isPresent());

        // incomplete event name --> no match
        String incompleteEventName = "cs2103";
        assertFalse(awareness.getContextualResumeEntry(incompleteEventName).isPresent());

        // event name given in wrong case --> no match
        String wrongCaseEventName = "CS2103T";
        assertFalse(awareness.getContextualResumeEntry(wrongCaseEventName).isPresent());
    }

    @Test
    public void equals() {

        Dictionary sampleDictionary = SampleDataUtil.getSampleDictionary();
        Dictionary emptyDictionary = new Dictionary();

        TreeMap<String, ResumeEntry> sampleNameToEntryMappings = SampleDataUtil.makeNameToEntryMappings();
        TreeMap<String, ResumeEntry> emptyNameToEntryMappings = new TreeMap<String, ResumeEntry>();

        Awareness sampleAwareness = SampleDataUtil.getSampleAwareness();
        Awareness sampleAwarenessDuplicate = new Awareness(sampleDictionary, sampleNameToEntryMappings);

        // same object --> true
        assertEquals(sampleAwareness, sampleAwareness);

        // same mappings in both dictionary and treemap --> true
        assertEquals(sampleAwareness, sampleAwarenessDuplicate);

        // not an instance of Awareness --> false
        assertNotEquals(sampleAwareness, "a string");

        // different mappings in dictionary, same mappings in treemap --> false
        assertNotEquals(sampleAwareness, new Awareness(emptyDictionary, sampleNameToEntryMappings));

        // same mappings in dictionary, different mappings treemap --> false
        assertNotEquals(sampleAwareness, new Awareness(sampleDictionary, emptyNameToEntryMappings));

    }

}
