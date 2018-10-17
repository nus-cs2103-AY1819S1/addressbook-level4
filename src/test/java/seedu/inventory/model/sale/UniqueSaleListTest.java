package seedu.inventory.model.sale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.exceptions.DuplicateSaleException;
import seedu.inventory.model.sale.exceptions.SaleNotFoundException;
import seedu.inventory.testutil.TypicalItems;

public class UniqueSaleListTest {
    private static SaleId saleId = new SaleId("1");
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueSaleList uniqueSaleList = new UniqueSaleList();

    @Test
    public void contains_nullSale_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSaleList.contains(null);
    }

    @Test
    public void contains_saleIdNotInList_returnsFalse() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        assertFalse(uniqueSaleList.contains(sale));
    }

    @Test
    public void contains_saleIdInList_returnsTrue() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        uniqueSaleList.add(sale);

        assertTrue(uniqueSaleList.contains(sale));
    }

    @Test
    public void add_nullSale_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSaleList.add(null);
    }

    @Test
    public void add_duplicateSale_throwsDuplicateSaleException() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        uniqueSaleList.add(sale);
        thrown.expect(DuplicateSaleException.class);
        uniqueSaleList.add(sale);
    }

    @Test
    public void remove_nullSale_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSaleList.remove(null);
    }

    @Test
    public void remove_saleDoesNotExist_throwsSaleNotFoundException() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        thrown.expect(SaleNotFoundException.class);
        uniqueSaleList.remove(sale);
    }

    @Test
    public void remove_existingSale_removesSale() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        uniqueSaleList.add(sale);
        uniqueSaleList.remove(sale);

        UniqueSaleList expectedUniqueItemList = new UniqueSaleList();

        assertEquals(expectedUniqueItemList, uniqueSaleList);
    }

    @Test
    public void setSales_nullUniqueSaleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSaleList.setSales((UniqueSaleList) null);
    }

    @Test
    public void seSales_uniqueSaleList_replacesOwnListWithProvidedUniqueSaleList() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        uniqueSaleList.add(sale);
        UniqueSaleList expectedUniqueSaleList = new UniqueSaleList();

        Sale sale1 = new Sale(new SaleId("2"), item, quantity, saleDate);

        expectedUniqueSaleList.add(sale1);

        uniqueSaleList.setSales(expectedUniqueSaleList);
        assertEquals(expectedUniqueSaleList, uniqueSaleList);
    }

    @Test
    public void setSales_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSaleList.setSales((List<Sale>) null);
    }

    @Test
    public void setSales_list_replacesOwnListWithProvidedList() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        uniqueSaleList.add(sale);

        Sale sale1 = new Sale(new SaleId("2"), item, quantity, saleDate);

        List<Sale> saleList = Collections.singletonList(sale1);
        uniqueSaleList.setSales(saleList);
        UniqueSaleList expectedUniqueSaleList = new UniqueSaleList();
        expectedUniqueSaleList.add(sale1);
        assertEquals(expectedUniqueSaleList, uniqueSaleList);
    }

    @Test
    public void setSales_listWithDuplicateSales_throwsDuplicateSaleException() {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        List<Sale> listWithDuplicateSales = Arrays.asList(sale, sale);
        thrown.expect(DuplicateSaleException.class);
        uniqueSaleList.setSales(listWithDuplicateSales);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueSaleList.asUnmodifiableObservableList().remove(0);
    }
}
