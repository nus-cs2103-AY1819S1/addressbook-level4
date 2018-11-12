package seedu.parking.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

public class GsonUtilTest extends GsonUtil {

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
}
