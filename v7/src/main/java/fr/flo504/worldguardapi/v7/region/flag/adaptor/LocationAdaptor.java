package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.Vector3;
import fr.flo504.worldguardapi.api.exeptions.FlagException;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@SuppressWarnings("all")
public class LocationAdaptor implements FlagAdaptor<Location> {

    private final static Constructor<?> lazyLocConstructor;

    static {
        try {
            lazyLocConstructor = Class.forName("com.sk89q.worldguard.protection.flags.LazyLocation")
                    .getDeclaredConstructor(String.class, Vector3.class, float.class, float.class);
            lazyLocConstructor.setAccessible(true);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new FlagException("Can not register LazyLocation constructor", e);
        }
    }

    @Override
    public Location from(Object object) {
        if(!(object instanceof com.sk89q.worldedit.util.Location))
            throw new FlagException("Attempting to parse an object that is not a worldedit location as a worldedit location");
        final com.sk89q.worldedit.util.Location loc = (com.sk89q.worldedit.util.Location) object;
        return BukkitAdapter.adapt(loc);
    }

    @Override
    public Object to(Location adapt) {
        try {
            return lazyLocConstructor.newInstance(
                    Objects.requireNonNull(adapt.getWorld()).getName(),
                    Vector3.at(
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
