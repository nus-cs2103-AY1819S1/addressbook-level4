package seedu.address.logic.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.ContextUpdateEvent;
import seedu.address.model.EntryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.util.SampleDataUtil;

public class AwarenessServiceTest {
    private static final Awareness typicalAwareness = SampleDataUtil.getSampleAwareness();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullModel() {
        thrown.expect(NullPointerException.class);
        AwarenessService awarenessService = new AwarenessService(null);
    }

    @Test
    public void observe_irrelevantInput() {

        Model model = new ModelManager();
        AwarenessService awarenessService = new AwarenessService(model);

        assertFalse(awarenessService.observe("test input").isPresent());
        assertFalse(awarenessService.observe("                ").isPresent());
        assertFalse(awarenessService.observe("       test         ").isPresent());
        assertFalse(awarenessService.observe("").isPresent());
    }


    @Test
    public void observe_relevantInput() {
        Model model = new ModelManager(new EntryBook(), new UserPrefs(), typicalAwareness);
        AwarenessService awarenessService = new AwarenessService(model);

        ContextUpdateEvent expected = new ContextUpdateEvent(String.format(AwarenessService.MESSAGE_AVAILABLE_ENTRY,
                                                                                   "teaching assistant ma1101r"));


        ContextUpdateEvent exactMatch = (ContextUpdateEvent) awarenessService.observe("nus ta ma1101r").get();

        ContextUpdateEvent trailingSpacesInput =
                (ContextUpdateEvent) awarenessService.observe("  nus ta ma1101r").get();

        ContextUpdateEvent leadingSpacesInput =
                (ContextUpdateEvent) awarenessService.observe("nus ta ma1101r    ").get();

        ContextUpdateEvent paddedSpacesInput =
               (ContextUpdateEvent) awarenessService.observe("   nus ta ma1101r    ").get();

        assertEquals(exactMatch.message, expected.message);
        assertEquals(trailingSpacesInput.message, expected.message);
        assertEquals(leadingSpacesInput.message, expected.message);
        assertEquals(paddedSpacesInput.message, expected.message);

    }

}
