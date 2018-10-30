package seedu.parking.commons.util;

/**
 * Container class to hold Geocode Information JSON data.
 */
public class GeocodeInfoJson {
    //CHECKSTYLE.OFF: MemberNameCheck
    //CHECKSTYLE.OFF: AbbreviationAsWordInNameCheck
    public final String BUILDINGNAME;
    public final String BLOCK;
    public final String ROAD;
    public final String POSTALCODE;
    public final String XCOORD;
    public final String YCOORD;
    public final String LATITUDE;
    public final String LONGITUDE;
    public final String LONGTITUDE;
    //CHECKSTYLE.ON: MemberNameCheck
    //CHECKSTYLE.ON: AbbreviationAsWordInNameCheck

    public GeocodeInfoJson(String... data) {
        BUILDINGNAME = data[0];
        BLOCK = data[1];
        ROAD = data[2];
        POSTALCODE = data[3];
        XCOORD = data[4];
        YCOORD = data[5];
        LATITUDE = data[6];
        LONGITUDE = data[7];
        LONGTITUDE = data[8];
    }
}
