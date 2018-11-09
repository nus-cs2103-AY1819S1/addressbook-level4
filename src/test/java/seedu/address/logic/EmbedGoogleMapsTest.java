package seedu.address.logic;

import seedu.address.testutil.Assert;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmbedGoogleMapsTest {

    @Test
    void invalidFacultyLocation_getPlaceId() {
        assertEquals(null, EmbedGoogleMaps.getPlaceId("arts")); // not a conventional faculty name
        assertEquals(null, EmbedGoogleMaps.getPlaceId("soc")); // lowercases should not be accepted
        assertEquals(null, EmbedGoogleMaps.getPlaceId("-"));
        assertEquals(null, EmbedGoogleMaps.getPlaceId("FOS+"));
    }

    @Test
    void validFacultyLocation_getPlaceId() {
        assertEquals("ChIJrY4IlPka2jERhqeUbnzFVKY", EmbedGoogleMaps.getPlaceId("SDE"));
        assertEquals("ChIJfyddcgMa2jER6C0WKzLdP6w", EmbedGoogleMaps.getPlaceId("FOL"));
        assertEquals("ChIJSejKW_Ya2jER9bHfrr9sA-c", EmbedGoogleMaps.getPlaceId("YSTCOM"));
        assertEquals("ChIJx_aDLFEa2jERvhbP1ACc2t8", EmbedGoogleMaps.getPlaceId("YLLSOM"));
    }

    @Test
    void getMeetingPlaceId() {
    }
}