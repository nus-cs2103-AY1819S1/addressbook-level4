package seedu.inventory.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.model.ItemListExportEvent;
import seedu.inventory.commons.events.model.ItemListImportEvent;
import seedu.inventory.commons.events.model.SaleListChangedEvent;
import seedu.inventory.commons.events.model.StaffListChangedEvent;
import seedu.inventory.commons.events.storage.DataExportingExceptionEvent;
import seedu.inventory.commons.events.storage.DataExportingSuccessEvent;
import seedu.inventory.commons.events.storage.DataImportingExceptionEvent;
import seedu.inventory.commons.events.storage.DataImportingSuccessEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.commons.events.storage.ItemListUpdateEvent;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.UserPrefs;

/**
 * Manages storage of Inventory data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InventoryStorage inventoryStorage;
    private UserPrefsStorage userPrefsStorage;
    private SaleListStorage saleListStorage;
    private ReportingStorage reportingStorage;

    public StorageManager(InventoryStorage inventoryStorage, UserPrefsStorage userPrefsStorage,
                          SaleListStorage saleListStorage, ReportingStorage reportingStorage) {
        super();
        this.inventoryStorage = inventoryStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.saleListStorage = saleListStorage;
        this.reportingStorage = reportingStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Inventory methods ==============================

    @Override
    public Path getInventoryFilePath() {
        return inventoryStorage.getInventoryFilePath();
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException {
        return readInventory(inventoryStorage.getInventoryFilePath());
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return inventoryStorage.readInventory(filePath);
    }

    @Override
    public void saveInventory(ReadOnlyInventory inventory) throws IOException {
        saveInventory(inventory, inventoryStorage.getInventoryFilePath());
    }

    @Override
    public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        inventoryStorage.saveInventory(inventory, filePath);
    }

    // ================ Sale List methods ==============================

    @Override
    public Path getSaleListFilePath() {
        return saleListStorage.getSaleListFilePath();
    }

    @Override
    public Optional<ReadOnlySaleList> readSaleList(ReadOnlyInventory inventory) throws DataConversionException,
            IOException {
        return readSaleList(saleListStorage.getSaleListFilePath(), inventory);
    }

    @Override
    public Optional<ReadOnlySaleList> readSaleList(Path filePath, ReadOnlyInventory inventory)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return saleListStorage.readSaleList(filePath, inventory);
    }

    @Override
    public void saveSaleList(ReadOnlySaleList saleList) throws IOException {
        saveSaleList(saleList, saleListStorage.getSaleListFilePath());
    }

    @Override
    public void saveSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        saleListStorage.saveSaleList(saleList, filePath);
    }

    // ================ Reporting methods ==============================
    @Override
    public Optional<ReadOnlyItemList> importItemList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to import item list from file: " + filePath);
        return reportingStorage.importItemList(filePath);
    }

    @Override
    public void exportItemList(ReadOnlyItemList itemList, Path filePath) throws IOException {
        logger.fine("Attempting to export item list to file: " + filePath);
        reportingStorage.exportItemList(itemList, filePath);
    }

    @Override
    public Optional<ReadOnlySaleList> importSaleList(ReadOnlyInventory inventory, Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to import sale list from file: " + filePath);
        return reportingStorage.importSaleList(inventory, filePath);
    }

    @Override
    public void exportSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException {
        logger.fine("Attempting to export sale list to file: " + filePath);
        reportingStorage.exportSaleList(saleList, filePath);
    }


    // ================ Event handler ==================================

    @Override
    @Subscribe
    public void handleSaleListChangedEvent(SaleListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveSaleList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleStaffListChangedEvent(StaffListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Staff data changed, saving to file"));
        try {
            saveStaffList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleInventoryChangedEvent(InventoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveInventory(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleItemListExportEvent(ItemListExportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Export item list to file"));
        try {
            exportItemList(event.data, event.filePath);
            raise(new DataExportingSuccessEvent());
        } catch (IOException e) {
            raise(new DataExportingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleItemListImportEvent(ItemListImportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Import item list from file"));
        try {
            Optional<ReadOnlyItemList> itemList = importItemList(event.filePath);
            if (itemList.isPresent()) {
                raise(new ItemListUpdateEvent(itemList.get()));
                raise(new DataImportingSuccessEvent());
            } else {
                raise(new DataImportingExceptionEvent(new FileNotFoundException()));
            }
        } catch (IOException ioe) {
            raise(new DataImportingExceptionEvent(ioe));
        } catch (DataConversionException dce) {
            raise(new DataImportingExceptionEvent(dce));
        }
    }

    // ================ Staffs methods ==============================

    @Override
    public Path getStaffListFilePath() {
        return inventoryStorage.getStaffListFilePath();
    }

    @Override
    public Optional<ReadOnlyStaffList> readStaffList() throws DataConversionException, IOException {
        return readStaffList(inventoryStorage.getStaffListFilePath());
    }

    @Override
    public Optional<ReadOnlyStaffList> readStaffList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return inventoryStorage.readStaffList(filePath);
    }

    @Override
    public void saveStaffList(ReadOnlyStaffList staffList) throws IOException {
        saveStaffList(staffList, inventoryStorage.getStaffListFilePath());
    }

    @Override
    public void saveStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        inventoryStorage.saveStaffList(staffList, filePath);
    }
}
