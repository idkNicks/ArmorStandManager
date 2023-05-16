package net.starly.armorstandmanager.command;

import org.bukkit.ChatColor;
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

        String command = commandBuilder.toString().trim();
        List<String> existingMessages = manager.getMessages(armorStand);

        if (existingMessages != null) {
            existingMessages.add(command);
        } else {
            existingMessages = new ArrayList<>();
            existingMessages.add(command);
        }

        manager.setMessage(armorStand, existingMessages);
        player.sendMessage(ChatColor.GREEN + "메시지를 등록하였습니다!");

        return true;
    }
}
