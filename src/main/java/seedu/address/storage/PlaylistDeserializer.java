package seedu.address.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import seedu.address.model.Playlist;
import seedu.address.model.Track;

/**
 * Deserializer for the playlist
 */
public class PlaylistDeserializer implements JsonDeserializer {

    @Override
    public Playlist deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final Playlist playlist = new Playlist(jsonObject.get("name").getAsString());
        final JsonArray jsonTracksArray = jsonObject.get("tracks").getAsJsonArray();
        final String[] tracks = new String[jsonTracksArray.size()];
        for (int i = 0; i < tracks.length; i++) {
            final JsonElement jsonTrack = jsonTracksArray.get(i);
            try {
                Track track = new Track(jsonTrack.getAsString());
                playlist.addTrack(track);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return playlist;
    }
}
