package fr.flo504.worldguardapi.api.vector;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * A vector that symbolize a block coordinates in three dimensions
 */
public class BlockVector3D {

    private int x;
    private int y;
    private int z;

    public BlockVector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public BlockVector3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockVector3D(BlockVector blockVector){
        x = blockVector.getBlockX();
        y = blockVector.getBlockY();
        z = blockVector.getBlockZ();
    }

    public BlockVector3D(Vector vector) {
        this.x = vector.getBlockX();
        this.y = vector.getBlockY();
        this.z = vector.getBlockZ();
    }

    public BlockVector3D(Location location){
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "BlockVector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /**
     * Static method to get an instance of {@code BlockVector3D} from a Bukkit {@code BlockVector}
     *
     * @param blockVector The vector to translate
     * @return an instance of {@code BlockVector3D} with the parametrized block vector coordinate
     */
    public static BlockVector3D fromBukkit(BlockVector blockVector){
        return new BlockVector3D(blockVector);
    }

    /**
     * Static method to get an instance of {@code BlockVector3D} from a Bukkit {@code Vector}
     *
     * @param vector The vector to translate
     * @return an instance of {@code BlockVector3D} with the parametrized vector coordinate
     */
    public static BlockVector3D fromBukkit(Vector vector){
        return new BlockVector3D(vector);
    }

    /**
     * Static method to get an instance of {@code BlockVector3D} from a Bukkit {@code Location}
     *
     * @param location The vector to translate
     * @return an instance of {@code BlockVector3D} with the parametrized location coordinate
     */
    public static BlockVector3D fromBukkit(Location location){
        return new BlockVector3D(location);
    }

}
