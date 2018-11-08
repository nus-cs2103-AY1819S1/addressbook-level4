package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.WishBook;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.Wish;
import seedu.address.model.wish.WishContainsKeywordsPredicate;
import seedu.address.testutil.EditWishDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_AGE_YEAR = "5y";

    public static final String VALID_PRICE_CHARLES = "32.20";
    public static final String VALID_DATE_1 = "01/01/2019";
    public static final String VALID_AGE_YEAR_MONTH = "7y2m";
    public static final String VALID_AGE_ALL_FIELDS = "3y7m2d";
    public static final String VALID_AGE_YEAR_DAY = "2y3d";
    public static final String VALID_AGE_DAY = "3d";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CHARLES = "Charles the slave";
    public static final String VALID_PRICE_AMY = "11.11";
    public static final String VALID_PRICE_BOB = "22.20";
    public static final String VALID_DATE_2 = "02/01/2019";
    public static final String VALID_DATE_CHARLES = "03/01/2100";
    public static final String VALID_URL_AMY = "https://www.lazada.sg/products/"
            + "ps4-055-hori-real-arcade-prov-hayabusa-ps4ps3-i223784442-s340908953.html";
    public static final String VALID_ID_AMY = "6b46cf8e-adf5-4c39-8885-0a3131a80c9e";
    public static final String VALID_URL_BOB = "https://www.lazada.sg/products/"
            + "ps4-090-hori-real-arcade-prov-silent-hayabusaps4ps3-ps4-090-i223784443-s340908954.html";
    public static final String VALID_ID_BOB = "f0fc4af7-8631-4370-9554-ac2f31a22f29";
    public static final String VALID_URL_CHARLES = "https://www.amazon.com/apb/page?handlerName="
            + "OctopusDealLandingStream&deals=9d1efcc6&marketplaceId=ATVPDKIKX0DER&node=553836&ref_="
            + "Oct_DotdC_553836_1&pf_rd_p=bd1935b5-36da-5d2d-95f8-38548b4e530e&pf_rd_s="
            + "merchandised-search-4&pf_rd_t=101&pf_rd_i=553836&pf_rd_m=ATVPDKIKX0DER&pf_rd_r="
            + "9WBH12306J9M7GD5SAWX&pf_rd_r=9WBH12306J9M7GD5SAWX&pf_rd_p=bd1935b5-36da-5d2d-95f8-38548b4e530e";
    public static final String VALID_ID_CHARLES = "73078162-8103-4daf-9c1c-30bce2bfc4e5";

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_SAVED_AMOUNT_AMY = "11.11";
    public static final String VALID_SAVED_AMOUNT_BOB = "12.12";
    public static final Remark SAMPLE_REMARK_1 = new Remark("test remark");
    public static final Remark SAMPLE_REMARK_1B = new Remark("test remark");
    public static final Remark SAMPLE_REMARK_2 = new Remark("test remark 2");
    public static final Remark SAMPLE_REMARK_3 = new Remark("test remark 3");
    public static final Remark SAMPLE_REMARK_EMPTY = new Remark("");

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_CHARLES = " " + PREFIX_NAME + VALID_NAME_CHARLES;
    public static final String PRICE_DESC_AMY = " " + PREFIX_PRICE + VALID_PRICE_AMY;
    public static final String PRICE_DESC_BOB = " " + PREFIX_PRICE + VALID_PRICE_BOB;
    public static final String PRICE_DESC_CHARLES = " " + PREFIX_PRICE + VALID_PRICE_CHARLES;
    public static final String DATE_DESC_1 = " " + PREFIX_DATE + VALID_DATE_1;
    public static final String DATE_DESC_2 = " " + PREFIX_DATE + VALID_DATE_2;
    public static final String DATE_DESC_3 = " " + PREFIX_DATE + VALID_DATE_CHARLES;
    public static final String AGE_DESC_1 = " " + PREFIX_AGE + VALID_AGE_ALL_FIELDS;
    public static final String AGE_DESC_2 = " " + PREFIX_AGE + VALID_AGE_DAY;
    public static final String AGE_DESC_3 = " " + PREFIX_AGE + VALID_AGE_YEAR_DAY;
    public static final String URL_DESC_AMY = " " + PREFIX_URL + VALID_URL_AMY;
    public static final String URL_DESC_BOB = " " + PREFIX_URL + VALID_URL_BOB;
    public static final String URL_DESC_CHARLES = " " + PREFIX_URL + VALID_URL_CHARLES;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_NONE = " " + PREFIX_TAG;
    public static final String REMARK_DESC_SAMPLE_1 = " " + PREFIX_REMARK + SAMPLE_REMARK_1;
    public static final String REMARK_DESC_SAMPLE_2 = " " + PREFIX_REMARK + SAMPLE_REMARK_2;
    public static final String REMARK_DESC_EMPTY = " " + PREFIX_REMARK;

    public static final String INVALID_AGE_1 = "9d2y";

    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + INVALID_AGE_1; // days need to come after years
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "9..2"; // two decimal points not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "15/21/2034"; // 21 is not a valid month
    public static final String INVALID_URL_DESC = " " + PREFIX_URL + " asd asd"; // whitespace not allowed for urls
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SAVED_AMOUNT = "-11..11"; // two decimal points not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditWishDescriptor DESC_AMY;
    public static final EditCommand.EditWishDescriptor DESC_BOB;



    static {
        DESC_AMY = new EditWishDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPrice(VALID_PRICE_AMY).withDate(VALID_DATE_1).withAddress(VALID_URL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditWishDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPrice(VALID_PRICE_BOB).withDate(VALID_DATE_2).withAddress(VALID_URL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the wish book and the filtered wish list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WishBook expectedWishBook = new WishBook(actualModel.getWishBook());
        List<Wish> expectedFilteredList = new ArrayList<>(actualModel.getFilteredSortedWishList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedWishBook, actualModel.getWishBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredSortedWishList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the wish at the given {@code targetIndex} in the
     * {@code model}'s wish book.
     */
    public static void showWishAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredSortedWishList().size());

        Wish wish = model.getFilteredSortedWishList().get(targetIndex.getZeroBased());
        final String[] splitName = wish.getName().fullName.split("\\s+");
        model.updateFilteredWishList(new WishContainsKeywordsPredicate(Arrays.asList(splitName[0]), Arrays.asList(),
                Arrays.asList(), true));

        assertEquals(1, model.getFilteredSortedWishList().size());
    }

    /**
     * Deletes the first wish in {@code model}'s filtered list from {@code model}'s wish book.
     */
    public static void deleteFirstWish(Model model) {
        Wish firstWish = model.getFilteredSortedWishList().get(0);
        model.deleteWish(firstWish);
        model.commitWishBook();
    }

}
