package net.starly.armorstandmanager.command;

import net.starly.armorstandmanager.ArmorStandPlugin;
import net.starly.armorstandmanager.context.MessageContent;
import net.starly.armorstandmanager.context.MessageType;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArmorStandManagerExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        MessageContent content = MessageContent.getInstance();

        if (!(sender instanceof Player player)) {
            sender.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "noConsole"));
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "wrongCommand"));
            return false;
        }

        if (args[0].equals("리로드") || args[0].equals("reload")) {
            ArmorStandPlugin.getInstance().reloadConfig();
            content.initializing(ArmorStandPlugin.getInstance().getConfig());
            player.sendMessage(content.getMessagesAfterPrefix(MessageType.NORMAL, "reloadComplete"));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);
            return true;
        } else {
            player.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "wrongCommand"));
        }
        return false;
    }
}
