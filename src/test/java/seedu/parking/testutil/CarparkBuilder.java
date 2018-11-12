package seedu.parking.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.parking.model.util.SampleDataUtil;

/**
 * A utility class to help with building Car park objects.
 */
public class CarparkBuilder {

    public static final String DEFAULT_CARPARK_NUMBER = "A1";
    public static final String DEFAULT_CARPARK_TYPE = "DEFAULT CAR PARK";
    public static final String DEFAULT_COORDINATE = "11111.1111, 99999.9999";
    public static final String DEFAULT_FREE_PARKING = "YES";
    public static final String DEFAULT_LOTS_AVAILABLE = "1";
    public static final String DEFAULT_NIGHT_PARKING = "NO";
    public static final String DEFAULT_SHORT_TERM = "SOME DAY";
    public static final String DEFAULT_TOTAL_LOTS = "9000";
    public static final String DEFAULT_TYPE_OF_PARKING = "OLD SCHOOL PARKING";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_POSTALCODE = "610289";

    private CarparkNumber carparkNumber;
    private CarparkType carparkType;
    private Coordinate coordinate;
    private FreeParking freeParking;
    private LotsAvailable lotsAvailable;
    private NightParking nightParking;
    private ShortTerm shortTerm;
    private TotalLots totalLots;
    private TypeOfParking typeOfParking;
    private Address address;
    private PostalCode postalCode;
    private Set<Tag> tags;

    public CarparkBuilder() {
        carparkNumber = new CarparkNumber(DEFAULT_CARPARK_NUMBER);
        carparkType = new CarparkType(DEFAULT_CARPARK_TYPE);
        coordinate = new Coordinate(DEFAULT_COORDINATE);
        freeParking = new FreeParking(DEFAULT_FREE_PARKING);
        lotsAvailable = new LotsAvailable(DEFAULT_LOTS_AVAILABLE);
        nightParking = new NightParking(DEFAULT_NIGHT_PARKING);
        shortTerm = new ShortTerm(DEFAULT_SHORT_TERM);
        totalLots = new TotalLots(DEFAULT_TOTAL_LOTS);
        typeOfParking = new TypeOfParking(DEFAULT_TYPE_OF_PARKING);
        address = new Address(DEFAULT_ADDRESS);
        postalCode = new PostalCode(DEFAULT_POSTALCODE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CarparkBuilder with the data of {@code carparkToCopy}.
     */
    public CarparkBuilder(Carpark carparkToCopy) {
        carparkNumber = carparkToCopy.getCarparkNumber();
        carparkType = carparkToCopy.getCarparkType();
        coordinate = carparkToCopy.getCoordinate();
        freeParking = carparkToCopy.getFreeParking();
        lotsAvailable = carparkToCopy.getLotsAvailable();
        nightParking = carparkToCopy.getNightParking();
        shortTerm = carparkToCopy.getShortTerm();
        totalLots = carparkToCopy.getTotalLots();
        typeOfParking = carparkToCopy.getTypeOfParking();
        address = carparkToCopy.getAddress();
        postalCode = carparkToCopy.getPostalCode();
        tags = new HashSet<>(carparkToCopy.getTags());
    }

    /**
     * Sets the {@code CarparkNumber} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withCarparkNumber (String carparkNumber) {
        this.carparkNumber = new CarparkNumber(carparkNumber);
        return this;
    }

    /**
     * Sets the {@code CarparkType} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withCarparkType(String carparkType) {
        this.carparkType = new CarparkType(carparkType);
        return this;
    }

    /**
     * Sets the {@code Coordinate} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withCoordinate(String coordinate) {
        this.coordinate = new Coordinate(coordinate);
        return this;
    }

    /**
     * Sets the {@code FreeParking} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withFreeParking(String freeParking) {
        this.freeParking = new FreeParking(freeParking);
        return this;
    }

    /**
     * Sets the {@code LotsAvailable} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withLotsAvailable(String lotsAvailable) {
        this.lotsAvailable = new LotsAvailable(lotsAvailable);
        return this;
    }

    /**
     * Sets the {@code NightParking} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withNightParking(String nightParking) {
        this.nightParking = new NightParking(nightParking);
        return this;
    }

    /**
     * Sets the {@code ShortTerm} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withShortTerm(String shortTerm) {
        this.shortTerm = new ShortTerm(shortTerm);
        return this;
    }

    /**
     * Sets the {@code TotalLots} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withTotalLots(String totalLots) {
        this.totalLots = new TotalLots(totalLots);
        return this;
    }

    /**
     * Sets the {@code TypeOfParking} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withTypeOfParking(String typeOfParking) {
        this.typeOfParking = new TypeOfParking(typeOfParking);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Carpark} that we are building.
     */
    public CarparkBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Carpark} that we are building.
     */
    public CarparkBuilder withPostalCode(String postalCode) {
        this.postalCode = new PostalCode(postalCode);
        return this;
    }

    /**
     * Builds a {@code Carpark} that we are building.
     */
    public Carpark build() {
        return new Carpark(address, carparkNumber, carparkType, coordinate,
            freeParking, lotsAvailable, nightParking, shortTerm, totalLots, typeOfParking, postalCode, tags);
    }

}
