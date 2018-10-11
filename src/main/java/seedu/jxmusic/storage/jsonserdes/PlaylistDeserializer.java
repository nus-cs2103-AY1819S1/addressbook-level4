package seedu.jxmusic.storage.jsonserdes;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Deserializer for the playlist
 */
public class PlaylistDeserializer implements JsonDeserializer {

    @Override
    public Playlist deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        Name playlistname = new Name(jsonObject.get("name").getAsString());
        final Playlist playlist = new Playlist(playlistname);
        final JsonArray jsonTracksArray = jsonObject.get("tracks").getAsJsonArray();
        final String[] tracks = new String[jsonTracksArray.size()];
        for (int i = 0; i < tracks.length; i++) {
            final JsonElement jsonTrack = jsonTracksArray.get(i);
            try {
                Name trackname = new Name(jsonTrack.getAsString());
                Track track = new Track(trackname);
                playlist.addTrack(track);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return playlist;
    }
}
