package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.Stock;

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String icNumber} into a {@code IcNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code icNumber} is invalid.
     */
    public static IcNumber parseIcNumber(String icNumber) throws ParseException {
        requireNonNull(icNumber);
        String trimmedIcNumber = icNumber.trim();
        if (!IcNumber.isValidIcNumber(trimmedIcNumber)) {
            throw new ParseException(IcNumber.MESSAGE_ICNUMBER_CONSTRAINTS);
        }
        return new IcNumber(trimmedIcNumber);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
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
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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

    /**
     * Parses a {@code String medicineName} into an {@code MedicineName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medicineName} is invalid.
     */
    public static MedicineName parseMedicineName(String medicineName) throws ParseException {
        requireNonNull(medicineName);
        String trimmedMedicineName = medicineName.trim();
        if (!MedicineName.isValidMedicineName(trimmedMedicineName)) {
            throw new ParseException(MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        }
        return new MedicineName(trimmedMedicineName);
    }

    /**
     * Parses a {@code String minimumStockQuantity} into an {@code MinimumStockQuantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code minimumStockQuantity} is invalid.
     */
    public static MinimumStockQuantity parseMinimumStockQuantity(String minimumStockQuantity) throws ParseException {
        requireNonNull(minimumStockQuantity);
        String trimmedMinimumStockQuantity = minimumStockQuantity.trim();
        if (!MinimumStockQuantity.isValidMinimumStockQuantity(trimmedMinimumStockQuantity)) {
            throw new ParseException(MinimumStockQuantity.MESSAGE_MINIMUM_STOCK_QUANTITY_CONSTRAINTS);
        }
        return new MinimumStockQuantity(trimmedMinimumStockQuantity);
    }

    /**
     * Parses a {@code String pricePerUnit} into an {@code PricePerUnit}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pricePerUnit} is invalid.
     */
    public static PricePerUnit parsePricePerUnit(String pricePerUnit) throws ParseException {
        requireNonNull(pricePerUnit);
        String trimmedPricePerUnit = pricePerUnit.trim();
        if (!PricePerUnit.isValidPricePerUnit(trimmedPricePerUnit)) {
            throw new ParseException(PricePerUnit.MESSAGE_PRICE_PER_UNIT_CONSTRAINTS);
        }
        return new PricePerUnit(trimmedPricePerUnit);
    }

    /**
     * Parses a {@code String serialNumber} into an {@code SerialNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code serialNumber} is invalid.
     */
    public static SerialNumber parseSerialNumber(String serialNumber) throws ParseException {
        requireNonNull(serialNumber);
        String trimmedSerialNumber = serialNumber.trim();
        if (!SerialNumber.isValidSerialNumber(trimmedSerialNumber)) {
            throw new ParseException(SerialNumber.MESSAGE_SERIAL_NUMBER_CONSTRAINTS);
        }
        return new SerialNumber(trimmedSerialNumber);
    }

    /**
     * Parses a {@code String stock} into an {@code Stock}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code stock} is invalid.
     */
    public static Stock parseStock(String stock) throws ParseException {
        requireNonNull(stock);
        String trimmedStock = stock.trim();
        if (!Stock.isValidStock(trimmedStock)) {
            throw new ParseException(Stock.MESSAGE_STOCK_CONSTRAINTS);
        }
        return new Stock(trimmedStock);
    }
}
