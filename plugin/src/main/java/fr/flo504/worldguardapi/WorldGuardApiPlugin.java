package fr.flo504.worldguardapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class WorldGuardApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Plugin WG = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if(WG == null || !WG.isEnabled()){
            Bukkit.getLogger().log(Level.INFO, "WorldGuard is not loaded on this server, disabling the api");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
