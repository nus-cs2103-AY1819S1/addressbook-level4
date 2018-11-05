package seedu.address.storage;

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
    public static void storeArgument(String name, List<Transformation> cmds, String saveFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode1 = mapper.createObjectNode();
        objectNode1.put("name", name);
        objectNode1.put("num", cmds.size());
        ObjectNode operation = mapper.createObjectNode();
        for (int i = 1; i <= cmds.size(); i++) {
            ObjectNode currentOperation = mapper.createObjectNode();
            currentOperation.put("name", cmds.get(i - 1).toList().get(0));
            int argNum = cmds.get(i - 1).toList().size() - 1;
            currentOperation.put("num", argNum);
            ObjectNode argument = mapper.createObjectNode();
            for (int j = 1; j <= argNum; j++) {
                argument.put("arg" + j, cmds.get(i - 1).toList().get(j));
            }
            currentOperation.putPOJO("args", argument);
            operation.putPOJO("op" + i, currentOperation);
        }
        objectNode1.putPOJO("operations", operation);
        byte[] content = mapper.writer().writeValueAsString(objectNode1).getBytes();
        File command = new File(saveFolder + "/" + name + ".json");
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
    public static List<String> retrieveCommandTemplate(URL fileUrl, String operation, String content)
            throws IOException {
        if (fileUrl == null || operation == null) {
            throw new IOException("the url is invalid");
        }
        File file = new File("commandTemplate.json");
        ResourceUtil.copyResourceFileOut(fileUrl, file);
        JsonNode jsonNode = new ObjectMapper().readTree(file);
        List<String> patterns = new ArrayList<>();
        int num = jsonNode.get("num").asInt();
        for (int i = 1; i <= num; i++) {
            String pattern = jsonNode.get(content + "s").get(content + i).textValue();
            patterns.add(pattern);
        }
        file.delete();
        return patterns;
    }

    /**
     * .
     * @param file
     * @return
     */
    public static List<String> retrieveCommandArguments(File file) throws IOException {
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
