package fr.flo504.worldguardapi.v7.selection;

import com.sk89q.worldedit.regions.RegionSelector;
import fr.flo504.worldguardapi.api.selection.OtherSelection;
import org.bukkit.World;

public final class OtherSelection7 implements OtherSelection {

    private final World world;
    private final RegionSelector selector;

    public OtherSelection7(World world, RegionSelector selector) {
        this.world = world;
        this.selector = selector;
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

    @Override
    public String toString() {
        return "OtherSelection7{" +
                "world=" + world +
                ", selector=" + selector +
                '}';
    }
}
