package seedu.address.model.google;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ALBUM_REQUESTED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE_REQUESTED;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.ListAlbumsPagedResponse;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.ListMediaItemsPagedResponse;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient.SearchMediaItemsPagedResponse;
import com.google.photos.library.v1.proto.Album;
import com.google.photos.library.v1.proto.MediaItem;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author chivent

/**
 * A middle-man giving access to Google Photos, translates commands to call appropriate methods.
 */
public class PhotoHandler {

    private PhotosLibraryClient photosLibraryClient;
    private String user;
    private Map<String, Album> albumMap = new HashMap<>();
    private Map<String, MediaItem> imageMap = new HashMap<>();
    private Map<String, MediaItem> albumSpecificMap = new HashMap<>();

    PhotoHandler(PhotosLibraryClient client, String email) {
        photosLibraryClient = client;
        user = email;
    }

    //=========== Listing Images (ls command) ================================
    /**
     * Returns a list of image names and saves a copy in albumSpecificMap for later reference
     * @return list of image names
     */
    public List<String> returnAllImagesList() {
        ArrayList<String> imageNames = new ArrayList<>();
        retrieveAllImagesFromGoogle();
        for (Map.Entry<String, MediaItem> entry : imageMap.entrySet()) {
            imageNames.add(entry.getKey());
        }
        return imageNames;
    }

    /**
     * Returns a list of album names and saves a copy in albumSpecificMap for later reference
     * @return list of album names
     */
    public List<String> returnAllAlbumsList() {

        ArrayList<String> albumNames = new ArrayList<>();
        retrieveAllAlbumsFromGoogle();
        for (Map.Entry<String, Album> entry : albumMap.entrySet()) {
            albumNames.add(entry.getKey());
        }
        return albumNames;
    }

    /**
     * Returns list of images in a specified album and saves a copy in albumSpecificMap for later reference
     * @param albumName name of album to look in
     * @return list of image names
     */
    public List<String> returnAllImagesinAlbum(String albumName) throws CommandException {

        ArrayList<String> imageNames = new ArrayList<>();

        retrieveSpecificAlbumGoogle(albumName);

        for (Map.Entry<String, MediaItem> entry : albumSpecificMap.entrySet()) {
            imageNames.add(entry.getKey());
        }

        return imageNames;
    }


    //=========== Retrieving Images from Google ================================
    /**
     * Retrieves all albums in Google Photos
     */
    private void retrieveAllAlbumsFromGoogle() {
        albumMap.clear();
        ListAlbumsPagedResponse albums = photosLibraryClient.listAlbums();
        for (Album album : albums.iterateAll()) {
            albumMap.put(getUniqueName(albumMap, album.getTitle()), album);
        }
    }

    /**
     * Retrieves all images in Google Photos
     */
    private void retrieveAllImagesFromGoogle() {
        imageMap.clear();
        ListMediaItemsPagedResponse listMediaItems = photosLibraryClient.listMediaItems();
        for (MediaItem item : listMediaItems.iterateAll()) {
            //only store the item if it is an image
            if (item.getMimeType().contains("image")) {
                imageMap.put(getUniqueName(imageMap, item.getFilename()), item);
            }
        }
    }

    /**
     * Retrieves all images in specified album
     * @param albumName name of album to retrieve
     */
    private void retrieveSpecificAlbumGoogle(String albumName) throws CommandException {
        Album album;

        // if album name not found, re-retrieve all albums (in case un-updated)
        if ((album = albumMap.get(albumName)) == null) {
            retrieveAllAlbumsFromGoogle();
            if ((album = albumMap.get(albumName)) == null) {
                throw new CommandException(MESSAGE_INVALID_ALBUM_REQUESTED);
            }
        }

        albumSpecificMap.clear();

        SearchMediaItemsPagedResponse listMediaItems = photosLibraryClient.searchMediaItems(album.getId());
        for (MediaItem item : listMediaItems.iterateAll()) {
            //only store the item if it is an image
            if (item.getMimeType().contains("image")) {
                albumSpecificMap.put(getUniqueName(albumSpecificMap, item.getFilename()), item);
            }
        }
    }

    //=========== Downloading Images from Google ================================

    /**
     * Downloads an image from Google Photos
     * @param imageName name of image to look for
     * @param currDir directory to save image in.
     * @throws IOException thrown if input/output is invalid
     */
    public void downloadImage(String imageName, String currDir) throws CommandException, IOException {

        MediaItem image;

        // if image name not found, re-retrieve album (in case un-updated)
        if ((image = imageMap.get(imageName)) == null) {
            retrieveAllImagesFromGoogle();
            if ((image = imageMap.get(imageName)) == null) {
                throw new CommandException(MESSAGE_INVALID_IMAGE_REQUESTED);
            }
        }

        saveImageInDir(image, currDir + "/" + imageName);
    }

    /**
     * Downloads an image from last opened album from Google Photos
     * @param albumName album to look in
     * @param imageName image to look for
     * @param currDir directory to save image in.
     * @throws IOException thrown if input/output is invalid
     */
    public void downloadAlbumImage(String albumName, String imageName, String currDir)
            throws CommandException, IOException {

        MediaItem image;
        retrieveSpecificAlbumGoogle(albumName);

        // if image name not found, re-retrieve album (in case un-updated)
        if ((image = albumSpecificMap.get(imageName)) == null) {
            throw new CommandException(MESSAGE_INVALID_IMAGE_REQUESTED);
        }

        saveImageInDir(image, currDir + "/" + imageName);
    }

    /**
     * Downloads all images from an album in Google Photos
     * @param albumName album to download
     * @param currDir directory to save image in.
     * @throws IOException thrown if input/output is invalid
     */
    public void downloadWholeAlbum(String albumName, String currDir) throws IOException, CommandException {

        retrieveSpecificAlbumGoogle(albumName);

        for (Map.Entry<String, MediaItem> entry : albumSpecificMap.entrySet()) {
            //only store the item if it is an image
            if (entry.getValue().getMimeType().contains("image")) {
                saveImageInDir(entry.getValue(), currDir + "/" + entry.getKey());
            }
        }

    }

    /**
     * Saves the specified image into the current opened directory
     * @param image MediaItem retrieved from Google Photos
     * @param pathName directory to save image in.
     * @throws IOException thrown if input/output is invalid
     */
    private void saveImageInDir(MediaItem image, String pathName) throws IOException {
        String extensionType = image.getMimeType().split("/")[1];
        BufferedImage newImage = ImageIO.read(new URL(image.getBaseUrl() + "=d"));
        ImageIO.write(newImage, extensionType, new File(pathName));
    }

    //=========== Misc ================================
    /**
     * Returns a string for user email
     * @return logged in user's email
     */
    public String identifyUser() {
        return user;
    }


    ///TODO: Ensure duplicate renaming doesn't replace other files.
    /**
     * As Album names can be duplicates in Google Photos, new names to display in CLI are appended with a
     * suitable number to differentiate albums
     * @param map Map to be comparing to
     * @param title Key to search for
     * @return new title to act as key in map
     */
    private String getUniqueName(Map map, String title) {
        String newTitle = title;
        int i = 1;
        while (map.get(newTitle) != null) {
            newTitle = title;
            title += " (" + i + ")";
            i++;
        }
        return newTitle;
    }

}
