package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.util.ResourceUtil;


/**
 * @@author lancelotwillow
 * this class is to convert the joson file that store the convert arguments to list[String], and store the convert
 */
public class JsonConvertArgsStorage {

    public static void storeArgument(String name, List<String> cmds) {
    }

    /**
     * get the template of the arguments need for the operation
     * @param fileUrl
     * @param operation
     * @return
     * @throws IOException
     */
    public static List<String> retrieveArgumentsTemplate(URL fileUrl, String operation) throws IOException {
        JsonNode jsonNode;
        if (!new File(fileUrl.toString()).exists()) {
            File file = new File("commandTemplate.json");
            ResourceUtil.copyResourceFileOut(fileUrl, file);
            jsonNode = new ObjectMapper().readTree(file);
            file.delete();
        } else {
            jsonNode = new ObjectMapper().readTree(fileUrl.getFile());
        }
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
