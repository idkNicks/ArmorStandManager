package net.starly.armorstandmanager.listener;

import net.starly.armorstandmanager.manager.ArmorStandManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ArmorStandBreakListener implements Listener {

    @EventHandler
    public void onArmorStandBreak(EntityDeathEvent event) {
        if (event.getEntity() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getEntity();
            ArmorStandManager manager = ArmorStandManager.getInstance();

            if (manager.containsCommand(armorStand)) manager.removeCommand(armorStand);
            if (manager.containsMessage(armorStand)) manager.removeMessage(armorStand);

            manager.save();
        }
    }
}
