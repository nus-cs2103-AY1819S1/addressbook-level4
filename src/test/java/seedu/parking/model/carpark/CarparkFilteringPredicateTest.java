package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.parking.logic.parser.CarparkTypeParameter;
import seedu.parking.logic.parser.FreeParkingParameter;
import seedu.parking.logic.parser.ParkingSystemTypeParameter;
import seedu.parking.testutil.CarparkBuilder;

public class CarparkFilteringPredicateTest {

    @Test
    public void equals() {

        // Create two testing predicates
        List<String> flagList = new ArrayList<>();
        flagList.add("n/");
        flagList.add("ct/");
        flagList.add("ps/");

        FreeParkingParameter freeParking = new FreeParkingParameter("SUN", new Date(2470200200L),
                new Date(3748239175L));

        CarparkTypeParameter carparkType = new CarparkTypeParameter("COVERED");

        ParkingSystemTypeParameter firstParkingSystem = new ParkingSystemTypeParameter("COUPON");
        ParkingSystemTypeParameter secondParkingSystem = new ParkingSystemTypeParameter("ELECTRONIC");

        List<String> firstLocationKeywords = new ArrayList<>();
        firstLocationKeywords.add("sengkang");

        List<String> secondLocationKeywords = new ArrayList<>();
        secondLocationKeywords.add("serangoon");

        CarparkFilteringPredicate firstPredicate = new CarparkFilteringPredicate(firstLocationKeywords, flagList,
                freeParking, carparkType, firstParkingSystem);

        CarparkFilteringPredicate secondPredicate = new CarparkFilteringPredicate(secondLocationKeywords, flagList,
                freeParking, carparkType, secondParkingSystem);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CarparkFilteringPredicate firstPredicateCopy = new CarparkFilteringPredicate(firstLocationKeywords, flagList,
                freeParking, carparkType, firstParkingSystem);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different car park -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_hasFreeParking_returnsTrue() { // filter f/ sun 8.30am 5.30pm
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("f/");

        FreeParkingParameter freeParking = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mmaa");
            Date inputStart = dateFormat.parse("8.30am");
            Date inputEnd = dateFormat.parse("5.30pm");
            freeParking = new FreeParkingParameter("SUN", inputStart, inputEnd);
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                freeParking, null, null);

        assertTrue(predicate.test(new CarparkBuilder().withFreeParking("SUN & PH FR 7AM-10.30PM")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasNightParking_returnsTrue() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("n/");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        assertTrue(predicate.test(new CarparkBuilder().withNightParking("YES")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasAvailableParking_returnsTrue() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("a/");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        assertTrue(predicate.test(new CarparkBuilder().withLotsAvailable("410")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasCorrectCarparkType_returnsTrue() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("ct/");

        CarparkTypeParameter carparkType = new CarparkTypeParameter("MULTISTOREY");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, carparkType, null);

        assertTrue(predicate.test(new CarparkBuilder().withCarparkType("MULTI-STOREY CAR PARK")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasCorrectParkingSystem_returnsTrue() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("ps/");

        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, parkingSystemType);

        assertTrue(predicate.test(new CarparkBuilder().withTypeOfParking("COUPON")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasShortTermParking_returnsTrue() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("s/");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        assertTrue(predicate.test(new CarparkBuilder().withShortTerm("WHOLE DAY")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_filterByMultipleFlags_returnsTrue() { // find sengkang, filter s/ ps/ coupon n/
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("s/");
        flagList.add("ps/");
        flagList.add("n/");

        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList, null,
                null, parkingSystemType);

        assertTrue(predicate.test(new CarparkBuilder().withTypeOfParking("COUPON").withShortTerm("WHOLE DAY")
                .withNightParking("YES").withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasNoFreeParking_returnsFalse() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("f/");

        FreeParkingParameter freeParking = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mmaa");
            Date inputStart = dateFormat.parse("8.30am");
            Date inputEnd = dateFormat.parse("5.30pm");
            freeParking = new FreeParkingParameter("SUN", inputStart, inputEnd);
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                freeParking, null, null);

        assertFalse(predicate.test(new CarparkBuilder().withFreeParking("NO")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasNoNightParking_returnsFalse() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("n/");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        assertFalse(predicate.test(new CarparkBuilder().withNightParking("NO")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasNoAvailableParking_returnsFalse() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("a/");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        assertFalse(predicate.test(new CarparkBuilder().withLotsAvailable("0")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));

    }

    @Test
    public void test_hasWrongCarparkType_returnsFalse() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("ct/");

        CarparkTypeParameter carparkType = new CarparkTypeParameter("MULTISTOREY");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, carparkType, null);

        assertFalse(predicate.test(new CarparkBuilder().withCarparkType("SURFACE")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasWrongParkingSystem_returnsFalse() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("ps/");

        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, parkingSystemType);

        assertFalse(predicate.test(new CarparkBuilder().withTypeOfParking("ELECTRONIC PARKING")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));
    }

    @Test
    public void test_hasNoShortTermParking_returnsFalse() {
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");

        List<String> flagList = new ArrayList<>();
        flagList.add("s/");

        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        assertFalse(predicate.test(new CarparkBuilder().withShortTerm("NO")
                .withAddress("BLK 451 SENGKANG WEST WAY").build()));

    }

}
