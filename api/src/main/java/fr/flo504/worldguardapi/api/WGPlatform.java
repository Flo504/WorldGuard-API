package fr.flo504.worldguardapi.api;

import fr.flo504.worldguardapi.api.exeptions.RegionAlreadyExistException;
import fr.flo504.worldguardapi.api.exeptions.RegionNotFoundException;
import fr.flo504.worldguardapi.api.region.Region;
import fr.flo504.worldguardapi.api.selection.Selection;
import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface WGPlatform {

    /**
     * Create a cuboid region
     *
     * @param world The world of the region
     * @param name The name of the region
     * @param maximumPoint The maximum point of the region
     * @param minimumPoint The minimum point of the region
     * @return A {@code Region} with the specifics parameters
     * @throws RegionAlreadyExistException if the region already exist
     */
    Region createRegion(World world, String name, BlockVector3D maximumPoint, BlockVector3D minimumPoint);

    /**
     * Create a cuboid region
     *
     * @param world The world of the region
     * @param name The name of the region
     * @param points All points of the region
     * @param maxY The maximum y of the region
     * @param minY The minimum y of the region
     * @return A {@code Region} with the specifics parameters
     * @throws RegionAlreadyExistException if the region already exist
     */
    Region createRegion(World world, String name, List<BlockVector2D> points, int maxY, int minY);

    /**
     * Get an existing region
     *
     * @param world The world of the region
     * @param name The name of the region
     * @return The region if exist
     * @throws RegionNotFoundException if the region does not exist
     */
    Region getRegion(World world, String name);

    /**
     * Check if a region exists
     *
     * @param world The world of the region
     * @param name The name of the region
     * @return true if exists, false if not
     */
    boolean existRegion(World world, String name);

    /**
     * Check if this id is a valid one
     *
     * @param id The id to check
     * @return true if the id is valid, false if not
     */
    boolean isValidRegionId(String id);

    Selection getSelection(Player player, World world);

    void applySelection(Player player, Selection selection);

}
