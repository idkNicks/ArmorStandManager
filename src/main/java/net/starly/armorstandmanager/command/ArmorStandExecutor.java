package net.starly.armorstandmanager.command;

import net.starly.armorstandmanager.manager.ArmorStandCommandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class ArmorStandExecutor implements CommandExecutor {

    protected ArmorStandCommandManager manager = ArmorStandCommandManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("해당 명령어는 플레이어만 사용할 수 있습니다.");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "올바르지 않은 명령어입니다.");
            return false;
        }

        ArmorStand armorStand = getTargetArmorStand(player);

        if (armorStand == null) {
            player.sendMessage("아머스탠드가 근처에 없습니다!");
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
