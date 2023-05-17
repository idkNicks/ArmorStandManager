package net.starly.armorstandmanager.command;

import net.starly.armorstandmanager.context.MessageContent;
import net.starly.armorstandmanager.context.MessageType;
import net.starly.armorstandmanager.manager.ArmorStandManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class ArmorStandExecutor implements CommandExecutor {

    protected ArmorStandManager manager = ArmorStandManager.getInstance();
    protected MessageContent content = MessageContent.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "noConsole"));
            return false;
        }

        if (!player.isOp()) {
            player.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "noAdministrator"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "wrongCommand"));
            return false;
        }

        ArmorStand armorStand = getTargetArmorStand(player);

        if (armorStand == null) {
            player.sendMessage(content.getMessagesAfterPrefix(MessageType.ERROR, "noArmorStand"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            return false;
        }

        return handleCommand(player, armorStand, args);
    }

    protected abstract boolean handleCommand(Player player, ArmorStand armorStand, String[] args);

    protected ArmorStand getTargetArmorStand(Player player) {
        List<Entity> nearbyEntities = player.getNearbyEntities(0.1, 0.1, 0.1);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof ArmorStand) return (ArmorStand) entity;
        }
        return null;
    }
}
