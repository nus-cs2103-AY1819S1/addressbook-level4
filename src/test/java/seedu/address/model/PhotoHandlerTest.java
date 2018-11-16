package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.google.PhotoHandler.PICONSO_ALBUM;
import static seedu.address.model.google.PhotoHandler.UPLOAD_FORMAT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.photos.library.v1.proto.Album;
import com.google.photos.library.v1.proto.MediaItem;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.google.PhotoHandler;

//@@author chivent

/**
 * Test for {@code PhotoHandler}, most functions cannot be tested for success due to a lack of a google account.
 */
public class PhotoHandlerTest {

    private String user = "user";
    private String extension = ".png";
    private String dummyName = "fake.png";
    private PhotoHandler photoHandler;
    private Map<String, String> dummyMap = new HashMap<>();
    private Map<String, Album> albumMap = new HashMap<>();
    private Map<String, MediaItem> imageMap = new HashMap<>();

    @Before
    public void setUp() {
        dummyMap.put("item 1.png", "1");
        dummyMap.put("item 2 (1).png", "2a");
        dummyMap.put("item 2.png", "2");
        dummyMap.put("item 3 (1).png", "3a");
        dummyMap.put("item 3 (2).png", "3b");
        dummyMap.put("item 3.png", "3");
        dummyMap.put("Album 1", "3");

        albumSetup();
        imageSetup();
        photoHandler = new PhotoHandler(user, albumMap, imageMap);
    }

    @Test
    public void returnAllImagesListWithRetrieval() {
        // test images cannot be retrieved without photoHandler
        Exception ex = null;
        PhotoHandler emptyPhotoHandler = new PhotoHandler(null, "user");
        try {
            emptyPhotoHandler.returnAllImagesList();
        } catch (NullPointerException nullPtr) {
            ex = nullPtr;
        }
        assertNotNull(ex);
    }

    @Test
    public void returnAllImagesList() {
        ArrayList<String> imageNames = new ArrayList<>();
        imageNames.addAll(imageMap.keySet());
        assertEquals(imageNames, photoHandler.returnAllImagesList());
    }

    @Test
    public void returnAllAlbumsWithRetrieval() {
        // test images cannot be retrieved without photoHandler
        Exception ex = null;
        PhotoHandler emptyPhotoHandler = new PhotoHandler(null, "user");
        try {
            emptyPhotoHandler.returnAllAlbumsList();
        } catch (NullPointerException nullPtr) {
            ex = nullPtr;
        }
        assertNotNull(ex);
    }

    @Test
    public void returnAllAlbumsList() {
        ArrayList<String> albumNames = new ArrayList<>();
        albumNames.addAll(albumMap.keySet());
        assertEquals(albumNames, photoHandler.returnAllAlbumsList());
    }

    @Test
    public void returnAllImagesInAlbum() {
        // test images cannot be retrieved without map
        Exception ex = null;
        PhotoHandler emptyPhotoHandler = new PhotoHandler(null, "user");
        try {
            emptyPhotoHandler.returnAllImagesinAlbum("album");
        } catch (Exception nullPtr) {
            ex = nullPtr;
        }
        assertNotNull(ex);

        // test images cannot be retrieved without photoHandler
        try {
            photoHandler.returnAllImagesinAlbum("Album 1");
        } catch (Exception nullPtr) {
            ex = nullPtr;
        }
        assertNotNull(ex);
    }

    @Test
    public void refreshImageExceptions() {
        // test images cannot be retrieved without photoHandler
        Exception ex = null;
        try {
            photoHandler.refreshLists();
        } catch (Exception nullPtr) {
            ex = nullPtr;
        }
        assertNotNull(ex);
    }

    @Test
    public void uploadImageExceptions() {
        Exception compare;

        //InvalidPath
        try {
            photoHandler.uploadImage(dummyName, "/sda/");
        } catch (Exception ex) {
            assertEquals("fake.png does not exist in folder!", ex.getMessage());
        }

        compare = null;
        // no PhotoLibraryClientInstance
        try {
            Path path = Paths.get("src", "test", "resources", "testimgs");
            photoHandler.uploadImage("test2.png", path.toString());
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);

        compare = null;
        //Upload all images no PhotoLibraryClientInstance
        try {
            Path path = Paths.get("src", "test", "resources", "testimgs");
            photoHandler.uploadAll(path.toString());
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);
    }

    @Test
    public void downloadImageExceptions() throws IOException, CommandException {
        Exception compare = null;

        //-------------Dl one image -----------------
        // Fails to download
        try {
            photoHandler.downloadImage("item.png", "/sda/");
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof MalformedURLException);

        // Invalid download
        try {
            photoHandler.downloadImage(dummyName, "/sda/");
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);

        //------------ Dl Album ----------------------
        //Album - Invalid download
        try {
            photoHandler.downloadWholeAlbum(dummyName, "/sda/");
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);

        // ---------- Dl Image from Album -------------
        //Image from Album - Invalid image
        try {
            photoHandler.downloadAlbumImage("Album 1", dummyName, "/sda/");
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);
        //Image from Album - Invalid album
        try {
            photoHandler.downloadAlbumImage(dummyName, "item.png", "/sda/");
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);
        //Image from Album - Invalid album and image
        try {
            photoHandler.downloadAlbumImage(dummyName, dummyName, "/sda/");
        } catch (Exception ex) {
            compare = ex;
        }
        assertTrue(compare instanceof NullPointerException);
    }

    @Test
    public void getUserName() {
        assertEquals(user, photoHandler.identifyUser());
    }

    @Test
    public void retrievePiconsoAlbum() {
        assertEquals(albumMap.get(PICONSO_ALBUM).getId(), photoHandler.retrievePiconsoAlbum());

        albumMap.remove(PICONSO_ALBUM);
        assertEquals("", photoHandler.retrievePiconsoAlbum());
    }

    @Test
    public void uploadMessageFormatting() {
        String[] images = {"img1", "img2"};
        Map<Integer, String> uploads = new HashMap<>();

        //Test all duplicates
        assertEquals("", photoHandler.formatUploadFeedback(uploads, Arrays.asList(images)));

        //Test some duplicates
        uploads.put(0, images[0]);
        String expected = String.format(UPLOAD_FORMAT, images[0], uploads.get(0));
        assertEquals(expected, photoHandler.formatUploadFeedback(uploads, Arrays.asList(images)));

        //Test no duplicates
        uploads.put(1, images[1]);
        expected = ".all" + String.format(UPLOAD_FORMAT, images[0], uploads.get(0))
                + String.format(UPLOAD_FORMAT, images[1], uploads.get(1));
        assertEquals(expected, photoHandler.formatUploadFeedback(uploads, Arrays.asList(images)));
    }

    @Test
    public void getUniqueName() {
        // try non existing item
        assertEquals("item.png", photoHandler.getUniqueName(dummyMap, "item.png", extension));

        // try existing item
        assertEquals("item 1 (1).png", photoHandler.getUniqueName(dummyMap, "item 1.png", extension));

        // try existing 2 items
        assertEquals("item 2 (2).png", photoHandler.getUniqueName(dummyMap, "item 2.png", extension));

        // try existing 3 items
        assertEquals("item 3 (3).png", photoHandler.getUniqueName(dummyMap, "item 3.png", extension));

        // try existing item () name
        assertEquals("item 3 (1) (1).png", photoHandler.getUniqueName(dummyMap, "item 3 (1).png", extension));

        // test without extension
        assertEquals("Album", photoHandler.getUniqueName(dummyMap, "Album", null));
        assertEquals("Album 1 (1)", photoHandler.getUniqueName(dummyMap, "Album 1", null));
    }

    /**
     * Setup of albumMap
     */
    private void albumSetup() {
        Album album = Album.newBuilder().setTitle("Album 1").build();
        albumMap.put(album.getTitle(), album);

        album = Album.newBuilder().setTitle("Album 2").build();
        albumMap.put(album.getTitle(), album);

        album = Album.newBuilder().setTitle("Album 3").build();
        albumMap.put(album.getTitle(), album);

        album = Album.newBuilder().setTitle(PICONSO_ALBUM).setId("1").build();
        albumMap.put(album.getTitle(), album);
    }

    /**
     * Setup of imageMap
     */
    private void imageSetup() {
        String mimeType = "image/png";
        MediaItem mediaItem = MediaItem.newBuilder().setFilename("item.png")
                .setMimeType(mimeType)
                .build();
        imageMap.put(mediaItem.getFilename(), mediaItem);

        mediaItem = MediaItem.newBuilder().setFilename("item 2.png")
                .setMimeType(mimeType)
                .build();
        imageMap.put(mediaItem.getFilename(), mediaItem);

        mediaItem = MediaItem.newBuilder().setFilename("item 3 (1).png")
                .setMimeType(mimeType)
                .build();
        imageMap.put(mediaItem.getFilename(), mediaItem);

        mediaItem = MediaItem.newBuilder().setFilename("item 3.png")
                .setMimeType(mimeType)
                .build();
        imageMap.put(mediaItem.getFilename(), mediaItem);
    }
}
