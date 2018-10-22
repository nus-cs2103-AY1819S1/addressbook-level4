package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * @@author lancelotwillow
 * this class is to convert the joson file that store the convert arguments to list[String], and store the convert
 */
public class JsonConvertArgsStorage {

    public static void storeArgument(String name, List<String> cmds) {
    }

    /**
     * get the template of the arguments need for the operation
     * @param filepath
     * @param operation
     * @return
     * @throws IOException
     */
    public static List<String> retrieveArgumentsTemplate(Path filepath, String operation) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(filepath.toFile());
        List<String> args = new ArrayList<>();
        if (jsonNode.get(operation) == null) {
            throw new IOException("no command found");
        }
        int num = jsonNode.get(operation).get("num").asInt();
        for (int i = 1; i <= num; i++) {
            String arg = jsonNode.get(operation).get("args").get("arg" + i).textValue();
            args.add(arg);
        }
        return args;
    }

}
