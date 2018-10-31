package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.commons.util.ResourceUtil;
import seedu.address.model.transformation.Transformation;


/**
 * @@author lancelotwillow
 * this class is to convert the joson file that store the convert arguments to list[String], and store the convert
 */
public class JsonConvertArgsStorage {

    /**
     * .
     * @param name
     * @param cmds
     * @throws IOException
     */
    public static void storeArgument(String name, List<Transformation> cmds) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode1 = mapper.createObjectNode();
        objectNode1.put("name", name);
        objectNode1.put("num", cmds.size());
        ObjectNode operation = mapper.createObjectNode();
        for (int i = 1; i <= cmds.size(); i++) {
            ObjectNode currentoperation = mapper.createObjectNode();
            currentoperation.put("name", cmds.get(i - 1).toList().get(0));
            currentoperation.put("num", cmds.size() - 1);
            ObjectNode argument = mapper.createObjectNode();
            for (int j = 1; j < cmds.size(); j++) {
                argument.put("arg" + j, cmds.get(i - 1).toList().get(j));
            }
            currentoperation.putPOJO("args", argument);
            operation.putPOJO("op" + i, currentoperation);
        }
        objectNode1.putPOJO("operations", operation);
        byte[] content = mapper.writer().writeValueAsString(objectNode1).getBytes();
        File command = new File(ImageMagickUtil.getCommandSaveFolder() + "/" + name + ".json");
        BufferedOutputStream bio = new BufferedOutputStream(new FileOutputStream(command));
        bio.write(content);
        bio.write("\n".getBytes());
        bio.flush();
    }

    /**
     * get the template of the arguments need for the operation
     * @param fileUrl
     * @param operation
     * @return
     * @throws IOException
     */
    public static List<String> retrieveCommandTemplate(URL fileUrl, String operation) throws IOException {
        requireNonNull(fileUrl);
        requireNonNull(operation);
        File file = new File("commandTemplate.json");
        ResourceUtil.copyResourceFileOut(fileUrl, file);
        JsonNode jsonNode = new ObjectMapper().readTree(file);
        List<String> args = new ArrayList<>();
        int num = jsonNode.get("num").asInt();
        for (int i = 1; i <= num; i++) {
            String arg = jsonNode.get("args").get("arg" + i).textValue();
            args.add(arg);
        }
        file.delete();
        return args;
    }

    /**
     * .
     * @param file
     * @param operation
     * @return
     */
    public static List<String> retrieveCommandArguments(File file, String operation) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(file);
        List<String> args = new ArrayList<>();
        int num = jsonNode.get("num").asInt();
        for (int i = 1; i <= num; i++) {
            JsonNode currentOp = jsonNode.get("operations").get("op" + i);
            args.add((i == 1 ? "" : "-") + currentOp.get("name").textValue());
            for (int j = 1; j <= currentOp.get("num").asInt(); j++) {
                args.add(currentOp.get("args").get("arg" + j).textValue());
            }
        }
        return args;
    }
}
