package net.starly.armorstandmanager.listener;

import net.starly.armorstandmanager.manager.ArmorStandCommandManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


import java.util.*;

public class ArmorStandProximityListener implements Listener {

    private final ArmorStandCommandManager manager = ArmorStandCommandManager.getInstance();
    private final Map<Player, Set<UUID>> sentMessages = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY() && to.getBlockZ() == from.getBlockZ()) return;

        Set<UUID> sentMessageSet = sentMessages.getOrDefault(player, new HashSet<>());
        Set<UUID> currentArmorStands = new HashSet<>();

        for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
            if (entity instanceof ArmorStand) {
                ArmorStand armorStand = (ArmorStand) entity;
                currentArmorStands.add(armorStand.getUniqueId());

                if (!sentMessageSet.contains(armorStand.getUniqueId())) {
                    List<String> messages = manager.getMessages(armorStand);
                    if (messages != null) {
                        for (String message : messages) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                }
            }
        }

        Iterator<UUID> iterator = sentMessageSet.iterator();

        while (iterator.hasNext()) {
            UUID armorStandUUID = iterator.next();
            if (!currentArmorStands.contains(armorStandUUID)) iterator.remove();
        }

        sentMessages.put(player, currentArmorStands);
    }
}

