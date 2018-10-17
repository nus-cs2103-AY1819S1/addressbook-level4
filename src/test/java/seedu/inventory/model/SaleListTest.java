package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;

public class SaleListTest {
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");
    private static SaleId saleId = new SaleId("1");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SaleList saleList = new SaleList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), saleList.getSaleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saleList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlySale_replacesData() {
        SaleList newData = new SaleList();

        Sale sale = new Sale(saleId, item, quantity, saleDate);

        newData.addSale(sale);

        saleList.resetData(newData);
        assertEquals(newData, saleList);
    }

    @Test
    public void hasSale_nullSale_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saleList.hasSale(null);
    }

    @Test
    public void hasSale_saleNotInSaleList_returnsFalse() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        assertFalse(saleList.hasSale(sale));
    }

    @Test
    public void hasSale_saleInSaleList_returnsTrue() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        saleList.addSale(sale);
        assertTrue(saleList.hasSale(sale));
    }

    @Test
    public void geSaleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        saleList.getSaleList().remove(0);
    }

    @Test
    public void geNextSaleId_correctNextId() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);
        saleList.addSale(sale);

        String expectedId = "2";

        assertEquals(saleList.getNextSaleId(), expectedId);
    }

    @Test
    public void geNextSaleId_incorrectNextId() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);
        saleList.addSale(sale);

        String wrongId = "1";

        assertNotEquals(saleList.getNextSaleId(), wrongId);
    }

}
