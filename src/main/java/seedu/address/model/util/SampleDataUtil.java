package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyWishBook;
import seedu.address.model.WishBook;
import seedu.address.model.WishTransaction;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Date;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

/**
 * Contains utility methods for populating {@code WishBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark SAMPLE_REMARK_EMPTY = new Remark("");
    public static Wish[] getSampleWishes() {
        List<Wish> wishList = Arrays.asList(
            new Wish(new Name("Apple iPhone X"), new Price("700.00"), new Date("21/05/2018"),
                    new Url("https://www.amazon.com/Apple-iPhone-Fully-Unlocked-32GB/dp/B0731HBTZ7"),
                    new SavedAmount("700.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("friends"),
                    UUID.fromString("4ffd64af-acee-40c5-ac65-85fcdd2343b2")),
            new Wish(new Name("Logitech K840 Mechanical Keyboard"), new Price("450.00"),
                    new Date("15/11/2021"),
                    new Url("https://www.amazon.com/Logitech-Mechanical-Keyboard-Romer-Switches/dp/B071VHYZ62"),
                    new SavedAmount("12.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("colleagues", "friends"),
                    UUID.fromString("27fb99bc-bff4-4c70-894f-466ea61380b6")),
            new Wish(new Name("Nintendo Switch"), new Price("540.00"), new Date("11/11/2018"),
                    new Url("https://www.lazada.sg/products/nintendo-switch-neon-console-"
                            + "1-year-local-warranty-best-seller-i180040203-s230048296.html"),
                    new SavedAmount("20.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("neighbours"),
                    UUID.fromString("03526709-f254-4eb9-96b8-c6bcaf0a1e39")),
            new Wish(new Name("PS4 Pro"), new Price("1200.00"), new Date("10/10/2017"),
                    new Url("https://www.lazada.sg/products/sony-playstation-4-pro-1tb-console-"
                            + "local-stock-with-sony-warranty-i100009437-s100011973.html"),
                    new SavedAmount("170.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("family"),
                    UUID.fromString("f8a95de7-1d70-4957-b214-0551cc1e678d")),
            new Wish(new Name("EVGA 1080 Ti Graphics Card"), new Price("70.00"),
                    new Date("09/09/2018"),
                    new Url("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV"),
                    new SavedAmount("4.50"),
                    SAMPLE_REMARK_EMPTY, getTagSet("classmates"),
                    UUID.fromString("97765020-5602-4fd2-a0d7-8bf753a746ef")),
            new Wish(new Name("1TB SSD"), new Price("55.00"),
                    new Date("05/05/2019"),
                    new Url("https://www.amazon.com/gp/product/B07D998212"),
                    new SavedAmount("45.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("colleagues"),
                    UUID.fromString("5f73cfae-eb98-4d5e-b434-ebe3f397f78d"))
        );
        wishList.sort(new WishComparator());
        return wishList.toArray(new Wish[0]);
    }

    public static ReadOnlyWishBook getSampleWishBook() {
        WishBook sampleWb = new WishBook();
        for (Wish sampleWish : getSampleWishes()) {
            sampleWb.addWish(sampleWish);
        }

        return sampleWb;
    }

    public static WishTransaction getSampleWishTransaction() {
        return new WishTransaction(getSampleWishBook());
    }

    /**`
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
