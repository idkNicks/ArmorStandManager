package net.starly.armorstandmanager.command;

import net.starly.armorstandmanager.context.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandMessageExecutor extends ArmorStandExecutor  {

    @Override
    protected boolean handleCommand(Player player, ArmorStand armorStand, String[] args) {
        StringBuilder commandBuilder = new StringBuilder();

        for (String arg : args) {
            commandBuilder.append(arg).append(" ");
        }

        String message = commandBuilder.toString().trim();
        List<String> existingMessages = manager.getMessages(armorStand);

        if (existingMessages != null) {
            existingMessages.add(message);
        } else {
            existingMessages = new ArrayList<>();
            existingMessages.add(message);
        }

        manager.setMessage(armorStand, existingMessages);
        player.sendMessage(content.getMessagesAfterPrefix(MessageType.NORMAL, "registerArmorStandMessage")
                .replace("{message}", message));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);
        return true;
    }
}
