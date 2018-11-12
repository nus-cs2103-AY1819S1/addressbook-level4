package seedu.parking.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GsonUtilTest extends GsonUtil {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fetchAllCarparkInfo_getAllCarparks_notNullReturned() throws Exception {
        assertNotNull(fetchAllCarparkInfo());
    }

    @Test
    public void getSelectedCarparkInfo_getSelectedCarpark_notNullReturned() throws Exception {
        assertNotNull(getSelectedCarparkInfo("TJ39"));
    }

    @Test
    public void getSelectedCarparkInfo_getEmptyList_notNullReturned() throws Exception {
        assertEquals(getSelectedCarparkInfo(""), new ArrayList<String>());
    }

    @Test
    public void fetchIndivudlaCarparkPostalCode () throws IOException {
        //System.out.println("test: " + getCarparkPostalData("15758.7046", "34458.9721"));
        //assertEquals("610138", getCarparkPostalData("15758.7046", "34458.9721"));
    }
}
