package seedu.parking.commons.util;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GsonUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fetchCarparkTest() {
        try {
            assertNotNull(GsonUtil.fetchAllCarparkInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchIndivudlaCarparkPostalCode () throws IOException {
        //System.out.println("test: " + getCarparkPostalData("15758.7046", "34458.9721"));
        //assertEquals("610138", getCarparkPostalData("15758.7046", "34458.9721"));
    }
}
