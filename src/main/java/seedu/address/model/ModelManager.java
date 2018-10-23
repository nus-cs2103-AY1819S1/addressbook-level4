package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_FAILURE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.google.PhotoHandler;
import seedu.address.model.google.PhotosLibraryClientFactory;
import seedu.address.model.person.Person;
import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PreviewImageManager previewImageManager;
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private ArrayList<Path> dirImageList;
    private Path currentOriginalImage;
    private Path currentPreviewImage;
    private PhotoHandler photoLibrary = null;
    private Canvas canvas;

    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        previewImageManager = PreviewImageManager.getInstance();
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());

        this.userPrefs = userPrefs;
        dirImageList = new ArrayList<>();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Directory Image List Accessors =============================================================

    /**
     * Returns an array list of the images from the current directory {@code dirImageList}
     * backed by the list of {@code userPrefs}
     */
    @Override
    public ArrayList<Path> getDirectoryImageList() {
        this.dirImageList = userPrefs.getAllImages();
        return this.dirImageList;
    }

    /**
     * Updates the list of the images in the current directory {@code dirImageList}
     */
    @Override
    public void updateImageList() {
        userPrefs.updateImageList();
    }

    /**
     * Updates the list of the images in the current directory {@code dirImageList} with the {@code dirImageList}
     */
    @Override
    public void updateImageList(ArrayList<Path> dirImageList) {
        userPrefs.updateImageList(dirImageList);
    }

    /**
     * Remove image from {@code dirImageList} at the given {@code idx}
     */
    @Override
    public void removeImageFromList(int idx) {
        this.dirImageList.remove(idx);
    }

    @Override
    public Path getCurrentOriginalImage() {
        return this.currentOriginalImage;
    }

    @Override
    public Path getCurrentPreviewImage() {
        return this.currentPreviewImage;
    }

    //author lancelotwillow
    @Override
    public void updateCurrentpreviewImage(Image image, Transformation transformation) {
        TransformationSet transformationSet;
        if (currentPreviewImage != null) {
            transformationSet = currentPreviewImage.getTransformationSet();
        } else {
            transformationSet = new TransformationSet();
        }
        transformationSet.addTransformations(transformation);
        this.currentPreviewImage = new PreviewImage(SwingFXUtils.fromFXImage(image, null), transformationSet);
    }

    /**
     * Update the current displayed original image and
     * reinitialize the previewImageManager with the new image
     */
    @Override
    public void updateCurrentOriginalImage(Image img, Path imgPath) {
        canvas = new Canvas();
        currentOriginalImage = imgPath;
        currentPreviewImage = imgPath;

        PreviewImage selectedImage = new PreviewImage(SwingFXUtils.fromFXImage(img, null));
        canvas.addLayer(selectedImage);

        previewImageManager.initialiseWithImage(selectedImage);
    }
    //=========== GoogleClient Accessors =============================================================

    @Override
    public void setPhotoHandler(PhotoHandler instance) {
        photoLibrary = instance;
    }

    @Override
    public PhotoHandler getPhotoHandler() throws CommandException {
        if (photoLibrary == null) {
            try {
                photoLibrary = PhotosLibraryClientFactory.createClient();
            } catch (Exception e) {
                throw new CommandException(MESSAGE_LOGIN_FAILURE);
            }
        }
        return photoLibrary;
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoPreviewImageManager() {
        return previewImageManager.canUndo();
    }

    @Override
    public boolean canRedoPreviewImageManager() {
        return previewImageManager.canRedo();
    }

    @Override
    public void undoPreviewImageManager() {
        previewImageManager.undo();
        currentPreviewImage = Paths.get(previewImageManager.getCurrentPreviewImageState().toString());
    }

    @Override
    public void redoPreviewImageManager() {
        previewImageManager.redo();
        currentPreviewImage = Paths.get(previewImageManager.getCurrentPreviewImageState().toString());
    }

    @Override
    public void commitPreviewImageManager() {
        // TODO: update currentPreviewImage
        // TODO: previewImageManager.commit(editedImage);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Update UserPrefs ==========================================================================

    @Override
    public void updateCurrDirectory(Path newCurrDirectory) {
        this.userPrefs.updateUserPrefs(newCurrDirectory);
    }

    @Override
    public Path getCurrDirectory() {
        return this.userPrefs.getCurrDirectory();
    }
}
