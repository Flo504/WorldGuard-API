package fr.flo504.worldguardapi.v6.worldedit;

import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.flo504.worldguardapi.api.exeptions.WorldGuardAPIException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class WorldEditManager {

    private final WorldEditPlugin worldEditPlugin;

    private final Map<World, BukkitWorld> worlds;

    public WorldEditManager() {
        final Plugin wePlugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if(wePlugin == null || !wePlugin.isEnabled())
            throw new WorldGuardAPIException("WorldEdit is not enable");

        if(!(wePlugin instanceof WorldEditPlugin))
            throw new WorldGuardAPIException("Wrong WorldEdit Plugin");

        worldEditPlugin = (WorldEditPlugin)wePlugin;
        worlds = new HashMap<>();
    }

    public BukkitWorld getWorld(World world){
        return worlds.getOrDefault(world, new BukkitWorld(world));
    }

    public WorldEditPlugin getWorldEditPlugin() {
        return worldEditPlugin;
    }
}
