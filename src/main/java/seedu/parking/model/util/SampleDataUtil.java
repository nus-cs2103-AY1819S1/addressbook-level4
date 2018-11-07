package seedu.parking.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.parking.model.CarparkFinder;
import seedu.parking.model.ReadOnlyCarparkFinder;
import seedu.parking.model.carpark.Address;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.CarparkType;
import seedu.parking.model.carpark.Coordinate;
import seedu.parking.model.carpark.FreeParking;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.NightParking;
import seedu.parking.model.carpark.PostalCode;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;
import seedu.parking.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CarparkFinder} with sample data.
 */
public class SampleDataUtil {

    public static Carpark[] getSampleCarpark() {
        return new Carpark[] {
            new Carpark(new Address("BLK 174 PUNGGOL FIELD"), new CarparkNumber("PL10"),
                new CarparkType("MULTI-STOREY CAR PARK"), new Coordinate("36567.6957, 42059.8507"),
                new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("188"),
                new NightParking("YES"), new ShortTerm("WHOLE DAY"),
                new TotalLots("560"), new TypeOfParking("ELECTRONIC PARKING"), new PostalCode("123456"),
                    null),
            new Carpark(new Address("BLK 175 PUNGGOL FIELD"), new CarparkNumber("PL11"),
                new CarparkType("MULTI-STOREY CAR PARK"), new Coordinate("36383.4393, 42222.0385"),
                new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("236"),
                new NightParking("YES"), new ShortTerm("WHOLE DAY"),
                new TotalLots("548"), new TypeOfParking("ELECTRONIC PARKING"), new PostalCode("123456"),
                    null),
            new Carpark(new Address("BLK 292 PUNGGOL CENTRAL"), new CarparkNumber("PL14"),
                new CarparkType("MULTI-STOREY CAR PARK"), new Coordinate("35982.6704, 42695.5596"),
                new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("407"),
                new NightParking("YES"), new ShortTerm("WHOLE DAY"),
                new TotalLots("734"), new TypeOfParking("ELECTRONIC PARKING"), new PostalCode("123456"),
                    null),
            new Carpark(new Address("BLK 642 PUNGGOL DRIVE"), new CarparkNumber("PL17"),
                new CarparkType("MULTI-STOREY CAR PARK"), new Coordinate("37244.6093, 42288.7308"),
                new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("297"),
                new NightParking("YES"), new ShortTerm("WHOLE DAY"),
                new TotalLots("609"), new TypeOfParking("ELECTRONIC PARKING"), new PostalCode("123456"),
                    null),
            new Carpark(new Address("BLK 162 PUNGGOL CENTRAL"), new CarparkNumber("PL18"),
                new CarparkType("MULTI-STOREY CAR PARK"), new Coordinate("37013.9934, 41998.5248"),
                new FreeParking("SUN & PH FR 7AM-10.30PM"), new LotsAvailable("136"),
                new NightParking("YES"), new ShortTerm("WHOLE DAY"),
                new TotalLots("615"), new TypeOfParking("ELECTRONIC PARKING"), new PostalCode("123456"),
                    null),
            new Carpark(new Address("BLK 186-188,190-192 PUNGGOL CENTRAL"), new CarparkNumber("PL19"),
                new CarparkType("BASEMENT CAR PARK"), new Coordinate("36545.1733, 42395.6863"),
                new FreeParking("NO"), new LotsAvailable("67"),
                new NightParking("NO"), new ShortTerm("7AM-7PM"),
                new TotalLots("291"), new TypeOfParking("ELECTRONIC PARKING"), new PostalCode("123456"),
                    null),
        };
    }

    public static ReadOnlyCarparkFinder getSampleCarparkFinder() {
        CarparkFinder sampleCf = new CarparkFinder();
        for (Carpark sampleCarpark : getSampleCarpark()) {
            sampleCf.addCarpark(sampleCarpark);
        }
        return sampleCf;
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
