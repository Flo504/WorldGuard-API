package fr.flo504.worldguardapi.api.selection;

import org.bukkit.World;

public final class NullSelection implements Selection {

    private final World world;

    public NullSelection(World world) {
        this.world = world;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Type getType() {
        return Type.NULL;
    }

    @Override
    public String toString() {
        return "NullSelection{" +
                "world=" + world +
                '}';
    }
}
