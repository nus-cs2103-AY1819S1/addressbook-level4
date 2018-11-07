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
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import seedu.parking.commons.core.LogsCenter;

/**
 * Converts JSON from API call to a Java Object
 */
public class GsonUtil {
    private static final HashSet<CarparkJson> carparkList = new HashSet<>();
    private static final HashMap<Long, String> postalCodeMap = new HashMap<>();
    private static final HashSet<String[]> parkingData = new HashSet<>();

    private static final String POSTAL_CODE_TXT = "postalcodeData.txt";

    private static final Logger logger = LogsCenter.getLogger(GsonUtil.class);

    /**
     * Fetches car park information and returns a list of it.
     * @return A list of list of strings containing the car park information.
     * @throws IOException if unable to connect to URL.
     */
    public static List<List<String>> fetchAllCarparkInfo() throws Exception {
        final boolean[] hasError = {false, false, false};

        //Thread first = new Thread(() -> {
        //    try {
        //        loadCarparkPostalCode();
        //    } catch (IOException e) {
        //        hasError[0] = true;
        //        logger.warning("Unable to load postal code.");
        //    }
        //});
        //first.start();

        Thread second = new Thread(() -> {
            try {
                getCarparkData();
            } catch (IOException e) {
                hasError[1] = true;
                logger.warning("Unable to load car park data.");
            }
        });
        second.start();

        Thread third = new Thread(() -> {
            try {
                getCarparkAvailability();
            } catch (IOException e) {
                hasError[2] = true;
                logger.warning("Unable to load parking lots data.");
            }
        });
        third.start();

        //first.join();
        second.join();
        third.join();

        if (hasError[0] || hasError[1] || hasError[2]) {
            throw new IOException();
        }

        return saveAsList();
    }

    public static List<String> getSelectedCarparkInfo(String carparkNum) throws IOException {
        String url = "https://api.data.gov.sg/v1/transport/carpark-availability";
        URL link = new URL(url);
        URLConnection communicate = link.openConnection();
        communicate.setConnectTimeout(20000);
        communicate.setReadTimeout(20000);
        communicate.connect();

        InputStreamReader in = new InputStreamReader((InputStream) communicate.getContent());

        JsonArray array = new JsonParser()
                .parse(in)
                .getAsJsonObject()
                .getAsJsonArray("items")
                .get(0)
                .getAsJsonObject()
                .getAsJsonArray("carpark_data");

        List<String> lotData = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject carObject = array.get(i).getAsJsonObject();
            String[] carparkNumber = carObject.get("carpark_number")
                    .toString()
                    .split("\"");

            if (!carparkNumber[1].equals(carparkNum)) {
                continue;
            }

            JsonObject carInfo = carObject.getAsJsonArray("carpark_info")
                    .get(0)
                    .getAsJsonObject();

            String[] totalLot = carInfo.get("total_lots")
                    .toString()
                    .split("\"");

            String[] lotAvail = carInfo.get("lots_available")
                    .toString()
                    .split("\"");

            lotData.add(carparkNumber[1]);
            lotData.add(lotAvail[1]);
            lotData.add(totalLot[1]);
        }

        in.close();
        return lotData;
    }

    /**
     * Adds in the parking lots details and convert to a list.
     * @return A List containing all the car parks information.
     */
    private static List<List<String>> saveAsList() {
        List<List<String>> str = new ArrayList<>();

        for (CarparkJson list : carparkList) {
            for (String[] data : parkingData) {
                if (list.getNumber().equals(data[0])) {
                    list.addOn(data[1], data[2]);
                    break;
                } else if (list.getJsonData() == null) {
                    list.addOn("0", "0");
                }
            }
            String value = postalCodeMap.get(fnvHash(new String[] {list.x_coord, list.y_coord}));
            if (value == null) {
                list.getJsonData().add("000000");
            } else {
                list.getJsonData().add(value);
            }
            str.add(list.getJsonData());
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
        communicate.setConnectTimeout(20000);
        communicate.setReadTimeout(20000);
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
     * Get list of car park information without available lots information.
     * @throws IOException if unable to connect to URL.
     */
    private static void getCarparkData() throws IOException {
        String urlHalf = "https://data.gov.sg/api/action/datastore_search?"
                + "resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&limit=2000&offset=";

        int offset = 0;
        StringBuilder urlFull = new StringBuilder();
        urlFull.append(urlHalf).append(offset);

        InputStreamReader in;
        JsonArray array;
        Gson gson = new Gson();

        do {
            URL link = new URL(urlFull.toString());
            URLConnection communicate = link.openConnection();
            communicate.setConnectTimeout(20000);
            communicate.setReadTimeout(20000);
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

            offset += 2000;
            urlFull.setLength(0);
            urlFull.append(urlHalf).append(offset);
        } while (array.size() == 2000);
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
    private static void loadCarparkPostalCode() throws IOException {
        postalCodeMap.clear();
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
    static String getCarparkPostalData(String xcoord, String ycoord) throws IOException {
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
        communicate.setConnectTimeout(20000);
        communicate.setReadTimeout(20000);
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
                return geocodeInfoJson.POSTALCODE;
            } else {
                logger.warning("Gson object is not available");
            }
        } else {
            logger.warning("Gson array size return is 0");
        }
        in.close();
        return null;
    }

    /**
     * FNV hashes a String array.
     * @return A long which is the hash value
     */
    private static long fnvHash(String[] strings) {
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
    private static void hashmapToTxt(HashMap<Long, String> map) throws IOException {
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
}
