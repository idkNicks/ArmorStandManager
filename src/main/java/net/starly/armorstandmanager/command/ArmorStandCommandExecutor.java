package net.starly.armorstandmanager.command;

import net.starly.armorstandmanager.context.MessageType;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandCommandExecutor extends ArmorStandExecutor {

    @Override
    protected boolean handleCommand(Player player, ArmorStand armorStand, String[] args) {

        StringBuilder commandBuilder = new StringBuilder();
        for (String arg : args) {
            commandBuilder.append(arg).append(" ");
        }

        String command = commandBuilder.toString().trim();

        List<String> existingCommands = manager.getCommands(armorStand);

        if (existingCommands != null) {
            existingCommands.add(command);
        } else {
            existingCommands = new ArrayList<>();
            existingCommands.add(command);
        }

        manager.setCommand(armorStand, existingCommands);
        player.sendMessage(content.getMessagesAfterPrefix(MessageType.NORMAL, "registerArmorStandCommand")
                .replace("{command}", command));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);
        return true;
    }
}