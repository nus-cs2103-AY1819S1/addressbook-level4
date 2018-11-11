package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

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
        assertEquals("ChIJW-fkx_ga2jERSjkkKeJjaUM", EmbedGoogleMaps.getPlaceId("SOC"));
        assertEquals("ChIJSfKZ6VYa2jERnuNsDKZ8moU", EmbedGoogleMaps.getPlaceId("FOS"));
        assertEquals("ChIJQ91DPvca2jERARhJO1i77Cg", EmbedGoogleMaps.getPlaceId("FOE"));
        assertEquals("ChIJk315cv8a2jERyoLIHsKS40Y", EmbedGoogleMaps.getPlaceId("BIZ"));
        assertEquals("ChIJadpVLvka2jERHq8cT_xdGVQ", EmbedGoogleMaps.getPlaceId("FASS"));
        assertEquals("ChIJcUayaFEa2jERVJ-bhaQxjmk", EmbedGoogleMaps.getPlaceId("FOD"));

    }

    @Test
    void getMeetingPlaceId() {
        assertNotEquals(null, EmbedGoogleMaps.getMeetingPlaceId()); // a null value should never be returned
    }
}
