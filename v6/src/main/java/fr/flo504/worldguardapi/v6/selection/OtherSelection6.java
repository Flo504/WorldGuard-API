package fr.flo504.worldguardapi.v6.selection;

import com.sk89q.worldedit.regions.RegionSelector;
import fr.flo504.worldguardapi.api.selection.OtherSelection;
import org.bukkit.World;

public final class OtherSelection6 implements OtherSelection {

    private final World world;
    private final RegionSelector selector;
    private final int maxVertices;

    public OtherSelection6(World world, RegionSelector selector, int maxVertices) {
        this.world = world;
        this.selector = selector;
        this.maxVertices = maxVertices;
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
        return Type.OTHER;
    }

    public RegionSelector getSelector() {
        return selector;
    }

    public int getMaxVertices() {
        return maxVertices;
    }

    @Override
    public String toString() {
        return "OtherSelection6{" +
                "world=" + world +
                ", selector=" + selector +
                '}';
    }
}
