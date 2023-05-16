package net.starly.armorstandmanager.command;

import org.bukkit.ChatColor;
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
        player.sendMessage(ChatColor.GREEN + "커맨드를 등록하였습니다!");

        return true;
    }
}