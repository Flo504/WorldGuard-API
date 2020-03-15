package fr.flo504.worldguardapi.v6.vectors;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import fr.flo504.worldguardapi.api.vector.BlockVector3D;
import fr.flo504.worldguardapi.api.vector.Vector3D;

public class VectorAdapter6 {

    private VectorAdapter6() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static BlockVector toWGBlockVector3D(BlockVector3D blockVector3D){
        if(blockVector3D == null)return null;
        return new BlockVector(blockVector3D.getX(), blockVector3D.getY(), blockVector3D.getZ());
    }

    public static Vector toWGVector3D(Vector3D vector3D){
        if(vector3D == null)return null;
        return new Vector(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public static BlockVector2D toWGBlockVector2D(fr.flo504.worldguardapi.api.vector.BlockVector2D blockVector2D){
        if(blockVector2D == null)return null;
        return new BlockVector2D(blockVector2D.getX(), blockVector2D.getZ());
    }

    public static Vector2D toWGVector2D(fr.flo504.worldguardapi.api.vector.Vector2D vector2D){
        if(vector2D == null)return null;
        return new Vector2D(vector2D.getX(), vector2D.getZ());
    }

    public static BlockVector3D fromWGBlockVector3D(BlockVector blockVector3D){
        if(blockVector3D == null)return null;
        return new BlockVector3D(blockVector3D.getBlockX(), blockVector3D.getBlockY(), blockVector3D.getBlockZ());
    }

    public static Vector3D fromWGVector3D(Vector vector3D){
        if(vector3D == null)return null;
        return new Vector3D(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public static fr.flo504.worldguardapi.api.vector.BlockVector2D fromWGBlockVector2D(BlockVector2D blockVector2D){
        if(blockVector2D == null)return null;
        return new fr.flo504.worldguardapi.api.vector.BlockVector2D(blockVector2D.getBlockX(), blockVector2D.getBlockZ());
    }

    public static fr.flo504.worldguardapi.api.vector.Vector2D fromWGVector2D(Vector2D vector2D){
        if(vector2D == null)return null;
        return new fr.flo504.worldguardapi.api.vector.Vector2D(vector2D.getX(), vector2D.getZ());
    }

}
