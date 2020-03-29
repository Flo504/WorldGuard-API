package fr.flo504.worldguardapi.v6.region.flag.adaptor;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import fr.flo504.worldguardapi.api.exeptions.FlagException;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class LocationAdaptor implements FlagAdaptor<Location> {

    private final static Constructor<?> lazyLocConstructor;

    static {
        try {
            lazyLocConstructor = Class.forName("com.sk89q.worldguard.protection.flags.LazyLocation")
                    .getDeclaredConstructor(String.class, Vector.class, float.class, float.class);
            lazyLocConstructor.setAccessible(true);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new FlagException("Can not register LazyLocation constructor", e);
        }
    }

    @Override
    public Location from(Object object) {
        if(!(object instanceof com.sk89q.worldedit.Location))
            throw new FlagException("Attempting to parse an object that is not a worldedit location as a worldedit location");
        final com.sk89q.worldedit.Location loc = (com.sk89q.worldedit.Location) object;
        return new Location(
                ((BukkitWorld)loc.getWorld()).getWorld(),
                loc.getPosition().getX(),
                loc.getPosition().getY(),
                loc.getPosition().getZ(),
                loc.getYaw(),
                loc.getPitch()
        );
    }

    @Override
    public Object to(Location adapt) {
        try {
            return lazyLocConstructor.newInstance(
                    Objects.requireNonNull(adapt.getWorld()).getName(),
                    new Vector(
                            adapt.getX(),
                            adapt.getY(),
                            adapt.getZ()),
                    adapt.getYaw(),
                    adapt.getPitch()
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FlagException("Can not create LazyLocation", e);
        }
    }
}
