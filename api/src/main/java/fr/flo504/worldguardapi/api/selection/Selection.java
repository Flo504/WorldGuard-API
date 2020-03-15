package fr.flo504.worldguardapi.api.selection;

import org.bukkit.World;

public interface Selection {

    boolean isSelected();

    World getWorld();

    Selection.Type getType();

    enum Type {
        POLYGONAL, CUBOID, NULL, OTHER;
    }

}
