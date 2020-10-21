package fr.flo504.worldguardapi.v7;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.*;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.flo504.worldguardapi.api.WGPlatform;
import fr.flo504.worldguardapi.api.region.Region;
import fr.flo504.worldguardapi.api.selection.CuboidSelection;
import fr.flo504.worldguardapi.api.selection.NullSelection;
import fr.flo504.worldguardapi.api.selection.PolygonalSelection;
import fr.flo504.worldguardapi.api.selection.Selection;
import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.v7.region.Region7;
import fr.flo504.worldguardapi.v7.region.RegionManagerUtils7;
import fr.flo504.worldguardapi.v7.region.flag.FlagRegistry7;
import fr.flo504.worldguardapi.v7.selection.OtherSelection7;
import fr.flo504.worldguardapi.v7.vectors.VectorAdapter7;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Platform7 implements WGPlatform {

    private final FlagRegistry7 flagRegistry;

    public Platform7(){
        flagRegistry = new FlagRegistry7();
    }

    @Override
    public Region createRegion(World world, String name, BlockVector3D maximumPoint, BlockVector3D minimumPoint) {
        return new Region7(world, name, maximumPoint, minimumPoint, flagRegistry);
    }

    @Override
    public Region createRegion(World world, String name, List<BlockVector2D> points, int maxY, int minY) {
        return new Region7(world, name, points, maxY, minY, flagRegistry);
    }

    @Override
    public Region getRegion(World world, String name) {
        return new Region7(world, name, flagRegistry);
    }

    @Override
    public boolean existRegion(World world, String name) {
        return RegionManagerUtils7.existRegion(world, name);
    }

    @Override
    public boolean isValidRegionId(String id) {
        return ProtectedRegion.isValidId(id);
    }

    @Override
    public Selection getSelection(Player player, World world) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(world);

        final com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(world);
        final LocalSession session = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
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
            return new CuboidSelection(
                    world,
                    VectorAdapter7.fromWGBlockVector3D(cuboidRegion.getMaximumPoint()),
                    VectorAdapter7.fromWGBlockVector3D(cuboidRegion.getMinimumPoint())
            );
        }
        if(wgRegion instanceof Polygonal2DRegion){
            final Polygonal2DRegion polygonal2DRegion = (Polygonal2DRegion)wgRegion;
            return new PolygonalSelection(
                    world,
                    polygonal2DRegion.getPoints().stream().map(VectorAdapter7::fromWGBlockVector2D).collect(Collectors.toList()),
                    polygonal2DRegion.getMaximumY(),
                    polygonal2DRegion.getMinimumY()
            );
        }

        return new OtherSelection7(world, selector);
    }

    @Override
    public void applySelection(Player player, Selection selection) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(selection);
        final com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(selection.getWorld());
        final RegionSelector selector;
        switch (selection.getType()){
            case CUBOID:
                final CuboidSelection cuboidSelection = (CuboidSelection)selection;
                selector = new CuboidRegionSelector(
                        world,
                        VectorAdapter7.toWGBlockVector3D(cuboidSelection.getMaximumPoint()),
                        VectorAdapter7.toWGBlockVector3D(cuboidSelection.getMinimumPoint())
                );
                break;
            case POLYGONAL:
                final PolygonalSelection polygonalSelection = (PolygonalSelection)selection;
                selector = new Polygonal2DRegionSelector(
                        world,
                        polygonalSelection.getPoints().stream().map(VectorAdapter7::toWGBlockVector2D).collect(Collectors.toList()),
                        polygonalSelection.getMinY(),
                        polygonalSelection.getMaxY()
                );
                break;
            case NULL:
                selector = new CuboidRegionSelector(world);
                break;
            case OTHER:
                final RegionSelector exSelector = ((OtherSelection7)selection).getSelector();
                if(exSelector instanceof ConvexPolyhedralRegionSelector)
                    selector = new ConvexPolyhedralRegionSelector(exSelector);
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

        WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player)).setRegionSelector(world, selector);
    }

    @Override
    public FlagRegistry7 getFlagRegistry() {
        return flagRegistry;
    }
}
