package seedu.parking.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.parking.commons.core.index.Index;
import seedu.parking.commons.util.StringUtil;
import seedu.parking.logic.parser.exceptions.ParseException;
import seedu.parking.model.carpark.Address;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.CarparkType;
import seedu.parking.model.carpark.Coordinate;
import seedu.parking.model.carpark.FreeParking;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.NightParking;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;
import seedu.parking.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String time} into an {@code int time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static int parseTime(String time) throws ParseException {
        String trimmedTime = time.trim();
        if (!StringUtil.isNonNegativeAboveNineInteger(trimmedTime)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedTime);
    }

    /**
     * Parses a {@code String parking} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code parking} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String carNum} into a {@code CarparkNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code carNum} is invalid.
     */
    public static CarparkNumber parseCarparkNumber(String carNum) throws ParseException {
        requireNonNull(carNum);
        String trimmedCarparkNumber = carNum.trim();
        if (!CarparkNumber.isValidCarparkNumber(trimmedCarparkNumber)) {
            throw new ParseException(CarparkNumber.MESSAGE_CAR_NUM_CONSTRAINTS);
        }
        return new CarparkNumber(trimmedCarparkNumber);
    }

    /**
     * Parses a {@code String carType} into a {@code CarparkType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code carType} is invalid.
     */
    public static CarparkType parseCarparkType(String carType) throws ParseException {
        requireNonNull(carType);
        String trimmedCarparkType = carType.trim();
        if (!CarparkType.isValidCarType(trimmedCarparkType)) {
            throw new ParseException(CarparkType.MESSAGE_CAR_TYPE_CONSTRAINTS);
        }
        return new CarparkType(trimmedCarparkType);
    }

    /**
     * Parses a {@code String coordinate} into a {@code Coordinate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code coordinate} is invalid.
     */
    public static Coordinate parseCoordinate(String coordinate) throws ParseException {
        requireNonNull(coordinate);
        String trimmedCoordinate = coordinate.trim();
        if (!Coordinate.isValidCoordinate(trimmedCoordinate)) {
            throw new ParseException(Coordinate.MESSAGE_COORD_CONSTRAINTS);
        }
        return new Coordinate(trimmedCoordinate);
    }

    /**
     * Parses a {@code String freePark} into a {@code FreeParking}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code coordinate} is invalid.
     */
    public static FreeParking parseFreeParking(String freePark) throws ParseException {
        requireNonNull(freePark);
        String trimmedFreeParking = freePark.trim();
        if (!FreeParking.isValidFreePark(trimmedFreeParking)) {
            throw new ParseException(FreeParking.MESSAGE_FREE_PARK_CONSTRAINTS);
        }
        return new FreeParking(trimmedFreeParking);
    }

    /**
     * Parses a {@code String lotsAvail} into a {@code LotsAvailable}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lotsAvail} is invalid.
     */
    public static LotsAvailable parseLotsAvailable(String lotsAvail) throws ParseException {
        String trimmedLotsAvailable = lotsAvail.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedLotsAvailable)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return new LotsAvailable(trimmedLotsAvailable);
    }

    /**
     * Parses a {@code String nightPark} into a {@code NightParking}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nightPark} is invalid.
     */
    public static NightParking parseNightParking(String nightPark) throws ParseException {
        requireNonNull(nightPark);
        String trimmedNightParking = nightPark.trim();
        if (!NightParking.isValidNightPark(trimmedNightParking)) {
            throw new ParseException(NightParking.MESSAGE_NIGHT_PARK_CONSTRAINTS);
        }
        return new NightParking(trimmedNightParking);
    }

    /**
     * Parses a {@code String shortTerm} into a {@code ShortTerm}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code shortTerm} is invalid.
     */
    public static ShortTerm parseShortTerm(String shortTerm) throws ParseException {
        requireNonNull(shortTerm);
        String trimmedShortTerm = shortTerm.trim();
        if (!ShortTerm.isValidShortTerm(trimmedShortTerm)) {
            throw new ParseException(ShortTerm.MESSAGE_SHORT_TERM_CONSTRAINTS);
        }
        return new ShortTerm(trimmedShortTerm);
    }

    /**
     * Parses a {@code String totalLots} into an {@code TotalLots}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code totalLots} is invalid.
     */
    public static TotalLots parseTotalLots(String totalLots) throws ParseException {
        requireNonNull(totalLots);
        String trimmedTotalLots = totalLots.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedTotalLots)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return new TotalLots(trimmedTotalLots);
    }

    /**
     * Parses a {@code String typePark} into a {@code TypeOfParking}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code typePark} is invalid.
     */
    public static TypeOfParking parseTypeOfParking(String typePark) throws ParseException {
        requireNonNull(typePark);
        String trimmedTypeOfParking = typePark.trim();
        if (!TypeOfParking.isValidTypePark(trimmedTypeOfParking)) {
            throw new ParseException(TypeOfParking.MESSAGE_TYPE_PARK_CONSTRAINTS);
        }
        return new TypeOfParking(trimmedTypeOfParking);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
