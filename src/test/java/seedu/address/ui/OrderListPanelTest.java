package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToOrderListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.order.Order;
import seedu.address.storage.XmlFoodZoom;

public class OrderListPanelTest extends GuiUnitTest {
    private static final ObservableList<Order> TYPICAL_ORDERS =
            FXCollections.observableList(getTypicalOrders());

    private static final JumpToOrderListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToOrderListRequestEvent(INDEX_SECOND);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private OrderListPanelHandle orderListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ORDERS);

        for (int i = 0; i < TYPICAL_ORDERS.size(); i++) {
            orderListPanelHandle.navigateToCard(TYPICAL_ORDERS.get(i));
            Order expectedOrder = TYPICAL_ORDERS.get(i);
            OrderCardHandle actualCard = orderListPanelHandle.getOrderCardHandle(i);

            assertCardDisplaysOrder(expectedOrder, actualCard);
            assertEquals("#" + Integer.toString(i + 1), actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_ORDERS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        OrderCardHandle expectedPerson = orderListPanelHandle.getOrderCardHandle(INDEX_SECOND.getZeroBased());
        OrderCardHandle selectedPerson = orderListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code OrderListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Order> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of common cards exceeded time limit");
    }

    /**
     * Returns a list of orders containing {@code orderCount} orders that is used to populate the
     * {@code OrderListPanel}.
     */
    private ObservableList<Order> createBackingList(int orderCount) throws Exception {
        Path xmlFile = createXmlFileWithOrders(orderCount);
        XmlFoodZoom xmlFoodZoom =
                XmlUtil.getDataFromFile(xmlFile, XmlFoodZoom.class);
        return FXCollections.observableArrayList(xmlFoodZoom.getOrderBook().getOrderList());
    }

    /**
     * Returns a .xml file containing {@code orderCount} orders. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithOrders(int orderCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<foodzoom>\n");
        builder.append("<orderbook>\n");
        for (int i = 0; i < orderCount; i++) {
            builder.append("<orders tag=\"" + UUID.randomUUID().toString() + "\">\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<address>a 612234</address>\n");
            builder.append("<date>01-10-2018 10:00:00</date>\n");
            builder.append("<status>PENDING</status>\n");
            builder.append("<food>milo</food>\n");
            builder.append("</orders>\n");
        }
        builder.append("</orderbook>\n");
        builder.append("<deliverymenList>\n");
        builder.append("</deliverymenList>\n");
        builder.append("</foodzoom>\n");

        Path manyOrdersFile = Paths.get(TEST_DATA_FOLDER + "manyOrders.xml");
        FileUtil.createFile(manyOrdersFile);
        FileUtil.writeToFile(manyOrdersFile, builder.toString());
        manyOrdersFile.toFile().deleteOnExit();
        return manyOrdersFile;
    }

    /**
     * Initializes {@code orderListPanelHandle} with a {@code OrderListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code OrderListPanel}.
     */
    private void initUi(ObservableList<Order> backingList) {
        OrderListPanel orderListPanel = new OrderListPanel(backingList);
        uiPartRule.setUiPart(orderListPanel);

        orderListPanelHandle = new OrderListPanelHandle(getChildNode(orderListPanel.getRoot(),
                OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }
}
