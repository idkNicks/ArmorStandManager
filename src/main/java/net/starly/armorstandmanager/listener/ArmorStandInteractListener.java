package net.starly.armorstandmanager.listener;

import net.starly.armorstandmanager.ArmorStandPlugin;
import net.starly.armorstandmanager.manager.ArmorStandCommandManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.List;

public class ArmorStandInteractListener implements Listener {

    private final ArmorStandCommandManager manager = ArmorStandCommandManager.getInstance();

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            List<String> commands = manager.getCommands(armorStand);

            if (commands != null) {
                for (String command : commands) {
                    ArmorStandPlugin.getInstance().getServer().dispatchCommand(event.getPlayer(), command);
                }
            }
        }
    }
}
