package fr.flo504.worldguardapi.api.region;

import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.api.vector.Vector2D;
import fr.flo504.worldguardapi.api.vector.Vector3D;
import org.bukkit.World;

import java.util.List;
import java.util.UUID;

public interface Region {

    void add();

    void remove();

    void remove(boolean removeChildren);

    World getWorld();

    String getName();

    void setName(String name);

    void redefine(BlockVector3D maximumPoint, BlockVector3D minimumPoint);

    void redefine(List<BlockVector2D> points, int maxY, int minY);

    void copyOf(Region other);

    void addMember(String member);

    void addMember(UUID member);

    void removeMember(String member);

    void removeMember(UUID member);

    boolean isMember(String member);

    boolean isMember(UUID member);

    void addGroupMember(String group);

    void removeGroupMember(String group);

    boolean isGroupMember(String group);

    void addOwner(String member);

    void addOwner(UUID member);

    void removeOwner(String member);

    void removeOwner(UUID member);

    boolean isOwner(String member);

    boolean isOwner(UUID member);

    void addGroupOwner(String group);

    void removeGroupOwner(String group);

    boolean isGroupOwner(String group);

    int getPriority();

    void setPriority(int priority);

    Region getParent();

    void setParent(Region parent);

    int getVolume();

    boolean contains(BlockVector3D blockVector3D);

    boolean contains(Vector3D vector3D);

    boolean contains(BlockVector2D blockVector3D);

    boolean contains(Vector2D vector2D);

    boolean isDirty();

    void setDirty(boolean dirty);

    //TODO Flag
    

}
