package seedu.parking.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Converts JSON from API call to a Java Object
 */
public class GsonUtil {
    private static HashSet<CarparkJson> carparkList = new HashSet<>();
    private static HashSet<String[]> parkingData = new HashSet<>();

    /**
     * Fetches car park information and returns a list of it.
     * @return A list of list of strings containing the car park information.
     * @throws IOException if unable to connect to URL.
     */
    public static List<List<String>> fetchCarparkInfo() throws Exception {
        final boolean[] hasError = {false, false, false};

        Thread first = new Thread(() -> {
            try {
                getCarparkData();
            } catch (IOException e) {
                hasError[0] = true;
            }
        });
        first.start();

        if (hasError[0]) {
            throw new IOException();
        }

        Thread second = new Thread(() -> {
            try {
                getCarparkAvailability();
            } catch (IOException e) {
                hasError[1] = true;
            }
        });
        second.start();

        if (hasError[1]) {
            throw new IOException();
        }

        first.join();
        second.join();

        return saveAsList();
    }

    /**
     * Adds in the parking lots details and convert to a list.
     * @return A List containing all the car parks information.
     */
    private static List<List<String>> saveAsList() {
        List<List<String>> str = new ArrayList<>();

        for (CarparkJson list : carparkList) {
            for (String[] data : parkingData) {
                if (list.getNumber().contains(data[0])) {
                    list.addOn(data[1], data[2]);
                    break;
                } else {
                    list.addOn("0", "0");
                }
            }
            str.add(list.jsonData);
        }

        return str;
    }

    private static void getCarparkAvailability() throws IOException {
        String url = "https://api.data.gov.sg/v1/transport/carpark-availability";
        URL link = new URL(url);
        URLConnection communicate = link.openConnection();
        communicate.setConnectTimeout(30000);
        communicate.setReadTimeout(30000);
        communicate.connect();

        InputStreamReader in = new InputStreamReader((InputStream) communicate.getContent());

        JsonArray array = new JsonParser()
                .parse(in)
                .getAsJsonObject()
                .getAsJsonArray("items")
                .get(0)
                .getAsJsonObject()
                .getAsJsonArray("carpark_data");

        for (int i = 0; i < array.size(); i++) {
            JsonObject carObject = array.get(i).getAsJsonObject();
            String[] carparkNumber = carObject.get("carpark_number")
                    .toString()
                    .split("\"");

            JsonObject carInfo = carObject.getAsJsonArray("carpark_info")
                    .get(0)
                    .getAsJsonObject();

            String[] totalLot = carInfo.get("total_lots")
                    .toString()
                    .split("\"");

            String[] lotAvail = carInfo.get("lots_available")
                    .toString()
                    .split("\"");

            String[] lotData = {carparkNumber[1], totalLot[1], lotAvail[1]};
            parkingData.add(lotData);
        }

        in.close();
    }

    private static void getCarparkData() throws IOException {
        String urlHalf = "https://data.gov.sg/api/action/datastore_search?"
                + "resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&limit=1000&offset=";

        int offset = 0;
        StringBuilder urlFull = new StringBuilder();
        urlFull.append(urlHalf).append(offset);

        InputStreamReader in;
        JsonArray array;
        Gson gson = new Gson();

        do {
            URL link = new URL(urlFull.toString());
            URLConnection communicate = link.openConnection();
            communicate.setConnectTimeout(30000);
            communicate.setReadTimeout(30000);
            communicate.connect();

            in = new InputStreamReader((InputStream) communicate.getContent());

            array = new JsonParser()
                    .parse(in)
                    .getAsJsonObject()
                    .getAsJsonObject("result")
                    .getAsJsonArray("records");

            for (int i = 0; i < array.size(); i++) {
                JsonElement object = array.get(i);
                CarparkJson cPark = gson.fromJson(object.toString(), CarparkJson.class);
                carparkList.add(cPark);
            }

            offset += 1000;
            urlFull.setLength(0);
            urlFull.append(urlHalf).append(offset);
        } while (array.size() > 0);

        in.close();
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

        private List<String> jsonData;

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

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof CarparkJson)) {
                return false;
            }

            CarparkJson otherCarparkJson = (CarparkJson) other;
            return short_term_parking.equals(otherCarparkJson.short_term_parking)
                    && y_coord.equals(otherCarparkJson.y_coord)
                    && car_park_type.equals(otherCarparkJson.car_park_type)
                    && x_coord.equals(otherCarparkJson.x_coord)
                    && free_parking.equals(otherCarparkJson.free_parking)
                    && night_parking.equals(otherCarparkJson.night_parking)
                    && address.equals(otherCarparkJson.address)
                    && car_park_no.equals(otherCarparkJson.car_park_no)
                    && type_of_parking_system.equals(otherCarparkJson.type_of_parking_system);
        }

        @Override
        public int hashCode() {
            return Objects.hash(short_term_parking, y_coord, car_park_type, x_coord, free_parking, night_parking,
                    address, car_park_no, type_of_parking_system);
        }
    }
}
