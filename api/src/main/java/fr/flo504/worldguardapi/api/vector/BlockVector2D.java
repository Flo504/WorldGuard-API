package fr.flo504.worldguardapi.api.vector;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * A vector that symbolize a block coordinates in two dimensions
 */
public class BlockVector2D {

    private int x;
    private int z;

    public BlockVector2D() {
        x = 0;
        z = 0;
    }

    public BlockVector2D(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public BlockVector2D(BlockVector blockVector){
        x = blockVector.getBlockX();
        z = blockVector.getBlockZ();
    }

    public BlockVector2D(Vector vector) {
        this.x = vector.getBlockX();
        this.z = vector.getBlockZ();
    }

    public BlockVector2D(Location location){
        this.x = location.getBlockX();
        this.z = location.getBlockZ();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "BlockVector2D{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    /**
     * Static method to get an instance of {@code BlockVector2D} from a Bukkit {@code BlockVector}
     *
     * @param blockVector The vector to translate
     * @return an instance of {@code BlockVector2D} with the parametrized block vector coordinate
     */
    public static BlockVector2D fromBukkit(BlockVector blockVector){
        return new BlockVector2D(blockVector);
    }

    /**
     * Static method to get an instance of {@code BlockVector2D} from a Bukkit {@code Vector}
     *
     * @param vector The vector to translate
     * @return an instance of {@code BlockVector2D} with the parametrized vector coordinate
     */
    public static BlockVector2D fromBukkit(Vector vector){
        return new BlockVector2D(vector);
    }

    /**
     * Static method to get an instance of {@code BlockVector2D} from a Bukkit {@code Location}
     *
     * @param location The vector to translate
     * @return an instance of {@code BlockVector2D} with the parametrized location coordinate
     */
    public static BlockVector2D fromBukkit(Location location){
        return new BlockVector2D(location);
    }

}
