package fr.flo504.worldguardapi.v6.region.flag.adaptor;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import org.bukkit.util.Vector;

public class VectorAdaptor implements FlagAdaptor<Vector> {
    @Override
    public Vector from(Object object) {
        final com.sk89q.worldedit.Vector vector = (com.sk89q.worldedit.Vector) object;
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    @Override
    public Object to(Vector adapt) {
        return new com.sk89q.worldedit.Vector(adapt.getX(), adapt.getY(), adapt.getZ());
    }
}
