package net.starly.armorstandmanager.context;

import net.starly.core.jb.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageContent {

    private static MessageContent instance;

    private final Map<MessageType, Map<String, Object>> map = new HashMap<>();

    private MessageContent() {}

    public static MessageContent getInstance() {
        if (instance == null) instance = new MessageContent();
        return instance;
    }

    public void initializing(FileConfiguration file) {
        map.clear();
        Arrays.stream(MessageType.values()).map(values -> new Pair<>(values, file.getConfigurationSection(values.name)))
                .forEach(this::initializingMessages);
    }

    private void initializingMessages(Pair<MessageType, ConfigurationSection> pair) {
        Map<String, Object> messages = map.computeIfAbsent(pair.getFirst(), (unused) -> new HashMap<>());
        pair.getSecond().getKeys(true).forEach(key -> {

            if (pair.getSecond().isInt(key)) {
                messages.put(key, pair.getSecond().getInt(key));

            } else if (pair.getSecond().isList(key)) {
                messages.put(key, pair.getSecond().getStringList(key)
                        .stream()
                        .map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
            } else {
                messages.put(key, ChatColor.translateAlternateColorCodes('&', pair.getSecond().getString(key)));
            }
        });
    }

    public String getMessage(MessageType type, String key) { return (String) map.get(type).get(key); }

    @SuppressWarnings("unchecked")
    public List<String> getMessages(MessageType type, String key) { return (List<String>) map.get(type).get(key); }

    public String getMessagesAfterPrefix(MessageType type, String key) { return map.get(MessageType.NORMAL).get("prefix") + getMessage(type, key); }

    public Integer getInt(MessageType type, String key) { return (Integer) map.get(type).get(key); }

}
