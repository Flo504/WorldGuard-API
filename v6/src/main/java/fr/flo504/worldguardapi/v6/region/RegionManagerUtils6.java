package fr.flo504.worldguardapi.v6.region;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.flo504.worldguardapi.api.exeptions.WorldGuardAPIException;
import org.bukkit.World;

public class RegionManagerUtils6 {

    private RegionManagerUtils6() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    private final static RegionContainer regionContainer;

    static{
        regionContainer = WorldGuardPlugin.inst().getRegionContainer();
    }

    public static boolean existRegion(World world, String name){
        final RegionManager regionManager = regionContainer.get(world);
        if(regionManager == null)
            return false;
        return regionManager.hasRegion(name);
    }

    private static RegionManager getRegionManager(World world){
        RegionManager regionManager = regionContainer.get(world);
        if(regionManager == null) {
            regionContainer.reload();
            regionManager = regionContainer.get(world);
            if(regionManager == null)
                throw new WorldGuardAPIException("Can not load the region manager for this world");
        }
        return regionManager;
    }

    public static void addRegion(World world, ProtectedRegion region){
        final RegionManager regionManager = getRegionManager(world);
        regionManager.addRegion(region);
    }

    public static void removeRegion(World world, String name, RemovalStrategy strategy){
        final RegionManager regionManager = getRegionManager(world);
        regionManager.removeRegion(name, strategy);
    }

    public static ProtectedRegion getRegion(World world, String name){
        final RegionManager regionManager = getRegionManager(world);
        return regionManager.getRegion(name);
    }

}
