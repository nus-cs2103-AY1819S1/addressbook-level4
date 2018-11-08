package seedu.address.model.wish;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.commons.core.amount.Amount;
import seedu.address.model.tag.Tag;

/**
 * Represents a Wish in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Wish {

    // Identity fields
    private final UUID id;

    // Data fields
    private final Name name;
    private final Price price;
    private final Date date;
    private final Url url;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final SavedAmount savedAmount;
    private final boolean fulfilled;
    private final boolean expired;


    /**
     * Constructs an object for an already created {@code Wish}.
     * Every field must be present and not null.
     */
    public Wish(Name name, Price price, Date date, Url url, SavedAmount savedAmount,
                Remark remark, Set<Tag> tags, UUID id) {
        requireAllNonNull(name, price, date, url, tags);

        fulfilled = isSavedAmountGreaterThanOrEqualToPrice(savedAmount, price);
        expired = isCurrDateGreaterThanOrEqualToDate(date);

        this.name = name;
        this.price = price;
        this.date = date;
        this.url = url;
        this.tags.addAll(tags);
        this.remark = remark;
        this.savedAmount = savedAmount;

        this.id = id;
    }

    public Wish(Wish wish) {
        this(new Name(wish.name), new Price(wish.price), new Date(wish.date), new Url(wish.url),
                new SavedAmount(wish.savedAmount), new Remark(wish.remark), wish.copyTags(), wish.id);
    }

    /**
     * Every field must be present and not null.
     */
    private Wish(Name name, Price price, Date date, Url url, SavedAmount savedAmount, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, price, date, url, tags);

        fulfilled = isSavedAmountGreaterThanOrEqualToPrice(savedAmount, price);
        expired = isCurrDateGreaterThanOrEqualToDate(date);

        this.name = name;
        this.price = price;
        this.date = date;
        this.url = url;
        this.tags.addAll(tags);
        this.remark = remark;
        this.savedAmount = savedAmount;

        this.id = UUID.randomUUID();
    }

    /**
     * Performs a deep copy on each tag in {@code tags} and returns the copied set of tags.
     * @return the deep copied set of tags.
     */
    private Set<Tag> copyTags() {
        Set<Tag> copy = tags.stream().map(Tag::new).collect(Collectors.toSet());
        return copy;
    }

    /**
     * Returns a newly created {@code Wish} with a new id.
     */
    public static Wish createWish(Name name, Price price, Date date, Url url,
                                  SavedAmount savedAmount, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, price, date, url, tags);
        return new Wish(name, price, date, url, savedAmount, remark, tags);
    }

    /**
     * Returns a newly created {@code Wish} with an incremented savedAmount.
     * @param oldWish The wish to increment the saved amount on.
     * @param amountToIncrement The amount to increment by.
     */
    public static Wish createWishWithIncrementedSavedAmount(Wish oldWish, Amount amountToIncrement) {
        requireAllNonNull(oldWish, amountToIncrement);
        return new Wish(oldWish.getName(), oldWish.getPrice(), oldWish.getDate(), oldWish.getUrl(),
                oldWish.getSavedAmount().incrementSavedAmount(amountToIncrement), oldWish.getRemark(),
                oldWish.getTags(), oldWish.getId());
    }

    /**
     * Returns true if SaveAmount exceeds Price of wish.
     */
    private boolean isSavedAmountGreaterThanOrEqualToPrice(SavedAmount savedAmount, Price price) {
        return savedAmount.value >= price.value;
    }

    /*
     * Returns true if CurrDate exceeds Date of wish.
     */
    private boolean isCurrDateGreaterThanOrEqualToDate(Date date) {
        return new java.util.Date().after(date.getDateObject());
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public SavedAmount getSavedAmount() {
        return savedAmount;
    }

    public Url getUrl() {
        return url;
    }

    public Remark getRemark() {
        return remark;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public boolean isExpired() {
        return expired;
    }

    public UUID getId() {
        return id;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true only if two wishes have the same id.
     */
    public boolean isSameWish(Wish otherWish) {
        if (otherWish == this) {
            return true;
        }

        return otherWish != null
                && otherWish.getId().equals(getId());
    }

    /**
     * Returns the progress for {@code wish}, ranges from 0.0 to 1.0.
     */
    public Double getProgress() {
        return getSavedAmount().value / getPrice().value;
    }

    /**
     * Returns the {@code savedAmount} - {@code price} for {@code wish}.
     */
    public Amount getSavedAmountToPriceDifference() {
        return Amount.add(new Amount(savedAmount.toString()), new Amount("-" + price.toString()));
    }

    /**
     * Returns true if both wishes have the same identity and data fields.
     * This defines a stronger notion of equality between two wishes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Wish)) {
            return false;
        }

        Wish otherWish = (Wish) other;
        return otherWish.getName().equals(getName())
                && otherWish.getPrice().equals(getPrice())
                && otherWish.getDate().equals(getDate())
                && otherWish.getUrl().equals(getUrl())
                && otherWish.getTags().equals(getTags())
                && otherWish.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, date, url, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: ")
                .append(getPrice())
                .append(" Date: ")
                .append(getDate())
                .append(" Url: ")
                .append(getUrl())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
