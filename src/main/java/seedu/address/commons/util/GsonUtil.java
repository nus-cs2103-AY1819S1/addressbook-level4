package seedu.address.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Converts JSON from API call to a Java Object
 */
public class GsonUtil {
    private static ArrayList<CarparkJson> carparkList = new ArrayList<>();

    /**
     * Fetches car park information and returns a list of it.
     * @return A list of list of strings containing the car park information.
     * @throws IOException if unable to connect to URL.
     */
    public static ArrayList<ArrayList<String>> fetchCarparkInfo() throws IOException {
        getCarparkData();
        getCarparkAvailability();

        ArrayList<ArrayList<String>> str = new ArrayList<>();
        for (CarparkJson list : carparkList) {
            if (list.jsonData == null) {
                list.addOn("0", "0");
            }
            str.add(list.jsonData);
        }

        return str;
    }

    private static void getCarparkAvailability() throws IOException {
        String url = "https://api.data.gov.sg/v1/transport/carpark-availability";
        URL obj = new URL(url);
        URLConnection con = obj.openConnection();
        con.connect();

        JsonArray test3 = new JsonParser()
                .parse(new InputStreamReader((InputStream) con.getContent()))
                .getAsJsonObject()
                .getAsJsonArray("items")
                .get(0)
                .getAsJsonObject()
                .getAsJsonArray("carpark_data");

        for (int i = 0; i < test3.size(); i++) {
            JsonObject object = test3.get(i).getAsJsonObject();
            String[] res = object.get("carpark_number")
                    .toString()
                    .split("\"");

            JsonObject test = object.getAsJsonArray("carpark_info")
                    .get(0)
                    .getAsJsonObject();

            String[] res2 = test.get("total_lots")
                    .toString()
                    .split("\"");

            String[] res3 = test.get("lots_available")
                    .toString()
                    .split("\"");

            for (CarparkJson carpark : carparkList) {
                if (carpark.getNumber().contains(res[1])) {
                    carpark.addOn(res2[1], res3[1]);
                    break;
                }
            }
        }
    }

    private static void getCarparkData() throws IOException {
        String url = "https://data.gov.sg/api/action/datastore_search?"
                + "resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&limit=2000";
        URL obj = new URL(url);
        URLConnection con = obj.openConnection();

        Gson gson = new Gson();

        JsonArray test3 = new JsonParser()
                .parse(new InputStreamReader((InputStream) con.getContent()))
                .getAsJsonObject()
                .getAsJsonObject("result")
                .getAsJsonArray("records");

        for (int i = 0; i < test3.size(); i++) {
            JsonElement object = test3.get(i);
            CarparkJson data = gson.fromJson(object.toString(), CarparkJson.class);
            carparkList.add(data);
        }
    }

    /**
     * Container class to hold JSON data.
     */
    private class CarparkJson {
        //CHECKSTYLE.OFF: MemberNameCheck
        private final String short_term_parking;
        private final String y_coord;
        private final String car_park_type;
        private final String x_coord;
        private final String free_parking;
        private final String night_parking;
        private final String address;
        private final String car_park_no;
        private final String type_of_parking_system;
        //CHECKSTYLE.ON: MemberNameCheck

        private ArrayList<String> jsonData;

        private CarparkJson(String... data) {
            short_term_parking = data[0];
            car_park_type = data[1];
            y_coord = data[2];
            x_coord = data[3];
            free_parking = data[4];
            night_parking = data[5];
            address = data[6];
            car_park_no = data[7];
            type_of_parking_system = data[8];
        }

        /**
         * Adds the JSON data into a list
         * @param data Contains total lots and lots availability numbers.
         */
        private void addOn(String... data) {
            jsonData = new ArrayList<>();

            jsonData.add(address);
            jsonData.add(car_park_no);
            jsonData.add(car_park_type);
            jsonData.add(x_coord + ", " + y_coord);
            jsonData.add(free_parking);
            jsonData.add(data[1]);
            jsonData.add(night_parking);
            jsonData.add(short_term_parking);
            jsonData.add(data[0]);
            jsonData.add(type_of_parking_system);
        }

        private String getNumber() {
            return car_park_no;
        }
    }
}
