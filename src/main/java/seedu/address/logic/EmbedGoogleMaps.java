package seedu.address.logic;

/**
 * Stores the logic related to the Embedded Google Maps API.
 */

public class EmbedGoogleMaps {

    private static final String socPlaceId = "ChIJW-fkx_ga2jERSjkkKeJjaUM";
    private static final String fosPlaceId = "ChIJSfKZ6VYa2jERnuNsDKZ8moU";
    private static final String yllsomPlaceId = "ChIJx_aDLFEa2jERvhbP1ACc2t8";
    private static final String fodPlaceId = "ChIJcUayaFEa2jERVJ-bhaQxjmk";
    private static final String bizPlaceId = "ChIJk315cv8a2jERyoLIHsKS40Y";
    private static final String sdePlaceId = "ChIJrY4IlPka2jERhqeUbnzFVKY";
    private static final String foePlaceId = "ChIJQ91DPvca2jERARhJO1i77Cg";
    private static final String folPlaceId = "ChIJfyddcgMa2jER6C0WKzLdP6w";
    private static final String ystcomPlaceId = "ChIJSejKW_Ya2jER9bHfrr9sA-c";
    private static final String fassPlaceId = "ChIJadpVLvka2jERHq8cT_xdGVQ";
    private static final String yihPlaceId = "ChIJcWXf-_ca2jERnQ81RhxHf5o";

    /**
     * This method returns the faculty location's Google Place ID.
     * @param faculty The faculty that the person is from
     * @return Google's PlaceId of the faculty location
     */

    public static String getPlaceId(String faculty) {
        String placeId = null;

        if (faculty.equals("SOC")) {
            placeId = socPlaceId;
        } else if (faculty.equals("FOS")) {
            placeId = fosPlaceId;
        } else if (faculty.equals("YLLSOM")) {
            placeId = yllsomPlaceId;
        } else if (faculty.equals("FOD")) {
            placeId = fodPlaceId;
        } else if (faculty.equals("BIZ")) {
            placeId = bizPlaceId;
        } else if (faculty.equals("SDE")) {
            placeId = sdePlaceId;
        } else if (faculty.equals("FOE")) {
            placeId = foePlaceId;
        } else if (faculty.equals("FOL")) {
            placeId = folPlaceId;
        } else if (faculty.equals("YSTCOM")) {
            placeId = ystcomPlaceId;
        } else if (faculty.equals("FASS")) {
            placeId = fassPlaceId;
        }

        return placeId;
    }

    // TODO to replace this with a random generator of placeId with an assortment of places
    public static String getYihPlaceId() {
        return yihPlaceId;
    }
}
