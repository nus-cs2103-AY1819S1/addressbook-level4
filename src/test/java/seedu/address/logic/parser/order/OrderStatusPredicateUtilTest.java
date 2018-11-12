package seedu.address.logic.parser.order;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderStatus;

public class OrderStatusPredicateUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_multipleValidStringStatus_returnStatusList() throws ParseException {
        List<OrderStatus> expectedOrderStatuses = Arrays.asList(new OrderStatus("PENDING"), new OrderStatus("ONGOING"));

        List<String> stringStatuses = Arrays.asList("PENDING", "ONGOING");
        List<OrderStatus> statuses = new OrderStatusPredicateUtil().parseOrderStatusKeywords(stringStatuses);

        assertEquals(expectedOrderStatuses, statuses);
    }

    @Test
    public void test_mixedCaseStringStatuses_returnValidStatusList() throws ParseException {
        List<OrderStatus> expectedOrderStatuses = Arrays.asList(new OrderStatus("PENDING"));

        List<String> stringStatuses = Arrays.asList("pENdiNG");
        List<OrderStatus> statuses = new OrderStatusPredicateUtil().parseOrderStatusKeywords(stringStatuses);

        assertEquals(expectedOrderStatuses, statuses);
    }

    @Test
    public void test_invalidStatusGiven_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);

        List<String> stringStatuses = Arrays.asList("invalidStatus");
        List<OrderStatus> statuses = new OrderStatusPredicateUtil().parseOrderStatusKeywords(stringStatuses);
    }
}
