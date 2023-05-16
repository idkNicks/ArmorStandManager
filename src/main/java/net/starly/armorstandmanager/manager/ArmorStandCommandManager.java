package net.starly.armorstandmanager.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.starly.armorstandmanager.ArmorStandPlugin;
import org.bukkit.entity.ArmorStand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ArmorStandCommandManager {

    private static ArmorStandCommandManager instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<UUID, List<String>> armorStandCommands = new HashMap<>();
    private final Map<UUID, List<String>> armorStandMessages = new HashMap<>();
    private final File dataFile;

    private ArmorStandCommandManager() {
        dataFile = new File(ArmorStandPlugin.getInstance().getDataFolder(), "armorstand_data.json");

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                save();
            } catch (IOException e) { e.printStackTrace(); }
        }

        load();
    }

    public static ArmorStandCommandManager getInstance() {
        if (instance == null) instance = new ArmorStandCommandManager();
        return instance;
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(dataFile.toPath(), StandardCharsets.UTF_8)) {
            JsonObject json = new JsonObject();

            JsonArray commandArray = new JsonArray();
            for (Map.Entry<UUID, List<String>> entry : armorStandCommands.entrySet()) {
                JsonObject commandObject = new JsonObject();
                commandObject.addProperty("uuid", entry.getKey().toString());
                commandObject.add("commands", gson.toJsonTree(entry.getValue()));
                commandArray.add(commandObject);
            }
            json.add("commands", commandArray);

            JsonArray messageArray = new JsonArray();
            for (Map.Entry<UUID, List<String>> entry : armorStandMessages.entrySet()) {
                JsonObject messageObject = new JsonObject();
                messageObject.addProperty("uuid", entry.getKey().toString());
                messageObject.add("messages", gson.toJsonTree(entry.getValue()));
                messageArray.add(messageObject);
            }
            json.add("messages", messageArray);

            gson.toJson(json, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void load() {
        try (Reader reader = Files.newBufferedReader(dataFile.toPath())) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            if (json.has("commands") && json.has("messages")) {
                JsonArray commandArray = json.getAsJsonArray("commands");
                for (JsonElement element : commandArray) {
                    JsonObject commandObject = element.getAsJsonObject();
                    UUID uuid = UUID.fromString(commandObject.get("uuid").getAsString());
                    List<String> commands = gson.fromJson(commandObject.get("commands"), new TypeToken<List<String>>() {}.getType());
                    armorStandCommands.put(uuid, commands);
                }

                JsonArray messageArray = json.getAsJsonArray("messages");
                for (JsonElement element : messageArray) {
                    JsonObject messageObject = element.getAsJsonObject();
                    UUID uuid = UUID.fromString(messageObject.get("uuid").getAsString());
                    List<String> messages = gson.fromJson(messageObject.get("messages"), new TypeToken<List<String>>() {}.getType());
                    armorStandMessages.put(uuid, messages);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void setCommand(ArmorStand armorStand, List<String> commands) {
        armorStandCommands.put(armorStand.getUniqueId(), commands);
    }

    public void setMessage(ArmorStand armorStand, List<String> messages) {
        armorStandMessages.put(armorStand.getUniqueId(), messages);
    }

    public List<String> getCommands(ArmorStand armorStand) {
        return armorStandCommands.get(armorStand.getUniqueId());
    }

    public List<String> getMessages(ArmorStand armorStand) {
        return armorStandMessages.get(armorStand.getUniqueId());
    }

    public boolean containsCommand(ArmorStand armorStand) {
        return armorStandCommands.containsKey(armorStand.getUniqueId());
    }

    public boolean containsMessage(ArmorStand armorStand) {
        return armorStandMessages.containsKey(armorStand.getUniqueId());
    }

    public void removeCommand(ArmorStand armorStand) {
        armorStandCommands.remove(armorStand.getUniqueId());
    }

    public void removeMessage(ArmorStand armorStand) {
        armorStandMessages.remove(armorStand.getUniqueId());
    }

    public boolean isCommandRegistered(ArmorStand armorStand) {
        return armorStandCommands.containsKey(armorStand.getUniqueId());
    }
}