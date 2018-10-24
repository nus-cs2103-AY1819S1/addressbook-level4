package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class SlotTest {

    @Test
    void testMethodsS() {
        Slot slot = new Slot();
        slot.setDay("tuesday");
        slot.setTime("0800");
        assertTrue (slot.getDay().equals("tuesday"));
        assertTrue (slot.getTime().equals("0800"));
    }
}
