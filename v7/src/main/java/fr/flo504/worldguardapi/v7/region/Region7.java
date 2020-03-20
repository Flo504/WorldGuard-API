package fr.flo504.worldguardapi.v7.region;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import fr.flo504.worldguardapi.api.exeptions.FlagException;
import fr.flo504.worldguardapi.api.exeptions.RegionAlreadyExistException;
import fr.flo504.worldguardapi.api.exeptions.RegionException;
import fr.flo504.worldguardapi.api.exeptions.RegionNotFoundException;
import fr.flo504.worldguardapi.api.region.Region;
import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.api.vector.Vector2D;
import fr.flo504.worldguardapi.api.vector.Vector3D;
import fr.flo504.worldguardapi.v7.region.flag.Flag7;
import fr.flo504.worldguardapi.v7.region.flag.FlagRegistry7;
import fr.flo504.worldguardapi.v7.vectors.VectorAdapter7;
import org.bukkit.World;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Region7 implements Region {

    private final World world;
    private ProtectedRegion region;

    private final FlagRegistry7 flagRegistry;

    public Region7(World world, String name, BlockVector3D maximumPoint, BlockVector3D minimumPoint, FlagRegistry7 flagRegistry) {
        if(RegionManagerUtils7.existRegion(world, name))
            throw new RegionAlreadyExistException("The region "+name+" in the world "+world.getName()+" already exist");

        this.world = world;
        region = new ProtectedCuboidRegion(name, VectorAdapter7.toWGBlockVector3D(maximumPoint), VectorAdapter7.toWGBlockVector3D(minimumPoint));

        this.flagRegistry = flagRegistry;
    }

    public Region7(World world, String name, List<BlockVector2D> points, int maxY, int minY, FlagRegistry7 flagRegistry) {
        if(RegionManagerUtils7.existRegion(world, name))
            throw new RegionAlreadyExistException("The region "+name+" in the world "+world.getName()+" already exist");

        this.world = world;

        final int miniY = Math.min(maxY, minY);
        final int maxiY = Math.max(maxY, minY);
        final List<BlockVector2> wgPoints = points.stream().map(VectorAdapter7::toWGBlockVector2D).collect(Collectors.toList());
        region = new ProtectedPolygonalRegion(name, wgPoints, miniY, maxiY);

        this.flagRegistry = flagRegistry;
    }

    public Region7(World world, String name, FlagRegistry7 flagRegistry) {
        if(!RegionManagerUtils7.existRegion(world, name))
            throw new RegionNotFoundException("The region "+name+" in the world "+world.getName()+" does not exist");

        this.world = world;
        this.region = RegionManagerUtils7.getRegion(world, name);

        this.flagRegistry = flagRegistry;
    }

    private Region7(World world, ProtectedRegion region, FlagRegistry7 flagRegistry){
        this.world = world;
        this.region = region;
        this.flagRegistry = flagRegistry;
    }

    @Override
    public void add() {
        RegionManagerUtils7.addRegion(world, region);
    }

    @Override
    public void remove() {
        remove(true);
    }

    @Override
    public void remove(boolean removeChildren) {
        RegionManagerUtils7.removeRegion(world, region.getId(), removeChildren ? RemovalStrategy.REMOVE_CHILDREN : RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
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
        if(RegionManagerUtils7.existRegion(world, name))
            throw new RegionAlreadyExistException("The region "+name+" in the world "+world.getName()+" already exist");

        final ProtectedRegion other;

        if(region.getType() == RegionType.CUBOID)
            other = new ProtectedCuboidRegion(name, region.getMaximumPoint(), region.getMinimumPoint());
        else if(region.getType() == RegionType.POLYGON)
            other = new ProtectedPolygonalRegion(region.getId(), region.getPoints(), region.getMinimumPoint().getBlockY(), region.getMaximumPoint().getBlockY());
        else
            throw new RegionException("Can not set the name of the global region");
        other.copyFrom(region);
        RegionManagerUtils7.removeRegion(world, region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);

        region = other;
        add();
    }

    @Override
    public void redefine(BlockVector3D maximumPoint, BlockVector3D minimumPoint) {
        final ProtectedRegion other = new ProtectedCuboidRegion(region.getId(), VectorAdapter7.toWGBlockVector3D(maximumPoint), VectorAdapter7.toWGBlockVector3D(minimumPoint));

        other.copyFrom(region);
        RegionManagerUtils7.removeRegion(world, region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);

        region = other;
        add();
    }

    @Override
    public void redefine(List<BlockVector2D> points, int maxY, int minY) {
        final int miniY = Math.min(maxY, minY);
        final int maxiY = Math.max(maxY, minY);
        final List<BlockVector2> wgPoints = points.stream().map(VectorAdapter7::toWGBlockVector2D).collect(Collectors.toList());
        final ProtectedRegion other = new ProtectedPolygonalRegion(region.getId(), wgPoints, miniY, maxiY);

        other.copyFrom(region);
        RegionManagerUtils7.removeRegion(world, region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);

        region = other;
        add();
    }

    @Override
    public void copyOf(Region other) {
        region.copyFrom(((Region7)other).region);
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
        return new Region7(world, region.getParent(), flagRegistry);
    }

    @Override
    public void setParent(Region parent) {
        try {
            region.setParent(((Region7)parent).region);
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

    private <F, T, U> Flag7<F, T> ensureFlag7(Flag<U, T> flag){
        if(!(flag instanceof Flag7))
            throw new FlagException("Wrong version for the flag '"+flag.getName()+"'");
        return (Flag7<F, T>)flag;
    }

    @Override
    public boolean hasFlag(Flag<?, ?> flag) {
        final Flag7<?, ?> flag7 = ensureFlag7(flag);
        return region.getFlags().containsKey(flag7.getWgFlag());
    }

    @Override
    public <F, T, U> T getFlag(Flag<U, T> flag) {
        final Flag7<F, T> flag7 = ensureFlag7(flag);
        final com.sk89q.worldguard.protection.flags.Flag<F> wgFlag = flag7.getWgFlag();
        if(!region.getFlags().containsKey(wgFlag))
            return null;
        return flag7.getFlagAdaptor().from(region.getFlag(wgFlag));
    }

    @Override
    public <F, T> void setFlag(Flag<?, T> flag, T value) {
        final Flag7<F, T> flag7 = ensureFlag7(flag);
        region.setFlag(flag7.getWgFlag(), flag7.getFlagAdaptor().to(value));
    }
}
