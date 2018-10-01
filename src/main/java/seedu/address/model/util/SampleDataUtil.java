package seedu.address.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkJson;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.CarparkType;
import seedu.address.model.carpark.Coordinate;
import seedu.address.model.carpark.FreeParking;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.NightParking;
import seedu.address.model.carpark.ShortTerm;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.carpark.TypeOfParking;
import seedu.address.model.tag.Tag;

import com.google.gson.*;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static void readCSV (ArrayList<CarparkJson> carparkList){
        try {
            String url = "https://data.gov.sg/api/action/datastore_search?resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&limit=2000";
            URL dataStream = new URL(url);
            URLConnection conn = dataStream.openConnection();

            Gson gson = new Gson();

            JsonArray test3 = new JsonParser()
                    .parse(new InputStreamReader((InputStream) conn.getContent()))
                    .getAsJsonObject()
                    .getAsJsonObject("result")
                    .getAsJsonArray("records");

            for (int i = 0; i < test3.size(); i++) {
                JsonElement object = test3.get(i);
                CarparkJson cPark = gson.fromJson(object.toString(), CarparkJson.class);
                carparkList.add(cPark);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readJson(ArrayList<CarparkJson> carparkList){
        String url = "https://api.data.gov.sg/v1/transport/carpark-availability";
        try {
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
                String res[] = object.get("carpark_number")
                        .toString()
                        .split("\"");

                JsonObject test = object.getAsJsonArray("carpark_info")
                        .get(0)
                        .getAsJsonObject();

                String res2[] = test.get("total_lots")
                        .toString()
                        .split("\"");

                String res3[] = test.get("lots_available")
                        .toString()
                        .split("\"");

                for (CarparkJson aCarList : carparkList) {
                    if (aCarList.getNumber().contains(res[1])) {
                        aCarList.AddOn(res2[1], res3[1]);
                        break;
                    }
                }
            }
//            System.out.println(carparkList.size());
//            System.out.println(test3.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Carpark[] getSampleCarpark() {
        ArrayList<CarparkJson> carList = new ArrayList<>();

        readCSV(carList);
        readJson(carList);

        ArrayList<Carpark> carparkList = new ArrayList<>();
        for (CarparkJson aCarList : carList) {
            carparkList.add(aCarList.toCarpark());
        }

        return carparkList.toArray(new Carpark[carparkList.size()]);

//        return new Carpark[] {
//            new Carpark(new Address("BLK 11/12/13 YORK HILL CAR PARK"), new CarparkNumber("YHS"),
//                    new CarparkType("SURFACE CAR PARK"), new Coordinate("28508.3965, 29880.2227"),
//                    new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("10"), new NightParking("YES"),
//                    new ShortTerm("WHOLE DAY"), new TotalLots("100"),
//                    new TypeOfParking("ELECTRONIC PARKING"), getTagSet("AMAZING")),
//            new Carpark(new Address("BLK 747/752 YISHUN STREET 72"), new CarparkNumber("Y9"),
//                    new CarparkType("SURFACE CAR PARK"), new Coordinate("28077.2305, 45507.8047"),
//                    new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("51"), new NightParking("YES"),
//                    new ShortTerm("WHOLE DAY"), new TotalLots("300"),
//                    new TypeOfParking("ELECTRONIC PARKING"), getTagSet("HOME")),
//        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Carpark sampleCarpark : getSampleCarpark()) {
            sampleAb.addCarpark(sampleCarpark);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
