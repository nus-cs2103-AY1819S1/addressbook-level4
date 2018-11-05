package ssp.scheduleplanner.storage;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ssp.scheduleplanner.logic.commands.FirstDayCommand;

public class XmlSerializableRangeOfWeekTest {
    private static final int WEEKS_IN_SEMESTER = 17;
    private static final String USER_INPUT_DATE = "130818";
    private String[][] rangeOfWeeks = new String[WEEKS_IN_SEMESTER][3];
    private String[][] checkRangeOfWeeks = new String[WEEKS_IN_SEMESTER][3];

    @Before
    public void setup() {
        rangeOfWeeks[0][0] = "130818";
        rangeOfWeeks[0][1] = "190818";
        rangeOfWeeks[0][2] = "Week 1";
        rangeOfWeeks[1][0] = "200818";
        rangeOfWeeks[1][1] = "260818";
        rangeOfWeeks[1][2] = "Week 2";
        rangeOfWeeks[2][0] = "270818";
        rangeOfWeeks[2][1] = "020918";
        rangeOfWeeks[2][2] = "Week 3";
        rangeOfWeeks[3][0] = "030918";
        rangeOfWeeks[3][1] = "090918";
        rangeOfWeeks[3][2] = "Week 4";
        rangeOfWeeks[4][0] = "100918";
        rangeOfWeeks[4][1] = "160918";
        rangeOfWeeks[4][2] = "Week 5";
        rangeOfWeeks[5][0] = "170918";
        rangeOfWeeks[5][1] = "230918";
        rangeOfWeeks[5][2] = "Week 6";
        rangeOfWeeks[6][0] = "240918";
        rangeOfWeeks[6][1] = "300918";
        rangeOfWeeks[6][2] = "Recess Week";
        rangeOfWeeks[7][0] = "011018";
        rangeOfWeeks[7][1] = "071018";
        rangeOfWeeks[7][2] = "Week 7";
        rangeOfWeeks[8][0] = "081018";
        rangeOfWeeks[8][1] = "141018";
        rangeOfWeeks[8][2] = "Week 8";
        rangeOfWeeks[9][0] = "151018";
        rangeOfWeeks[9][1] = "211018";
        rangeOfWeeks[9][2] = "Week 9";
        rangeOfWeeks[10][0] = "221018";
        rangeOfWeeks[10][1] = "281018";
        rangeOfWeeks[10][2] = "Week 10";
        rangeOfWeeks[11][0] = "291018";
        rangeOfWeeks[11][1] = "041118";
        rangeOfWeeks[11][2] = "Week 11";
        rangeOfWeeks[12][0] = "051118";
        rangeOfWeeks[12][1] = "111118";
        rangeOfWeeks[12][2] = "Week 12";
        rangeOfWeeks[13][0] = "121118";
        rangeOfWeeks[13][1] = "181118";
        rangeOfWeeks[13][2] = "Week 13";
        rangeOfWeeks[14][0] = "191118";
        rangeOfWeeks[14][1] = "251118";
        rangeOfWeeks[14][2] = "Study Week";
        rangeOfWeeks[15][0] = "261118";
        rangeOfWeeks[15][1] = "021218";
        rangeOfWeeks[15][2] = "Examination Week";
        rangeOfWeeks[16][0] = "031218";
        rangeOfWeeks[16][1] = "091218";
        rangeOfWeeks[16][2] = "Examination Week";
    }

    @Test
    public void equal_assertTrue() {
        FirstDayCommand fdc = new FirstDayCommand();
        checkRangeOfWeeks = fdc.computeRangeOfWeeks(USER_INPUT_DATE);
        XmlSerializableRangeOfWeek checkSerializableRangeOfWeek = new XmlSerializableRangeOfWeek(checkRangeOfWeeks);
        XmlSerializableRangeOfWeek serializableRangeOfWeek = new XmlSerializableRangeOfWeek(rangeOfWeeks);
        assertTrue(serializableRangeOfWeek.equals(checkSerializableRangeOfWeek));
        assertTrue(checkSerializableRangeOfWeek.equals(serializableRangeOfWeek));
    }

    @Test
    public void toString_assertTrue() {
        FirstDayCommand fdc = new FirstDayCommand();
        checkRangeOfWeeks = fdc.computeRangeOfWeeks(USER_INPUT_DATE);
        XmlSerializableRangeOfWeek checkSerializableRangeOfWeek = new XmlSerializableRangeOfWeek(checkRangeOfWeeks);
        XmlSerializableRangeOfWeek serializableRangeOfWeek = new XmlSerializableRangeOfWeek(rangeOfWeeks);
        assertTrue(checkSerializableRangeOfWeek.toString().equals(serializableRangeOfWeek.toString()));
        assertTrue(serializableRangeOfWeek.toString().equals(checkSerializableRangeOfWeek.toString()));
    }

}
