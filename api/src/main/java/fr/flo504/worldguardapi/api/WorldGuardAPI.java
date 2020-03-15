package fr.flo504.worldguardapi.api;

import fr.flo504.worldguardapi.api.exeptions.WorldGuardAPIException;
import fr.flo504.worldguardapi.api.exeptions.WorldGuardUnknownVersionException;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.logging.Level;

public class WorldGuardAPI {

    private static WGPlatform platform;

    static{

        final String version = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WorldGuard")).getDescription().getVersion().replace(".", ",").split(",")[0];
        final int worldGuardVersion = Integer.parseInt(version);

        final String platformClassName;

        switch (worldGuardVersion){
            case 6:
            case 7:
                platformClassName = "fr.flo504.worldguardapi.v"+worldGuardVersion+".Platform"+worldGuardVersion;
                break;
            default:
                throw new WorldGuardUnknownVersionException("Unknown version of Worldguard");
        }

        Object platform = null;
        try{
            final Class<?> platformClass = Class.forName(platformClassName);
            final Constructor<?> platformConstructor = platformClass.getDeclaredConstructor();
            platform = platformConstructor.newInstance();
        }catch (ReflectiveOperationException ignored){}

        if(platform == null)
            throw new WorldGuardAPIException("Platform can not be loaded");

        Bukkit.getLogger().log(Level.INFO, "Successfully load the WorldGuard api for WorldGuard version "+worldGuardVersion);

        WorldGuardAPI.platform = (WGPlatform) platform;
    }

    /**
     * Get the platform to do some operations
     * @return the platform of the working version of WorldGuard
     */
    public static WGPlatform getPlatform() {
        return platform;
    }
}
