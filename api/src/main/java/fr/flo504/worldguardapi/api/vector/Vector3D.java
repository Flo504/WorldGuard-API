package fr.flo504.worldguardapi.api.vector;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * A vector that symbolize a point coordinates in two dimensions
 */
public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(BlockVector blockVector){
        this.x = blockVector.getX();
        this.y = blockVector.getY();
        this.z = blockVector.getZ();
    }

    public Vector3D(Vector vector){
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    public Vector3D(Location location){
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /**
     * Static method to get an instance of {@code Vector3D} from a Bukkit {@code BlockVector}
     *
     * @param blockVector The vector to translate
     * @return an instance of {@code Vector3D} with the parametrized block vector coordinate
     */
    public static Vector3D fromBukkit(BlockVector blockVector){
        return new Vector3D(blockVector);
    }

    /**
     * Static method to get an instance of {@code Vector3D} from a Bukkit {@code Vector}
     *
     * @param vector The vector to translate
     * @return an instance of {@code Vector3D} with the parametrized vector coordinate
     */
    public static Vector3D fromBukkit(Vector vector){
        return new Vector3D(vector);
    }

    /**
     * Static method to get an instance of {@code Vector3D} from a Bukkit {@code Location}
     *
     * @param location The vector to translate
     * @return an instance of {@code Vector3D} with the parametrized location coordinate
     */
    public static Vector3D fromBukkit(Location location){
        return new Vector3D(location);
    }

}
