package fr.flo504.worldguardapi.api.selection;

import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import org.bukkit.World;

import java.util.List;

public final class PolygonalSelection implements Selection{

    private final World world;
    private final List<BlockVector2D> points;
    private final int maxY;
    private final int minY;

    public PolygonalSelection(World world, List<BlockVector2D> points, int maxY, int minY) {
        this.world = world;
        this.points = points;
        this.maxY = maxY;
        this.minY = minY;
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
        return Type.POLYGONAL;
    }

    public List<BlockVector2D> getPoints() {
        return points;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    @Override
    public String toString() {
        return "PolygonalSelection{" +
                "world=" + world +
                ", points=" + points +
                ", maxY=" + maxY +
                ", minY=" + minY +
                '}';
    }
}
