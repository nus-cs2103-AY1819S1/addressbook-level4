package seedu.address.commons.util;

import java.text.ParseException;

public class TimeTableUtilTest {


    /*
     * Test Line
     * edit 1 tt/ http://modsn.us/H4v8s
     */

    private String shortlink = "http://modsn.us/H4v8s";
    private String actuallonglink = "https://nusmods.com/timetable/sem-1/" +
            "share?CS2102=LEC:1,TUT:11&CS2103=LEC:1,TUT:01&CS2105=LEC:1,TUT:16" +
            "&CS2106=LAB:09,LEC:1,TUT:09&MA1521=LEC:1,TUT:4&UCV2209=SEM:01";
    private String scheduleString = "0000000000000000111100000000111100000000000" +
        "00000000000000000000000000000111111110000000000000000000000000000000000001100000" +
        "0111100000000000000000000000000000000000000001111000011" +
            "11000000000000000000000000000000111100" +
        "001111111111000000000000000000000000000000000000000" +
            "000000000000000000000000000000000000000000000000000000000000000000000";


    @org.junit.Test
    public void parseShortUrl() throws ParseException {
        assert actuallonglink.equals(TimeTableUtil.parseShortUrl(shortlink));
    }

//    @org.junit.Test
//    public void parseLongUrl() {
//    }
//
//    @org.junit.Test
//    public void parseModule() {
//    }
//
//    @org.junit.Test
//    public void obtainModuleInfoFromApi() {
//
//    }

    @org.junit.Test
    public void convertToSchedule() throws ParseException {
       assert(TimeTableUtil.parseUrl(shortlink).convertToSchedule().valueToString().equals(scheduleString));
    }
}
