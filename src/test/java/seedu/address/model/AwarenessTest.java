package seedu.address.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.util.SampleDataUtil;

public class AwarenessTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new Awareness(null, null);
    }

    @Test
    public void getPossibleEventName_postiveMatches() {
        Awareness awareness = SampleDataUtil.getSampleAwareness();

        // Expression consisting of single full phrase should match the correct possibleEventName
        assertEquals("hackathon", awareness.getPossibleEventName("hackathon"));

        // Expression consisting of multiple full phrases should match the correct possibleEventName
        assertEquals("financial technology hackathon",
                awareness.getPossibleEventName("financial technology hackathon"));

        // Expression consisting of a single partial phrase should match the correct possibleEventName
        assertEquals("computer", awareness.getPossibleEventName("comp"));
        assertEquals("science", awareness.getPossibleEventName("sci"));

        // Expression consisting of multiple partial phrases should match the correct possibleEventName
        assertEquals("computer science", awareness.getPossibleEventName("comp sci"));
        assertEquals("teaching assistant", awareness.getPossibleEventName("teach assist"));

        // Expression consisting a single slang should match the correct possibleEventName
        assertEquals("computer science", awareness.getPossibleEventName("cs"));
        assertEquals("singapore", awareness.getPossibleEventName("sg"));

        // Expression consisting of multiple slang should match the correct possibleEventName
        assertEquals("undergraduate teaching assistant", awareness.getPossibleEventName("ug ta"));

        // Expression consisting of both partial and full phrases should match correct possibleEventName
        assertEquals("financial technology research programme",
                awareness.getPossibleEventName("financial tech research prog"));

        assertEquals("double degree programme", awareness.getPossibleEventName("double deg prog"));

        // Expression consisting of both partial phrase and slang should match correct possibleEventName
        assertEquals("undergraduate research assistant", awareness.getPossibleEventName("ug res asst"));


        // Expression consisting of both full phrase and slang should match correct possibleEventName
        assertEquals("teaching assistant", awareness.getPossibleEventName("teaching asst"));
        assertEquals("financial technology hackathon", awareness.getPossibleEventName("fintech hackathon"));

        // Expression consisting of slang, partial phrase and full phrase should match correct possibleEventName
        assertEquals("undergraduate research programme", awareness.getPossibleEventName("ug research prog"));
        assertEquals("postgraduate machine learning research opportunities",
                awareness.getPossibleEventName("pg ml research opp"));

    }


}
