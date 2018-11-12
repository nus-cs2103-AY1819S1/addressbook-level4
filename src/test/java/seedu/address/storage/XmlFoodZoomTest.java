package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Streams;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.OrderBook;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.testutil.TypicalDeliverymen;
import seedu.address.testutil.TypicalOrders;

public class XmlFoodZoomTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlFoodZoomTest");
    private static final Path TYPICAL_FOODZOOM_FILE = TEST_DATA_FOLDER.resolve("typicalFoodZoom.xml");
    private static final Path INVALID_FOODZOOM_FILE = TEST_DATA_FOLDER.resolve("invalidFoodZoom.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getOrdersAndDeliverymen_typicalFoodZoom_success() throws Exception {
        XmlFoodZoom dataFromFile = XmlUtil.getDataFromFile(TYPICAL_FOODZOOM_FILE,
            XmlFoodZoom.class);
        OrderBook orderBookFromFile = dataFromFile.getOrderBook();
        OrderBook typicalOrdersOrderBook = TypicalOrders.getTypicalOrderBook();
        DeliverymenList deliverymenListFromFile = dataFromFile.getDeliverymenList();
        DeliverymenList typicalDeliverymenDeliverymenList = TypicalDeliverymen.getTypicalDeliverymenList();
        assertEquals(orderBookFromFile, typicalOrdersOrderBook);
        assertTrue(Streams.zip(orderBookFromFile.getOrderList().stream(),
            typicalOrdersOrderBook.getOrderList().stream(), (a, b) -> a.hasSameTag(b)).allMatch(x -> x));
        assertEquals(deliverymenListFromFile, typicalDeliverymenDeliverymenList);
        assertTrue(Streams.zip(deliverymenListFromFile.getDeliverymenList().stream(),
            typicalDeliverymenDeliverymenList.getDeliverymenList().stream(), (a, b) -> a.hasSameTag(b))
            .allMatch(x -> x));
    }

    @Test
    public void getOrdersAndDeliverymen_invalidFile_throwsIllegalValueException() throws Exception {
        XmlFoodZoom dataFromFile = XmlUtil.getDataFromFile(INVALID_FOODZOOM_FILE, XmlFoodZoom.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.getOrderBook();
    }
}
