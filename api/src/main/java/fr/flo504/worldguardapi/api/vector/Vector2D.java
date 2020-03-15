package fr.flo504.worldguardapi.api.vector;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * A vector that symbolize a point coordinates in two dimensions
 */
public class Vector2D {

    private double x;
    private double z;

    public Vector2D() {
        x = 0;
        z = 0;
    }

    public Vector2D(double x, double z) {
        this.x = x;
        this.z = z;
    }
    
    public Vector2D(BlockVector blockVector){
        this.x = blockVector.getX();
        this.z = blockVector.getZ();
    }
    
    public Vector2D(Vector vector){
        this.x = vector.getX();
        this.z = vector.getZ();
    }
    
    public Vector2D(Location location){
        this.x = location.getX();
        this.z = location.getZ();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    /**
     * Static method to get an instance of {@code Vector2D} from a Bukkit {@code BlockVector}
     *
     * @param blockVector The vector to translate
     * @return an instance of {@code Vector2D} with the parametrized block vector coordinate
     */
    public static Vector2D fromBukkit(BlockVector blockVector){
        return new Vector2D(blockVector);
    }

    /**
     * Static method to get an instance of {@code Vector2D} from a Bukkit {@code Vector}
     *
     * @param vector The vector to translate
     * @return an instance of {@code Vector2D} with the parametrized vector coordinate
     */
    public static Vector2D fromBukkit(Vector vector){
        return new Vector2D(vector);
    }

    /**
     * Static method to get an instance of {@code Vector2D} from a Bukkit {@code Location}
     *
     * @param location The vector to translate
     * @return an instance of {@code Vector2D} with the parametrized location coordinate
     */
    public static Vector2D fromBukkit(Location location){
        return new Vector2D(location);
    }
    
}
