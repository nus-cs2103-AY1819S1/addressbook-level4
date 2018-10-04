package seedu.address.model.util;

import java.io.IOException;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.commons.util.GsonUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Carpark[] getSampleCarpark() throws IOException {
        ArrayList<Carpark> carparkList = new ArrayList<>();
        for (ArrayList<String> carpark : GsonUtil.fetchCarparkInfo()) {
            Carpark c = new Carpark(new Address(carpark.get(0)), new CarparkNumber(carpark.get(1)),
                    new CarparkType(carpark.get(2)), new Coordinate(carpark.get(3)),
                    new FreeParking(carpark.get(4)), new LotsAvailable(carpark.get(5)),
                    new NightParking(carpark.get(6)), new ShortTerm(carpark.get(7)),
                    new TotalLots(carpark.get(8)), new TypeOfParking(carpark.get(9)), null);
            carparkList.add(c);
        }
        return carparkList.toArray(new Carpark[0]);

//        return new Carpark[0];
    }

    public static ReadOnlyAddressBook getSampleAddressBook() throws IOException {
        AddressBook sampleAb = new AddressBook();
        for (Carpark sampleCarpark : getSampleCarpark()) {
//            System.out.println(sampleCarpark);
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
