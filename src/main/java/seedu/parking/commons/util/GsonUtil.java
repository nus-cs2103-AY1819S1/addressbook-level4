package seedu.parking.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    private static HashMap<Long, String> postalCodeMap = new HashMap<>();
    private static HashSet<String[]> parkingData = new HashSet<>();

    private static final String POSTAL_CODE_TXT = "postalcodeData.txt";

    /**
     * Fetches car park information and returns a list of it.
     * @return A list of list of strings containing the car park information.
     * @throws IOException if unable to connect to URL.
     */
    public static List<List<String>> fetchCarparkInfo() throws Exception {
        final boolean[] hasError = {false, false, false};
        loadCarparkPostalCode();

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
            String value = postalCodeMap.get(fnvHash(new String[] {list.x_coord, list.y_coord}));
            if (value == null) {
                list.jsonData.add("000000");
            } else {
                list.jsonData.add(value);
            }
            str.add(list.jsonData);
        }

        return str;
    }

    /**
     * Gets the list of car park lots and sets it in the set.
     * @throws IOException if unable to connect to URL.
     */
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

    /**
     * FNV hashes a String array.
     * @return A long which is the hash value
     */
    private static long fnvHash (String[] strings) {
        long hash = 0xCBF29CE484222325L;
        for (String s : strings) {
            hash ^= s.hashCode();
            hash *= 0x100000001B3L;
        }
        return hash;
    }

    /**
     * Outputs a hashmap to a txt file.
     * @throws IOException if unable to open file.
     */
    private static void hashmapToTxt (HashMap<Long, String> map) throws IOException {
        FileWriter fstream;
        BufferedWriter out;

        // create your filewriter and bufferedreader
        fstream = new FileWriter(POSTAL_CODE_TXT);
        out = new BufferedWriter(fstream);

        // initialize the record count
        int count = 0;

        // create your iterator for your map
        Iterator<Map.Entry<Long, String>> it = map.entrySet().iterator();

        // then use the iterator to loop through the map, stopping when we reach the
        // last record in the map or when we have printed enough records
        while (it.hasNext()) {

            // the key/value pair is stored here in pairs
            Map.Entry<Long, String> pairs = it.next();

            // since you only want the value, we only care about pairs.getValue(), which is written to out
            out.write(pairs.getKey() + "," + pairs.getValue() + "\n");

            // increment the record count once we have printed to the file
            count++;
        }
        // lastly, close the file and end
        out.close();
    }

    /**
     * Get list of car park information without available lots information.
     * @throws IOException if unable to connect to URL.
     */
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
     * Get list of car park information postal code information by first getting the list of car parks.
     * For every car park, we parse a coordinate into an API parser. The API converts SVY21 to a singapore postal code.
     * @throws IOException if unable to connect to URL.
     */
    private static void getCarparkPostalCodeData() throws IOException {
        String urlHalf = "https://data.gov.sg/api/action/datastore_search?"
                + "resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&limit=1000&offset=";

        int offset = 0;
        StringBuilder urlFull = new StringBuilder();
        urlFull.append(urlHalf).append(offset);

        InputStreamReader in;
        JsonArray array;
        Gson gson = new Gson();

        HashMap<Long, String> postalCodes = new HashMap<>();

        do {
            URL link = new URL(urlFull.toString());
            URLConnection communicate = link.openConnection();
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
                long hash = fnvHash(new String[] {cPark.x_coord, cPark.y_coord});
                String postalCode = getCarparkPostalData(cPark.x_coord, cPark.y_coord);

                postalCodes.put(hash, postalCode);
                carparkList.add(cPark);
            }

            offset += 1000;
            urlFull.setLength(0);
            urlFull.append(urlHalf).append(offset);
        } while (array.size() > 0);

        hashmapToTxt(postalCodes);
        in.close();
    }

    /**
     * Loads the list of car park from the text file. We do this due to speed.
     * As querying the API to convert every coordinate in real time is too slow and no efficient,
     * We first store it in a text file, which is our hash map of information.
     *
     * The key is the hashed x coordinate followed by y coordinate. The value is the postal code.
     * As postal code can be null, default postal code is 000000.
     * If postal code is default, we will not display it or check against it.
     * @throws IOException if unable to open file.
     */
    private static void loadCarparkPostalCode () throws IOException {
        postalCodeMap = new HashMap<>();
        File file = new File(POSTAL_CODE_TXT);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String[] splitData = st.split(",");
            if (!splitData[1].equals("null")) {
                postalCodeMap.put(Long.parseLong(splitData[0]), splitData[1]);
            }
        }
    }

    /**
     * Gets the postal code based off the x and y coordinate.
     * Query an onemap API to get the information.
     * @return A string of our postal code.
     * @throws IOException if unable to connect to URL.
     */
    private static String getCarparkPostalData (String xcoord, String ycoord) throws IOException {
        String url = "https://developers.onemap.sg/privateapi/commonsvc/revgeocodexy?location="
                + xcoord + "," + ycoord
                + "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIxNDIsInVzZXJfaWQiOjIxNDIsImVtYWlsIj"
                + "oiZm9uZ3poaXpob25nQGdtYWlsLmNvbSIsImZvcmV2ZXIiOmZhbHNlLCJpc3MiOiJodHRwOlwvXC9vbTIuZGZlLm9u"
                + "ZW1hcC5zZ1wvYXBpXC92MlwvdXNlclwvc2Vzc2lvbiIsImlhdCI6MTU0MDY1NTE2NiwiZXhwIjoxNTQxMDg3MTY2LC"
                + "JuYmYiOjE1NDA2NTUxNjYsImp0aSI6ImYwNzQxODgwZTE2NWQ3YjE2MzQwNDc0MWFhODc1NjNjIn0.D0vWxmcG-66k_"
                + "cZGns2ec6hh2unWqWZJggOQcy2MKes";

        InputStreamReader in;
        JsonArray array;
        Gson gson = new Gson();

        URL link = new URL(url);
        URLConnection communicate = link.openConnection();
        communicate.connect();

        in = new InputStreamReader((InputStream) communicate.getContent());

        array = new JsonParser()
                .parse(in)
                .getAsJsonObject()
                .getAsJsonArray("GeocodeInfo");

        if (array.size() > 0) {
            JsonElement object = array.get(0);
            if (object != null) {
                GeocodeInfoJson geocodeInfoJson = gson.fromJson(object.toString(), GeocodeInfoJson.class);
                return geocodeInfoJson.postalCode;
            }
        }
        in.close();
        return null;
    }

    /**
     * Container class to hold Geocode Information JSON data.
     */
    private class GeocodeInfoJson {
        //CHECKSTYLE.OFF: MemberNameCheck
        private final String buildingName;
        private final String block;
        private final String road;
        private final String postalCode;
        private final String xCoord;
        private final String yCoord;
        private final String latitude;
        private final String longitude;
        private final String longtitude;
        //CHECKSTYLE.ON: MemberNameCheck

        private GeocodeInfoJson(String... data) {
            buildingName = data[0];
            block = data[1];
            road = data[2];
            postalCode = data[3];
            xCoord = data[4];
            yCoord = data[5];
            latitude = data[6];
            longitude = data[7];
            longtitude = data[8];
        }
    }

    /**
     * Container class to hold Car park JSON data.
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
