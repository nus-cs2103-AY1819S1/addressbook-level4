package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.OrderBook;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.storage.XmlAdaptedFood;
import seedu.address.storage.XmlAdaptedOrder;
import seedu.address.storage.XmlFoodZoom;
import seedu.address.storage.XmlSerializableOrderBook;
import seedu.address.storage.deliveryman.XmlAdaptedDeliveryman;
import seedu.address.storage.deliveryman.XmlSerializableDeliverymenList;
import seedu.address.testutil.DeliverymanBuilder;
import seedu.address.testutil.DeliverymenListBuilder;
import seedu.address.testutil.OrderBookBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validOrderBook.xml");
    private static final Path VALID_DELIVERYMEN_LIST_FILE = TEST_DATA_FOLDER.resolve("validDeliverymenList.xml");
    private static final Path MISSING_ORDER_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingOrderField.xml");
    private static final Path INVALID_ORDER_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidOrderField.xml");
    private static final Path INVALID_DELIVERYMAN_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidDeliverymanField.xml");
    private static final Path VALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("validOrder.xml");
    private static final Path VALID_DELIVERYMAN_FILE = TEST_DATA_FOLDER.resolve("validDeliveryman.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempOrderBook.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_ID = "1650dbab-e584-4e7f-87dc-fcac93a2aea9";
    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_ADDRESS = "4th street, 612234";
    private static final String VALID_STATUS = "PENDING";
    private static final String VALID_DATE = "01-10-2018 10:00:00";
    private static final List<XmlAdaptedFood> VALID_FOOD =
        Collections.singletonList(new XmlAdaptedFood("milo"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, OrderBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, OrderBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, OrderBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        OrderBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE,
                XmlSerializableOrderBookWithRootElement.class).toModelType();
        assertEquals(9, dataFromFile.getOrderList().size());
        DeliverymenList deliverymenDataFromFile = XmlUtil.getDataFromFile(VALID_DELIVERYMEN_LIST_FILE,
                XmlSerializableDeliverymenListWithRootElement.class).toModelType();
        assertEquals(2, deliverymenDataFromFile.getDeliverymenList().size());
    }

    @Test
    public void xmlAdaptedOrderFromFile_fileWithMissingOrderField_validResult() throws Exception {
        XmlAdaptedOrder actualOrder = XmlUtil.getDataFromFile(
                MISSING_ORDER_FIELD_FILE, XmlAdaptedOrderWithRootElement.class);
        XmlAdaptedOrder expectedOrder = new XmlAdaptedOrder(
                VALID_ID, null, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void xmlAdaptedOrderFromFile_fileWithInvalidOrderField_validResult() throws Exception {
        XmlAdaptedOrder actualOrder = XmlUtil.getDataFromFile(
                INVALID_ORDER_FIELD_FILE, XmlAdaptedOrderWithRootElement.class);
        XmlAdaptedOrder expectedOrder = new XmlAdaptedOrder(
                VALID_ID, VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);
        assertEquals(expectedOrder, actualOrder);

        XmlAdaptedDeliveryman actualDeliveryman = XmlUtil.getDataFromFile(
            INVALID_DELIVERYMAN_FIELD_FILE, XmlAdaptedDeliverymanWithRootElement.class);
        XmlAdaptedDeliveryman expectedDeliveryman = new XmlAdaptedDeliveryman(VALID_NAME);
        assertNotEquals(expectedDeliveryman, actualDeliveryman);
    }

    @Test
    public void xmlAdaptedOrderFromFile_fileWithValidOrder_validResult() throws Exception {
        XmlAdaptedOrder actualOrder = XmlUtil.getDataFromFile(
                VALID_ORDER_FILE, XmlAdaptedOrderWithRootElement.class);
        XmlAdaptedOrder expectedOrder = new XmlAdaptedOrder(
                VALID_ID, VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);

        System.out.println(actualOrder.toString());
        System.out.println(expectedOrder.toString());

        assertEquals(expectedOrder, actualOrder);

        XmlAdaptedDeliveryman actualDeliveryman = XmlUtil.getDataFromFile(
            VALID_DELIVERYMAN_FILE, XmlAdaptedDeliverymanWithRootElement.class);
        XmlAdaptedDeliveryman expectedDeliveryman = new XmlAdaptedDeliveryman(VALID_ID, VALID_NAME, null);
        assertEquals(expectedDeliveryman, actualDeliveryman);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new OrderBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new OrderBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlFoodZoom dataToWrite = new XmlFoodZoom(new OrderBook(), new DeliverymenList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlFoodZoom dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE,
            XmlFoodZoom.class);
        assertEquals(new OrderBook(), dataFromFile.getOrderBook());
        assertEquals(new DeliverymenList(), dataFromFile.getDeliverymenList());

        OrderBookBuilder builder = new OrderBookBuilder(new OrderBook()).withOrder(new OrderBuilder().build());
        DeliverymenListBuilder dBuilder = new DeliverymenListBuilder(new DeliverymenList())
            .withDeliveryman(new DeliverymanBuilder().build());
        dataToWrite = new XmlFoodZoom(builder.build(), dBuilder.build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlFoodZoom.class);
        assertEquals(dataToWrite.getOrderBook(), dataFromFile.getOrderBook());
        assertEquals(dataToWrite.getDeliverymenList(), dataFromFile.getDeliverymenList());

    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedOrder}
     * objects.
     */
    @XmlRootElement(name = "order")
    private static class XmlAdaptedOrderWithRootElement extends XmlAdaptedOrder {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedDeliveryman} objects.
     */
    @XmlRootElement(name = "deliveryman")
    private static class XmlAdaptedDeliverymanWithRootElement extends XmlAdaptedDeliveryman {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedDeliveryman} objects.
     */
    @XmlRootElement(name = "deliverymenlist")
    private static class XmlSerializableDeliverymenListWithRootElement extends XmlSerializableDeliverymenList {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedDeliveryman} objects.
     */
    @XmlRootElement(name = "orderbook")
    private static class XmlSerializableOrderBookWithRootElement extends XmlSerializableOrderBook {
    }
}
