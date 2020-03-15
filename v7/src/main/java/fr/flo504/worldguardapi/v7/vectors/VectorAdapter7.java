package fr.flo504.worldguardapi.v7.vectors;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector2;
import com.sk89q.worldedit.math.Vector3;
import fr.flo504.worldguardapi.api.vector.BlockVector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.api.vector.Vector2D;
import fr.flo504.worldguardapi.api.vector.Vector3D;

public class VectorAdapter7 {

    private VectorAdapter7() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static BlockVector3 toWGBlockVector3D(BlockVector3D blockVector3D){
        if (blockVector3D == null)return null;
        return BlockVector3.at(blockVector3D.getX(), blockVector3D.getY(), blockVector3D.getZ());
    }

    public static Vector3 toWGVector3D(Vector3D vector3D){
        if (vector3D == null)return null;
        return Vector3.at(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public static BlockVector2 toWGBlockVector2D(fr.flo504.worldguardapi.api.vector.BlockVector2D blockVector2D){
        if (blockVector2D == null)return null;
        return BlockVector2.at(blockVector2D.getX(), blockVector2D.getZ());
    }

    public static Vector2 toWGVector2D(fr.flo504.worldguardapi.api.vector.Vector2D vector2D){
        if (vector2D == null)return null;
        return Vector2.at(vector2D.getX(), vector2D.getZ());
    }

    public static BlockVector3D fromWGBlockVector3D(BlockVector3 blockVector3D){
        if (blockVector3D == null)return null;
        return new BlockVector3D(blockVector3D.getX(), blockVector3D.getY(), blockVector3D.getZ());
    }

    public static Vector3D fromWGVector3D(Vector3 vector3D){
        if (vector3D == null)return null;
        return new Vector3D(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public static BlockVector2D fromWGBlockVector2D(BlockVector2 blockVector2D){
        if (blockVector2D == null)return null;
        return new BlockVector2D(blockVector2D.getX(), blockVector2D.getZ());
    }

    public static Vector2D fromWGVector2D(Vector2 vector2D){
        if (vector2D == null)return null;
        return new Vector2D(vector2D.getX(), vector2D.getZ());
    }

}
