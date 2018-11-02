package seedu.parking.commons.util;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.parking.commons.util.GsonUtil.fetchAllCarparkInfo;
//import static seedu.parking.commons.util.GsonUtil.getCarparkPostalData;

import java.io.IOException;

import org.junit.Test;

public class GsonUtilTest {

    @Test
    public void fetchCarparkTest () throws Exception {
        assertNotNull(fetchAllCarparkInfo());
    }

    @Test
    public void fetchIndivudlaCarparkPostalCode () throws IOException {
        //System.out.println("test: " + getCarparkPostalData("15758.7046", "34458.9721"));
        //assertEquals("610138", getCarparkPostalData("15758.7046", "34458.9721"));
    }
}
