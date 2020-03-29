package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldedit.math.Vector3;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import org.bukkit.util.Vector;

public class VectorAdaptor implements FlagAdaptor<Vector> {
    @Override
    public Vector from(Object object) {
        final Vector3 vector = (Vector3) object;
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    @Override
    public Object to(Vector adapt) {
        return Vector3.at(adapt.getX(), adapt.getY(), adapt.getZ());
    }
}
