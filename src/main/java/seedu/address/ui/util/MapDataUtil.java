package seedu.address.ui.util;

import java.util.Map;

/**
 * A cache that maps postal codes to districts on the Singapore map
 */
public class MapDataUtil {

    public static final Map<String, Integer> POSTAL_CODE_CACHE = Map.ofEntries(
            Map.entry("01", 1),
            Map.entry("02", 1),
            Map.entry("03", 1),
            Map.entry("04", 1),
            Map.entry("05", 1),
            Map.entry("06", 1),

            Map.entry("07", 2),
            Map.entry("08", 2),

            Map.entry("14", 3),
            Map.entry("15", 3),
            Map.entry("16", 3),

            Map.entry("09", 4),
            Map.entry("10", 4),

            Map.entry("11", 5),
            Map.entry("12", 5),
            Map.entry("13", 5),

            Map.entry("17", 6),

            Map.entry("18", 7),
            Map.entry("19", 7),

            Map.entry("20", 8),
            Map.entry("21", 8),

            Map.entry("22", 9),
            Map.entry("23", 9),

            Map.entry("24", 10),
            Map.entry("25", 10),
            Map.entry("26", 10),
            Map.entry("27", 10),

            Map.entry("28", 11),
            Map.entry("29", 11),
            Map.entry("30", 11),

            Map.entry("31", 12),
            Map.entry("32", 12),
            Map.entry("33", 12),

            Map.entry("34", 13),
            Map.entry("35", 13),
            Map.entry("36", 13),
            Map.entry("37", 13),

            Map.entry("38", 14),
            Map.entry("39", 14),
            Map.entry("40", 14),
            Map.entry("41", 14),

            Map.entry("42", 15),
            Map.entry("43", 15),
            Map.entry("44", 15),
            Map.entry("45", 15),

            Map.entry("46", 16),
            Map.entry("47", 16),
            Map.entry("48", 16),

            Map.entry("49", 17),
            Map.entry("50", 17),
            Map.entry("81", 17),

            Map.entry("51", 18),
            Map.entry("52", 18),

            Map.entry("53", 19),
            Map.entry("54", 19),
            Map.entry("55", 19),
            Map.entry("82", 19),

            Map.entry("56", 20),
            Map.entry("57", 20),

            Map.entry("58", 21),
            Map.entry("59", 21),

            Map.entry("60", 22),
            Map.entry("61", 22),
            Map.entry("62", 22),
            Map.entry("63", 22),
            Map.entry("64", 22),

            Map.entry("65", 23),
            Map.entry("66", 23),
            Map.entry("67", 23),
            Map.entry("68", 23),

            Map.entry("69", 24),
            Map.entry("70", 24),
            Map.entry("71", 24),

            Map.entry("72", 25),
            Map.entry("73", 25),

            Map.entry("77", 26),
            Map.entry("78", 26),

            Map.entry("75", 27),
            Map.entry("76", 27),

            Map.entry("79", 28),
            Map.entry("80", 28)
    );

    public static final Map<Integer, Integer[]> DISTRICT_CACHE = Map.ofEntries(
            Map.entry(1, new Integer[]{8, 5}),
            Map.entry(2, new Integer[]{9, 5}),
            Map.entry(3, new Integer[]{8, 4}),
            Map.entry(4, new Integer[]{9, 4}),
            Map.entry(5, new Integer[]{7, 3}),
            Map.entry(6, new Integer[]{8, 5}),
            Map.entry(7, new Integer[]{8, 5}),
            Map.entry(8, new Integer[]{7, 5}),
            Map.entry(9, new Integer[]{8, 4}),
            Map.entry(10, new Integer[]{7, 4}),
            Map.entry(11, new Integer[]{6, 4}),
            Map.entry(12, new Integer[]{5, 5}),
            Map.entry(13, new Integer[]{5, 5}),
            Map.entry(14, new Integer[]{6, 6}),
            Map.entry(15, new Integer[]{7, 6}),
            Map.entry(16, new Integer[]{6, 7}),
            Map.entry(17, new Integer[]{5, 8}),
            Map.entry(18, new Integer[]{4, 7}),
            Map.entry(19, new Integer[]{4, 6}),
            Map.entry(20, new Integer[]{4, 4}),
            Map.entry(21, new Integer[]{5, 3}),
            Map.entry(22, new Integer[]{6, 2}),
            Map.entry(23, new Integer[]{4, 3}),
            Map.entry(24, new Integer[]{3, 2}),
            Map.entry(25, new Integer[]{2, 3}),
            Map.entry(26, new Integer[]{3, 4}),
            Map.entry(27, new Integer[]{1, 4}),
            Map.entry(28, new Integer[]{3, 5})
    );


}
