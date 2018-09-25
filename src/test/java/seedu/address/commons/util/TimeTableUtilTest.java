package seedu.address.commons.util;

import java.text.ParseException;

public class TimeTableUtilTest {


    /*
     * Test Line
     * edit 1 tt/ http://modsn.us/H4v8s
     */

    @org.junit.Test
    public void parseShortURL() throws ParseException {
        String shortlink = "http://modsn.us/H4v8s";
        String actuallonglink = "https://nusmods.com/timetable/sem-1/share?CS2102=LEC:1,TUT:11&CS2103=LEC:1,TUT:01&CS2105=LEC:1,TUT:16&CS2106=LAB:09,LEC:1,TUT:09&MA1521=LEC:1,TUT:4&UCV2209=SEM:01";
        assert actuallonglink.equals(TimeTableUtil.parseShortUrl(shortlink));
    }

    @org.junit.Test
    public void parseLongURL() {
    }

    @org.junit.Test
    public void parseModule() {
    }

    @org.junit.Test
    public void obtainModuleInfoFromApi() {
    }
}
