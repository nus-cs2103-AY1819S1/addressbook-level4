package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
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

    public static Carpark[] getSampleCarpark() {
        return new Carpark[] {
            new Carpark(new Address("BLK 11/12/13 YORK HILL CAR PARK"), new CarparkNumber("YHS"),
                    new CarparkType("SURFACE CAR PARK"), new Coordinate("28508.3965, 29880.2227"),
                    new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("10"), new NightParking("YES"),
                    new ShortTerm("WHOLE DAY"), new TotalLots("100"),
                    new TypeOfParking("ELECTRONIC PARKING"), getTagSet("AMAZING")),
            new Carpark(new Address("BLK 747/752 YISHUN STREET 72"), new CarparkNumber("Y9"),
                    new CarparkType("SURFACE CAR PARK"), new Coordinate("28077.2305, 45507.8047"),
                    new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("51"), new NightParking("YES"),
                    new ShortTerm("WHOLE DAY"), new TotalLots("300"),
                    new TypeOfParking("ELECTRONIC PARKING"), getTagSet("HOME")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Carpark sampleCarpark : getSampleCarpark()) {
            sampleAb.addPerson(sampleCarpark);
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
