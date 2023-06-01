package net.starly.armorstandmanager.command.tabcomplete;

import net.starly.armorstandmanager.ArmorStandPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ArmorStandCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            List<String> pluginCommands = getAllPluginCommands();
            List<String> completions = new ArrayList<>();

            pluginCommands.forEach(pluginCommand -> {
                if (pluginCommand.startsWith(args[0])) completions.add(pluginCommand);
            });
            return completions;
        }
        return Collections.emptyList();
    }

    private List<String> getAllPluginCommands() {
        List<String> pluginCommands = new ArrayList<>();
        Plugin[] plugins = ArmorStandPlugin.getInstance().getServer().getPluginManager().getPlugins();

        for (Plugin plugin : plugins) {
            Map<String, Map<String, Object>> commands = plugin.getDescription().getCommands();

            if (commands != null) {
                commands.keySet().forEach(commandName -> pluginCommands.add(plugin.getName() + ":" + commandName));
            }
        }
        return pluginCommands;
    }
}