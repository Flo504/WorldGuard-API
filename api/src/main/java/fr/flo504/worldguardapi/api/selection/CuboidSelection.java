package fr.flo504.worldguardapi.api.selection;

import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import org.bukkit.World;

public final class CuboidSelection implements Selection {

    private final World world;
    private final BlockVector3D maximumPoint;
    private final BlockVector3D minimumPoint;

    public CuboidSelection(World world, BlockVector3D maximumPoint, BlockVector3D minimumPoint) {
        this.world = world;
        this.maximumPoint = maximumPoint;
        this.minimumPoint = minimumPoint;
    }

    @Override
    public boolean isSelected() {
        return true;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Type getType() {
        return Type.CUBOID;
    }

    public BlockVector3D getMaximumPoint() {
        return maximumPoint;
    }

    public BlockVector3D getMinimumPoint() {
        return minimumPoint;
    }

    @Override
    public String toString() {
        return "CuboidSelection{" +
                "world=" + world +
                ", maximumPoint=" + maximumPoint +
                ", minimumPoint=" + minimumPoint +
                '}';
    }
}
