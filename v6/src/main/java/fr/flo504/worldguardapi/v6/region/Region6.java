package fr.flo504.worldguardapi.v6.region;

import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import fr.flo504.worldguardapi.api.exeptions.RegionAlreadyExistException;
import fr.flo504.worldguardapi.api.exeptions.RegionException;
import fr.flo504.worldguardapi.api.exeptions.RegionNotFoundException;
import fr.flo504.worldguardapi.api.region.Region;
import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.FlagSession;
import fr.flo504.worldguardapi.api.region.flag.MapReflect;
import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.api.vector.Vector2D;
import fr.flo504.worldguardapi.api.vector.Vector3D;
import fr.flo504.worldguardapi.v6.region.flag.FlagRegistry6;
import fr.flo504.worldguardapi.v6.vectors.VectorAdapter6;
import org.bukkit.World;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Region6 implements Region {

    private final World world;
    private ProtectedRegion region;

    private final FlagRegistry6 flagRegistry;

    public Region6(World world, String name, BlockVector3D maximumPoint, BlockVector3D minimumPoint, FlagRegistry6 flagRegistry) {
        if(RegionManagerUtils6.existRegion(world, name))
            throw new RegionAlreadyExistException("The region "+name+" in the world "+world.getName()+" already exist");

        this.world = world;
        region = new ProtectedCuboidRegion(name, VectorAdapter6.toWGBlockVector3D(maximumPoint), VectorAdapter6.toWGBlockVector3D(minimumPoint));

        this.flagRegistry = flagRegistry;
    }

    public Region6(World world, String name, List<BlockVector2D> points, int maxY, int minY, FlagRegistry6 flagRegistry) {
        if(RegionManagerUtils6.existRegion(world, name))
            throw new RegionAlreadyExistException("The region "+name+" in the world "+world.getName()+" already exist");

        this.world = world;

        final int miniY = Math.min(maxY, minY);
        final int maxiY = Math.max(maxY, minY);
        final List<com.sk89q.worldedit.BlockVector2D> wgPoints = points.stream().map(VectorAdapter6::toWGBlockVector2D).collect(Collectors.toList());
        region = new ProtectedPolygonalRegion(name, wgPoints, miniY, maxiY);

        this.flagRegistry = flagRegistry;
    }

    public Region6(World world, String name, FlagRegistry6 flagRegistry) {
        if(!RegionManagerUtils6.existRegion(world, name))
            throw new RegionNotFoundException("The region "+name+" in the world "+world.getName()+" does not exist");

        this.world = world;
        this.region = RegionManagerUtils6.getRegion(world, name);

        this.flagRegistry = flagRegistry;
    }

    private Region6(World world, ProtectedRegion region, FlagRegistry6 flagRegistry){
        this.world = world;
        this.region = region;

        this.flagRegistry = flagRegistry;
    }

    @Override
    public void add() {
        RegionManagerUtils6.addRegion(world, region);
    }

    @Override
    public void remove() {
        remove(true);
    }

    @Override
    public void remove(boolean removeChildren) {
        RegionManagerUtils6.removeRegion(world, region.getId(), removeChildren ? RemovalStrategy.REMOVE_CHILDREN : RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public String getName() {
        return region.getId();
    }

    @Override
    public void setName(String name) {
        if(RegionManagerUtils6.existRegion(world, name))
            throw new RegionAlreadyExistException("The region "+name+" in the world "+world.getName()+" already exist");

        final ProtectedRegion other;

        if(region.getType() == RegionType.CUBOID)
            other = new ProtectedCuboidRegion(name, region.getMaximumPoint(), region.getMinimumPoint());
        else if(region.getType() == RegionType.POLYGON)
            other = new ProtectedPolygonalRegion(region.getId(), region.getPoints(), region.getMinimumPoint().getBlockY(), region.getMaximumPoint().getBlockY());
        else
            throw new RegionException("Can not set the name of the global region");
        other.copyFrom(region);
        RegionManagerUtils6.removeRegion(world, region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
        
        region = other;
        add();
    }

    @Override
    public void redefine(BlockVector3D maximumPoint, BlockVector3D minimumPoint) {
        final ProtectedRegion other = new ProtectedCuboidRegion(region.getId(), VectorAdapter6.toWGBlockVector3D(maximumPoint), VectorAdapter6.toWGBlockVector3D(minimumPoint));

        other.copyFrom(region);
        RegionManagerUtils6.removeRegion(world, region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);

        region = other;
        add();
    }

    @Override
    public void redefine(List<BlockVector2D> points, int maxY, int minY) {
        final int miniY = Math.min(maxY, minY);
        final int maxiY = Math.max(maxY, minY);
        final List<com.sk89q.worldedit.BlockVector2D> wgPoints = points.stream().map(VectorAdapter6::toWGBlockVector2D).collect(Collectors.toList());
        final ProtectedRegion other = new ProtectedPolygonalRegion(region.getId(), wgPoints, miniY, maxiY);

        other.copyFrom(region);
        RegionManagerUtils6.removeRegion(world, region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);

        region = other;
        add();
    }

    @Override
    public void copyOf(Region other) {
        region.copyFrom(((Region6)other).region);
    }

    @Override
    public void addMember(String member) {
        if(!region.getMembers().contains(member))
            region.getMembers().addPlayer(member);
    }

    @Override
    public void addMember(UUID member) {
        if(!region.getMembers().contains(member))
            region.getMembers().addPlayer(member);
    }

    @Override
    public void removeMember(String member) {
        if(region.getMembers().contains(member))
            region.getMembers().removePlayer(member);
    }

    @Override
    public void removeMember(UUID member) {
        if(region.getMembers().contains(member))
            region.getMembers().removePlayer(member);
    }

    @Override
    public boolean isMember(String member) {
        return region.getMembers().contains(member);
    }

    @Override
    public boolean isMember(UUID member) {
        return region.getMembers().contains(member);
    }

    @Override
    public void addGroupMember(String group) {
        if(!region.getMembers().getGroups().contains(group))
            region.getMembers().addGroup(group);
    }

    @Override
    public void removeGroupMember(String group) {
        if(region.getMembers().getGroups().contains(group))
            region.getMembers().removeGroup(group);
    }

    @Override
    public boolean isGroupMember(String group) {
        return region.getMembers().getGroups().contains(group);
    }

    @Override
    public void addOwner(String member) {
        if(!region.getOwners().contains(member))
            region.getOwners().addPlayer(member);
    }

    @Override
    public void addOwner(UUID member) {
        if(!region.getOwners().contains(member))
            region.getOwners().addPlayer(member);
    }

    @Override
    public void removeOwner(String member) {
        if(region.getOwners().contains(member))
            region.getOwners().removePlayer(member);
    }

    @Override
    public void removeOwner(UUID member) {
        if(region.getOwners().contains(member))
            region.getOwners().removePlayer(member);
    }

    @Override
    public boolean isOwner(String member) {
        return region.getOwners().contains(member);
    }

    @Override
    public boolean isOwner(UUID member) {
        return region.getOwners().contains(member);
    }

    @Override
    public void addGroupOwner(String group) {
        if(!region.getOwners().getGroups().contains(group))
            region.getOwners().addGroup(group);
    }

    @Override
    public void removeGroupOwner(String group) {
        if(region.getOwners().getGroups().contains(group))
            region.getOwners().removeGroup(group);
    }

    @Override
    public boolean isGroupOwner(String group) {
        return region.getOwners().getGroups().contains(group);
    }

    @Override
    public int getPriority() {
        return region.getPriority();
    }

    @Override
    public void setPriority(int priority) {
        region.setPriority(priority);
    }

    @Override
    public Region getParent() {
        return new Region6(world, region.getParent(), flagRegistry);
    }

    @Override
    public void setParent(Region parent) {
        try {
            region.setParent(((Region6)parent).region);
        } catch (ProtectedRegion.CircularInheritanceException e) {
            throw new RegionException(e.getMessage(), e);
        }
    }

    @Override
    public int getVolume() {
        return region.volume();
    }

    @Override
    public boolean contains(BlockVector3D blockVector3D) {
        return region.contains(blockVector3D.getX(), blockVector3D.getY(), blockVector3D.getZ());
    }

    @Override
    public boolean contains(Vector3D vector3D) {
        final int x = vector3D.getX() >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)Math.floor(vector3D.getX());
        final int y = vector3D.getY() >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)Math.floor(vector3D.getY());
        final int z = vector3D.getZ() >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)Math.floor(vector3D.getZ());
        return region.contains(x, y, z);
    }

    @Override
    public boolean contains(BlockVector2D blockVector2D) {
        return region.contains(blockVector2D.getX(), region.getMinimumPoint().getBlockY(), blockVector2D.getZ());
    }

    @Override
    public boolean contains(Vector2D vector2D) {
        final int x = vector2D.getX() >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)Math.floor(vector2D.getX());
        final int y = region.getMinimumPoint().getBlockY();
        final int z = vector2D.getZ() >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)Math.floor(vector2D.getZ());
        return region.contains(x, y, z);
    }

    @Override
    public boolean isDirty() {
        return region.isDirty();
    }

    @Override
    public void setDirty(boolean dirty) {
        region.setDirty(dirty);
    }

    @Override
    public boolean hasFlag(Flag<?> flag) {
        return MapReflect.contains(region.getFlags(), flagRegistry.getFlagSession(flag).getWgFlag());
    }

    @Override
    public <T> T getFlag(Flag<T> flag) {
        final FlagSession<T> flagSession = flagRegistry.getFlagSession(flag);
        final Object obj = MapReflect.get(region.getFlags(), flagSession.getWgFlag());
        return obj != null ? flagSession.getAdaptor().from(obj) : null;
    }

    @Override
    public <T> void setFlag(Flag<T> flag, T value) {
        final FlagSession<T> flagSession = flagRegistry.getFlagSession(flag);
        if (value == null)
            MapReflect.remove(region.getFlags(), flagSession.getWgFlag());
        else
            region.getFlags().put((com.sk89q.worldguard.protection.flags.Flag<?>) flagSession.getWgFlag(), flagSession.getAdaptor().to(value));
        region.setDirty(true);
    }

}
