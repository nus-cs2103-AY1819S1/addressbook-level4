package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.model.SaleListChangedEvent;
import seedu.inventory.commons.events.model.StaffListChangedEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
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
    private StaffStorage staffStorage;
    private SaleListStorage saleListStorage;


    public StorageManager(InventoryStorage inventoryStorage, UserPrefsStorage userPrefsStorage,
                          SaleListStorage saleListStorage, StaffStorage staffStorage) {
        super();
        this.inventoryStorage = inventoryStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.saleListStorage = saleListStorage;
        this.staffStorage = staffStorage;
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveStaffList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ Staffs methods ==============================

    @Override
    public Path getStaffListFilePath() {
        return staffStorage.getStaffListFilePath();
    }

    @Override
    public Optional<ReadOnlyStaffList> readStaffList() throws DataConversionException, IOException {
        return readStaffList(staffStorage.getStaffListFilePath());
    }

    @Override
    public Optional<ReadOnlyStaffList> readStaffList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return staffStorage.readStaffList(filePath);
    }

    @Override
    public void saveStaffList(ReadOnlyStaffList staffList) throws IOException {
        saveStaffList(staffList, staffStorage.getStaffListFilePath());
    }

    @Override
    public void saveStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        staffStorage.saveStaffList(staffList, filePath);
    }
}
