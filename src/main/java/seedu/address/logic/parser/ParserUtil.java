package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.carpark.Address;
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
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
        String trimmedcarNum = carNum.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedcarNum)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return new CarparkNumber(trimmedcarNum);
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
        if (!Coordinate.isValidCoord(trimmedCoordinate)) {
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
        String trimmedLotsAvail = lotsAvail.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedLotsAvail)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return new LotsAvailable(trimmedLotsAvail);
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
