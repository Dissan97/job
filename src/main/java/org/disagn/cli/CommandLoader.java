package org.disagn.cli;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class used by views to load their command list to avoid code duplication
 */

public class CommandLoader {
    List<String> commandList = new ArrayList<>();
    public static final String HELP = "Type help or 0 to get command list";
    public CommandLoader(String user) throws FileNotFoundException {
        this.setUpCommandList(user);
    }

    private void setUpCommandList(String commandSet) throws FileNotFoundException {
        JsonElement reader;

        String path = Objects.requireNonNull(CommandLoader.class.getResource("userCliCommand.json")).getPath();

        reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(path)
                    )
        );

        JsonObject object = reader.getAsJsonObject();

        JsonArray commands = object.get(commandSet).getAsJsonArray();

        for (JsonElement elem:
             commands) {
            this.commandList.add(elem.getAsString());
        }
    }

    public List<String> getCommandList() {
        return commandList;
    }
}
