package seedu.address.model.google;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ALBUM_REQUESTED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Album album;

        // if album name not found, re-retrieve all albums (in case un-updated)
        if ((album = albumMap.get(albumName)) == null) {
            retrieveAllAlbumsFromGoogle();
            if ((album = albumMap.get(albumName)) == null) {
                throw new CommandException(MESSAGE_INVALID_ALBUM_REQUESTED);
            }
        }

        retrieveSpecificAlbumGoogle(album.getId());

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
     */
    private void retrieveSpecificAlbumGoogle(String albumName) {
        albumSpecificMap.clear();

        SearchMediaItemsPagedResponse listMediaItems = photosLibraryClient.searchMediaItems(albumName);
        for (MediaItem item : listMediaItems.iterateAll()) {
            //only store the item if it is an image
            if (item.getMimeType().contains("image")) {
                albumSpecificMap.put(getUniqueName(albumSpecificMap, item.getFilename()), item);
            }
        }
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
