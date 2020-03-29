package fr.flo504.worldguardapi.v6;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.*;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.flo504.worldguardapi.api.WGPlatform;
import fr.flo504.worldguardapi.api.exeptions.WorldGuardAPIException;
import fr.flo504.worldguardapi.api.region.Region;
import fr.flo504.worldguardapi.api.selection.CuboidSelection;
import fr.flo504.worldguardapi.api.selection.NullSelection;
import fr.flo504.worldguardapi.api.selection.PolygonalSelection;
import fr.flo504.worldguardapi.api.selection.Selection;
import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.v6.region.Region6;
import fr.flo504.worldguardapi.v6.region.RegionManagerUtils6;
import fr.flo504.worldguardapi.v6.region.flag.FlagRegistry6;
import fr.flo504.worldguardapi.v6.selection.OtherSelection6;
import fr.flo504.worldguardapi.v6.vectors.VectorAdapter6;
import fr.flo504.worldguardapi.v6.worldedit.WorldEditManager;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Platform6 implements WGPlatform {

    private WorldEditManager worldEditManager;
    private FlagRegistry6 flagRegistry;

    public Platform6() {
        worldEditManager = new WorldEditManager();
        flagRegistry = new FlagRegistry6();
    }

    @Override
    public Region createRegion(World world, String name, BlockVector3D maximumPoint, BlockVector3D minimumPoint) {
        return new Region6(world, name, maximumPoint, minimumPoint, flagRegistry);
    }

    @Override
    public Region createRegion(World world, String name, List<BlockVector2D> points, int maxY, int minY) {
        return new Region6(world, name, points, maxY, minY, flagRegistry);
    }

    @Override
    public Region getRegion(World world, String name) {
        return new Region6(world, name, flagRegistry);
    }

    @Override
    public boolean existRegion(World world, String name) {
        return RegionManagerUtils6.existRegion(world, name);
    }

    @Override
    public boolean isValidRegionId(String id) {
        return ProtectedRegion.isValidId(id);
    }

    @Override
    public Selection getSelection(Player player, World world) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(world);

        final BukkitWorld wgWorld = worldEditManager.getWorld(world);
        final LocalSession session = worldEditManager.getWorldEditPlugin().getSession(player);
        final RegionSelector selector = session.getRegionSelector(wgWorld);
        final com.sk89q.worldedit.regions.Region wgRegion;
        try {
            wgRegion = session.getSelection(wgWorld);
        } catch (IncompleteRegionException e) {
            return new NullSelection(world);
        }

        if(wgRegion == null || selector == null){
            return new NullSelection(world);
        }

        if(wgRegion instanceof CuboidRegion){
            final CuboidRegion cuboidRegion = (CuboidRegion)wgRegion;
            final Vector max = cuboidRegion.getMaximumPoint();
            final Vector min = cuboidRegion.getMinimumPoint();
            return new CuboidSelection(
                    world,
                    new BlockVector3D(max.getBlockX(), max.getBlockY(), max.getBlockZ()),
                    new BlockVector3D(min.getBlockX(), min.getBlockY(), min.getBlockZ())
            );
        }
        if(wgRegion instanceof Polygonal2DRegion){
            final Polygonal2DRegion polygonal2DRegion = (Polygonal2DRegion)wgRegion;
            return new PolygonalSelection(
                    world,
                    polygonal2DRegion.getPoints().stream().map(VectorAdapter6::fromWGBlockVector2D).collect(Collectors.toList()),
                    polygonal2DRegion.getMaximumY(),
                    polygonal2DRegion.getMinimumY()
            );
        }

        final int maxVertices =
                selector instanceof ConvexPolyhedralRegionSelector ?
                        MaxVerticesGetter.getMaxVertices((ConvexPolyhedralRegionSelector) selector) :
                        0;

        return new OtherSelection6(world, selector, maxVertices);
    }

    @Override
    public void applySelection(Player player, Selection selection) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(selection);
        final BukkitWorld world = worldEditManager.getWorld(selection.getWorld());
        final RegionSelector selector;
        switch (selection.getType()){
            case CUBOID:
                final CuboidSelection cuboidSelection = (CuboidSelection)selection;
                selector = new CuboidRegionSelector(
                        world,
                        VectorAdapter6.toWGBlockVector3D(cuboidSelection.getMaximumPoint()),
                        VectorAdapter6.toWGBlockVector3D(cuboidSelection.getMinimumPoint())
                );
                break;
            case POLYGONAL:
                final PolygonalSelection polygonalSelection = (PolygonalSelection)selection;
                selector = new Polygonal2DRegionSelector(
                        world,
                        polygonalSelection.getPoints().stream().map(VectorAdapter6::toWGBlockVector2D).collect(Collectors.toList()),
                        polygonalSelection.getMinY(),
                        polygonalSelection.getMaxY()
                );
                break;
            case NULL:
                selector = new CuboidRegionSelector(world);
                break;
            case OTHER:
                final OtherSelection6 otherSelection = (OtherSelection6) selection;
                final RegionSelector exSelector = otherSelection.getSelector();
                if(exSelector instanceof ConvexPolyhedralRegionSelector)
                    selector = new ConvexPolyhedralRegionSelector(exSelector, otherSelection.getMaxVertices());
                else if(exSelector instanceof CylinderRegionSelector)
                    selector = new CylinderRegionSelector(exSelector);
                else if(exSelector instanceof SphereRegionSelector)
                    selector = new SphereRegionSelector(exSelector);
                else if(exSelector instanceof EllipsoidRegionSelector)
                    selector = new EllipsoidRegionSelector(exSelector);
                else
                    selector = new CuboidRegionSelector(world);
                break;
            default:
                selector = new CuboidRegionSelector();
        }

        worldEditManager.getWorldEditPlugin().getSession(player).setRegionSelector(world, selector);
    }

    public final static class MaxVerticesGetter {

        private MaxVerticesGetter() throws IllegalAccessException {
            throw new IllegalAccessException("Utility class");
        }

        private static final Field maxVerticesField;

        static{
            try {
                maxVerticesField = ConvexPolyhedralRegionSelector.class.getDeclaredField("maxVertices");
            } catch (NoSuchFieldException e) {
                throw new WorldGuardAPIException("Error during the initialization, please report the following error to the author", e);
            }
            maxVerticesField.setAccessible(true);
        }

        public static int getMaxVertices(ConvexPolyhedralRegionSelector selector){
            if(selector == null)return 0;
            try {
                return (int) maxVerticesField.get(selector);
            } catch (IllegalAccessException e) {
                throw new WorldGuardAPIException("Error when trying to get the maximum vertices of a region selector, please report the following error to the author", e);
            }
        }

    }

    @Override
    public FlagRegistry6 getFlagRegistry() {
        return flagRegistry;
    }
}
