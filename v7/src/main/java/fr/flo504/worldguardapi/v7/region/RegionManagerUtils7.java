package fr.flo504.worldguardapi.v7.region;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseItem;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extension.platform.Platform;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Direction;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.TreeGenerator;
import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.weather.WeatherType;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fr.flo504.worldguardapi.api.exeptions.WorldGuardAPIException;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.util.List;

public class RegionManagerUtils7 {

    private RegionManagerUtils7() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    private final static RegionContainer regionContainer;

    static{
        regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    public static boolean existRegion(World world, String name){
        final RegionManager regionManager = regionContainer.get(new WGWorldName(world.getName()));
        if(regionManager == null)
            return false;
        return regionManager.hasRegion(name);
    }

    private static RegionManager getRegionManager(World world){
        final WGWorldName wgWorldName = new WGWorldName(world.getName());
        RegionManager regionManager = regionContainer.get(wgWorldName);
        if(regionManager == null) {
            regionContainer.reload();
            regionManager = regionContainer.get(wgWorldName);
            if(regionManager == null)
                throw new WorldGuardAPIException("Can not load the region manager for this world");
        }
        return regionManager;
    }

    public static void addRegion(World world, ProtectedRegion region){
        final RegionManager regionManager = getRegionManager(world);
        regionManager.addRegion(region);
    }

    public static void removeRegion(World world, String name, RemovalStrategy strategy){
        final RegionManager regionManager = getRegionManager(world);
        regionManager.removeRegion(name, strategy);
    }

    public static ProtectedRegion getRegion(World world, String name){
        final RegionManager regionManager = getRegionManager(world);
        return regionManager.getRegion(name);
    }

    private final static class WGWorldName implements com.sk89q.worldedit.world.World{

        private final String name;

        public WGWorldName(String name){
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getMaxY() {
            return 0;
        }

        @Override
        public Mask createLiquidMask() {
            return null;
        }

        @Override
        public boolean useItem(BlockVector3 blockVector3, BaseItem baseItem, Direction direction) {
            return false;
        }

        @Override
        public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 blockVector3, B b, boolean b1) {
            return false;
        }

        @Override
        public boolean notifyAndLightBlock(BlockVector3 blockVector3, BlockState blockState) {
            return false;
        }

        @Override
        public int getBlockLightLevel(BlockVector3 blockVector3) {
            return 0;
        }

        @Override
        public boolean clearContainerBlockContents(BlockVector3 blockVector3) {
            return false;
        }

        @Override
        public void dropItem(Vector3 vector3, BaseItemStack baseItemStack, int i) {}

        @Override
        public void dropItem(Vector3 vector3, BaseItemStack baseItemStack) {}

        @Override
        public void simulateBlockMine(BlockVector3 blockVector3) {}

        @Override
        public boolean regenerate(Region region, EditSession editSession) {
            return false;
        }

        @Override
        public boolean generateTree(TreeGenerator.TreeType treeType, EditSession editSession, BlockVector3 blockVector3) {
            return false;
        }

        @Override
        public void checkLoadedChunk(BlockVector3 blockVector3) {}

        @Override
        public void fixAfterFastMode(Iterable<BlockVector2> iterable) {}

        @Override
        public void fixLighting(Iterable<BlockVector2> iterable) {}

        @Override
        public boolean playEffect(Vector3 vector3, int i, int i1) {
            return false;
        }

        @Override
        public boolean queueBlockBreakEffect(Platform platform, BlockVector3 blockVector3, BlockType blockType, double v) {
            return false;
        }

        @Override
        public WeatherType getWeather() {
            return null;
        }

        @Override
        public long getRemainingWeatherDuration() {
            return 0;
        }

        @Override
        public void setWeather(WeatherType weatherType) {}

        @Override
        public void setWeather(WeatherType weatherType, long l) {}

        @Override
        public BlockVector3 getSpawnPosition() {
            return null;
        }

        @Override
        public BlockVector3 getMinimumPoint() {
            return null;
        }

        @Override
        public BlockVector3 getMaximumPoint() {
            return null;
        }

        @Override
        public List<? extends Entity> getEntities(Region region) {
            return null;
        }

        @Override
        public List<? extends Entity> getEntities() {
            return null;
        }

        @Nullable
        @Override
        public Entity createEntity(Location location, BaseEntity baseEntity) {
            return null;
        }

        @Override
        public BlockState getBlock(BlockVector3 blockVector3) {
            return null;
        }

        @Override
        public BaseBlock getFullBlock(BlockVector3 blockVector3) {
            return null;
        }

        @Override
        public BiomeType getBiome(BlockVector2 blockVector2) {
            return null;
        }

        @Override
        public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 blockVector3, T t) {
            return false;
        }

        @Override
        public boolean setBiome(BlockVector2 blockVector2, BiomeType biomeType) {
            return false;
        }

        @Nullable
        @Override
        public Operation commit() {
            return null;
        }
    }

}
