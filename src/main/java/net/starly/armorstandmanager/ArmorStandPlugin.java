package net.starly.armorstandmanager;

import lombok.Getter;
import net.starly.armorstandmanager.command.ArmorStandCommandExecutor;
import net.starly.armorstandmanager.command.ArmorStandMessageExecutor;
import net.starly.armorstandmanager.command.tabcomplete.ArmorStandCommandTabCompleter;
import net.starly.armorstandmanager.listener.ArmorStandBreakListener;
import net.starly.armorstandmanager.listener.ArmorStandInteractListener;
import net.starly.armorstandmanager.listener.ArmorStandProximityListener;
import net.starly.armorstandmanager.manager.ArmorStandCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmorStandPlugin extends JavaPlugin {

    @Getter private static ArmorStandPlugin instance;
    @Getter private static String prefix;

    private ArmorStandCommandManager manager;

    @Override
    public void onLoad() { this.instance = this; }

    @Override
    public void onEnable() {
        if (!isPluginEnable("ST-Core")) {
            getServer().getLogger().warning("[" + getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            getServer().getLogger().warning("[" + getName() + "] 다운로드 링크 : http://starly.kr/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        this.prefix = getConfig().getString("messages.prefix");

        manager = ArmorStandCommandManager.getInstance();

        getCommand("아머스탠드명령어").setExecutor(new ArmorStandCommandExecutor());
        getCommand("아머스탠드명령어").setTabCompleter(new ArmorStandCommandTabCompleter());
        getCommand("아머스탠드메시지").setExecutor(new ArmorStandMessageExecutor());

        getServer().getPluginManager().registerEvents(new ArmorStandInteractListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorStandProximityListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorStandBreakListener(), this);
    }

    @Override
    public void onDisable() {
        if (manager != null) manager.save();
    }

    private boolean isPluginEnable(String pluginName) {
        Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }
}
