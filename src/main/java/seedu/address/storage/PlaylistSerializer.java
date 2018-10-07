package seedu.address.storage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import seedu.address.model.Playlist;
import seedu.address.model.Track;

/**
 * Serializes the playlists.
 */
public class PlaylistSerializer implements JsonSerializer<Playlist> {
    @Override
    public JsonElement serialize(final Playlist playlist, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", playlist.getName());

        final JsonArray jsonTracksArray = new JsonArray();
//        playlist.getTracks().stream().map(Track::getFileName).collect(Collectors.toList());
        List<String> filenames = new ArrayList<>();
        for (Track t : playlist.getTracks()) {
            filenames.add(t.getFileName());
        }
        for (final String track : filenames) {
            final JsonPrimitive jsonTrack = new JsonPrimitive(track);
            jsonTracksArray.add(jsonTrack);
        }
        jsonObject.add("tracks", jsonTracksArray);

        return jsonObject;
    }
}
