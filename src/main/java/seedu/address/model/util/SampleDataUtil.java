package seedu.address.model.util;

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
                    SAMPLE_REMARK_EMPTY, getTagSet("technology"),
                    UUID.fromString("4ffd64af-acee-40c5-ac65-85fcdd2343b2")),
            new Wish(new Name("Dyson V8 Carbon Fibre Cord Free Vacuum Cleaner"),
                    new Price("599.00"), new Date("21/05/2019"),
                    new Url("https://www.lazada.sg/products/dyson-v8-carbon-fibre-cord-"
                            + "free-vacuum-cleaner-i236932729-s363025435.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("family"),
                    UUID.fromString("23ceb2af-40c5-b2af-ac65-85fcdd2343b2")),
            new Wish(new Name("Native Eyewear Apres Sunglasses"), new Price("269.68"),
                    new Date("13/8/2019"),
                    new Url("https://www.amazon.com/dp/B004ACE528?tag=thimest07-20"),
                    new SavedAmount("88.99"),
                    SAMPLE_REMARK_EMPTY,
                    getTagSet("accessory"),
                    UUID.fromString("ba2ba0-c13a-a512-74ba-5603a7431ca0")),
            new Wish(new Name("Digital Air Fryer"), new Price("299.45"),
                    new Date("1/4/2019"),
                    new Url("https://www.amazon.com/Philips-HD9240-94-Digital-Airfryer/dp/B00TR78QUI"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("family"),
                    UUID.fromString("6ab13d0c-7f6a-6ea6-4270-466ea612d0b6")),
            new Wish(new Name("ADIDAS PUREBOOST DPR"), new Price("199.00"),
                    new Date("25/12/2018"),
                    new Url("https://www.lazada.sg/products/adidas-pureboost-dpr-men-shoes-"
                            + "blackbb6291-i278264059-s429643922.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet(),
                    UUID.fromString("61313d0c-7f69-6ea6-4270-466ea613d0b6")),
            new Wish(new Name("Marvels Spiderman for PS4"), new Price("59.49"),
                    new Date("12/2/2019"),
                    new Url("https://www.amazon.com/Marvels-Spider-Man-PlayStation-4/dp/B01GW8YDLK"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("games"),
                    UUID.fromString("61313d0c-7f69-6ea6-4120-466ea613d0b6")),
            new Wish(new Name("SEIKO MENS QUARTZ CHRONOGRAPH SSB193P1"), new Price("269.68"),
                    new Date("5/6/2019"),
                    new Url("https://www.lazada.sg/products/seiko-mens-quartz-chronograph-ssb193p1"
                            + "-i140905982-s172509072.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY,
                    getTagSet("accessory"),
                    UUID.fromString("574aa0-b012-f512-a0d7-5603a7474aa0")),
            new Wish(new Name("Black Insulted Backpack Cooler"), new Price("36.49"),
                    new Date("2/3/2019"),
                    new Url("https://www.amazon.com/dp/B008L3F4MM?tag=thimest07-20"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY,
                    getTagSet("accessory"),
                    UUID.fromString("534ab0-d012-c712-a1d7-5203a7b7caa0")),
            new Wish(new Name("Black Insulted Backpack Cooler"), new Price("36.49"),
                    new Date("2/3/2019"),
                    new Url("https://www.amazon.com/dp/B008L3F4MM?tag=thimest07-20"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY,
                    getTagSet("accessory"),
                    UUID.fromString("532ab0-d012-c712-a1c7-5203a7b7caa0")),
            new Wish(new Name("Logitech K840 Mechanical Keyboard"), new Price("450.00"),
                    new Date("15/11/2021"),
                    new Url("https://www.amazon.com/Logitech-Mechanical-Keyboard-Romer-Switches/dp/B071VHYZ62"),
                    new SavedAmount("12.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("peripherals", "technology"),
                    UUID.fromString("27fb99bc-bff4-4c70-894f-466ea61380b6")),
            new Wish(new Name("Fenders Beginner Squire Bass Guitar"), new Price("1057.88"),
                    new Date("14/2/2019"),
                    new Url("https://www.lazada.sg/products/squier-by-fender-vintage-modified-"
                            + "jazz-beginner-electric-bass-guitar-3-color-sunburst-intl-i222326849-s338928293.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("music", "family"),
                    UUID.fromString("27f69bbc-7f69-4270-894f-466ea613d0b6")),
            new Wish(new Name("Nintendo Switch"), new Price("540.00"), new Date("11/11/2018"),
                    new Url("https://www.lazada.sg/products/nintendo-switch-neon-console-"
                            + "1-year-local-warranty-best-seller-i180040203-s230048296.html"),
                    new SavedAmount("20.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("technology", "games"),
                    UUID.fromString("03526709-f254-4eb9-96b8-c6bcaf0a1e39")),
            new Wish(new Name("Standing Fan"),
                    new Price("99.00"), new Date("12/03/2019"),
                    new Url("https://www.lazada.sg/products/kdk-p40us-gold-40cm-stand-fan-"
                            + "1-year-warranty-by-kdk-i6359057-s8000474.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("family"),
                    UUID.fromString("fd23d2af-40c5-b2af-ac65-eb2afd2343b2")),
            new Wish(new Name("SEIKO MENS QUARTZ CHRONOGRAPH SSB193P1"), new Price("269.68"),
                    new Date("5/6/2019"),
                    new Url("https://www.lazada.sg/products/seiko-mens-quartz-chronograph-ssb193p1"
                            + "-i140905982-s172509072.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY,
                    getTagSet("accessory"),
                    UUID.fromString("56032b-f532-7474-d0a7-5603a7474aa0")),
            new Wish(new Name("PS4 Pro"), new Price("1200.00"), new Date("10/10/2017"),
                    new Url("https://www.lazada.sg/products/sony-playstation-4-pro-1tb-console-"
                            + "local-stock-with-sony-warranty-i100009437-s100011973.html"),
                    new SavedAmount("170.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("family", "gaming", "technology"),
                    UUID.fromString("f8a95de7-1d70-4957-b214-0551cc1e678d")),
            new Wish(new Name("Autumn And Winter Brushed And Thick Coat"), new Price("36.60"),
                    new Date("1/12/2018"),
                    new Url("https://www.lazada.sg/products/autumn-and-winter-brushed-and-thick-coat-men-plus-"
                            + "sized-loose-windshield-outdoor-jacket-detachable-hat-i162368770-s207578926.html"),
                    new SavedAmount("12.00"),
                    SAMPLE_REMARK_EMPTY,
                    getTagSet("clothing"),
                    UUID.fromString("9f512b-5ab2-3a74-a0d7-560253a74aa0")),
            new Wish(new Name("Qanba Obsidian Arcade Stick"), new Price("606.16"),
                    new Date("5/1/2020"),
                    new Url("https://www.lazada.sg/products/qanba-obsidian-joystick-for-playstation-4-and-playstation"
                            + "-3-and-pc-fighting-stick-officially-licensed-sony-product-i269618449-s418683701.html"),
                    new SavedAmount("0.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("peripherals", "technology"),
                    UUID.fromString("27fb99bc-bff4-4c70-891f-467ea61380b6")),
            new Wish(new Name("EVGA 1080 Ti Graphics Card"), new Price("70.00"),
                    new Date("09/09/2018"),
                    new Url("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV"),
                    new SavedAmount("4.50"),
                    SAMPLE_REMARK_EMPTY, getTagSet("classmates"),
                    UUID.fromString("97765020-5602-4fd2-a0d7-8bf753a746ef")),
            new Wish(new Name("SAMSUNG Micro SD Card 128GB"), new Price("57.00"),
                    new Date("02/09/2018"),
                    new Url("https://www.lazada.sg/products/100-authentic-guaranteedsamsung-micro-"
                            + "sdxc-128gb-evo-plus-u3-class-10-micro-sd-card-memory-i234920863-s360019537.html"),
                    new SavedAmount("57.00"),
                    SAMPLE_REMARK_EMPTY, getTagSet("technology"),
                    UUID.fromString("97761020-5602-4fc2-a0d7-8bf753a746ef")),
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
