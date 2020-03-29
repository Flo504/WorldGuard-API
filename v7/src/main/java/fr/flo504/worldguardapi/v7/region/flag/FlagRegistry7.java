package fr.flo504.worldguardapi.v7.region.flag;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.*;
import fr.flo504.worldguardapi.api.exeptions.FlagRegisterException;
import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.FlagRegistry;
import fr.flo504.worldguardapi.api.region.flag.FlagSession;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import fr.flo504.worldguardapi.api.region.flag.adaptor.MapAdaptor;
import fr.flo504.worldguardapi.api.region.flag.adaptor.SetAdaptor;
import fr.flo504.worldguardapi.api.region.flag.adaptor.StandardAdaptor;
import fr.flo504.worldguardapi.api.region.flag.type.State;
import fr.flo504.worldguardapi.api.region.flag.type.WeatherType;
import fr.flo504.worldguardapi.v7.region.flag.adaptor.*;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.*;

public class FlagRegistry7 extends FlagRegistry {

    private final RegionGroupAdaptor regionGroupAdaptor;
    private final com.sk89q.worldguard.protection.flags.registry.FlagRegistry flagRegistry;

    public FlagRegistry7() {
        regionGroupAdaptor = new RegionGroupAdaptor();
        flagRegistry = WorldGuard.getInstance().getFlagRegistry();
    }

    @Override
    public boolean isValidName(String name) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T> Flag<T> registerWorldGuardFlag(com.sk89q.worldguard.protection.flags.Flag<?> wgFlag) {

        final Flag<T> flag = new Flag<>(wgFlag.getName());

        final FlagAdaptor<T> adaptor;
        if(wgFlag instanceof BooleanFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof StringFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof DoubleFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof EntityTypeFlag)
            adaptor = (FlagAdaptor<T>) new EntityTypeAdaptor();
        else if(wgFlag instanceof GameModeTypeFlag)
            adaptor = (FlagAdaptor<T>) new GamemodeAdaptor();
        else if(wgFlag instanceof IntegerFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof LocationFlag)
            adaptor = (FlagAdaptor<T>) new LocationAdaptor();
        else if(wgFlag instanceof MapFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof RegionGroupFlag)
            adaptor = (FlagAdaptor<T>) new RegionGroupAdaptor();
        else if(wgFlag instanceof RegistryFlag){
            if(wgFlag.equals(Flags.WEATHER_LOCK))
                adaptor = (FlagAdaptor<T>) new WeatherTypeAdaptor();
            else if(wgFlag.equals(Flags.GAME_MODE))
                adaptor = (FlagAdaptor<T>) new GamemodeAdaptor();
            else
                throw new FlagRegisterException("Registry Flag are not supported yet by the API");
        }
        else if(wgFlag instanceof SetFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof StateFlag)
            adaptor = (FlagAdaptor<T>) new StateAdaptor();
        else if(wgFlag instanceof TimestampFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof VectorFlag)
            adaptor = (FlagAdaptor<T>) new VectorAdaptor();
        else if(wgFlag instanceof WeatherTypeFlag)
            adaptor = (FlagAdaptor<T>) new WeatherTypeAdaptor();
        else if(wgFlag instanceof EnumFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else
            throw new FlagRegisterException("Can not find adaptor for the flag "+wgFlag.getName());

        final FlagSession<T> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> Flag<T> registerCustomFlag(String name, Class<T> flagType, fr.flo504.worldguardapi.api.region.flag.type.RegionGroup regionGroup, T defaultValue) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(flagType);
        Objects.requireNonNull(regionGroup);

        name = name.toLowerCase(Locale.ROOT);

        if(exist(name))
            throw new FlagRegisterException("The flag '"+name+"' already exist, choose another name");

        final Flag<T> flag = new Flag<>(name);

        final com.sk89q.worldguard.protection.flags.RegionGroup wgRegionGroup = regionGroupAdaptor.to(regionGroup);

        final FlagAdaptor<T> adaptor;
        final com.sk89q.worldguard.protection.flags.Flag<?> wgFlag;

        if(flagType == Boolean.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new BooleanFlag(name, wgRegionGroup);
        }
        else if(flagType == String.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new StringFlag(name, wgRegionGroup, defaultValue == null ? null : (String) adaptor.to(defaultValue));
        }
        else if(flagType == Double.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new DoubleFlag(name, wgRegionGroup);
        }
        else if(flagType == EntityType.class){
            throw new FlagRegisterException("Disabled by the WorldGuard API");
        }
        else if(flagType == GameMode.class){
            throw new FlagRegisterException("Disabled by the WorldGuard API");
        }
        else if(flagType == Integer.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new IntegerFlag(name, wgRegionGroup);
        }
        else if(flagType == Location.class){
            adaptor = (FlagAdaptor<T>) new LocationAdaptor();
            wgFlag = new LocationFlag(name, wgRegionGroup);
        }
        else if(flagType == Map.class) {
            throw new FlagRegisterException("Unsupported type for this method, use registerCustomMapFlag method. For this you must cast the flag registry in (FlagRegistry7)");
        }
        else if(flagType == fr.flo504.worldguardapi.api.region.flag.type.RegionGroup.class){
            adaptor = (FlagAdaptor<T>) new RegionGroupAdaptor();
            wgFlag = new RegionGroupFlag(name, wgRegionGroup);
        }
        else if(flagType == Keyed.class){
            throw new FlagRegisterException("Registry Flag are not supported yet by the API");
        }
        else if(flagType == Set.class){
            throw new FlagRegisterException("Unsupported type for this method, use registerCustomSetFlag method");}
        else if(flagType == State.class){
            adaptor = (FlagAdaptor<T>) new StateAdaptor();
            wgFlag = new StateFlag(name, defaultValue == State.ALLOW, wgRegionGroup);
        }
        else if(flagType == Instant.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new TimestampFlag(name, wgRegionGroup);
        }
        else if(flagType == Vector.class){
            adaptor = (FlagAdaptor<T>) new VectorAdaptor();
            wgFlag = new VectorFlag(name, wgRegionGroup);
        }
        else if(flagType == WeatherType.class){
            throw new FlagRegisterException("Disabled by the WorldGuard API");
        }
        else if(flagType.isEnum()){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new EnumFlag(name, flagType, wgRegionGroup);
        }
        else
            throw new FlagRegisterException("Can not find adaptor for the flag "+name);

        flagRegistry.register(wgFlag);

        final FlagSession<T> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    @Override
    public <T> Flag<Set<T>> registerCustomSetFlag(String name, Flag<T> flagType, fr.flo504.worldguardapi.api.region.flag.type.RegionGroup regionGroup) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(flagType);
        Objects.requireNonNull(regionGroup);

        name = name.toLowerCase(Locale.ROOT);

        if(exist(name))
            throw new FlagRegisterException("The flag '"+name+"' already exist, choose another name");

        final Optional<FlagSession<T>> optionalFlagSession = getSession(flagType);

        if(!optionalFlagSession.isPresent())
            throw new FlagRegisterException("Attempting to register a SetFlag with a Flag that is not registered");

        final FlagSession<T> flagSession = optionalFlagSession.get();

        final Flag<Set<T>> flag = new Flag<>(name);

        final com.sk89q.worldguard.protection.flags.RegionGroup wgRegionGroup = regionGroupAdaptor.to(regionGroup);

        final FlagAdaptor<Set<T>> adaptor = new SetAdaptor<>(flagSession.getAdaptor());
        final com.sk89q.worldguard.protection.flags.Flag<?> wgFlag = new SetFlag<>(name, wgRegionGroup, (com.sk89q.worldguard.protection.flags.Flag<?>) flagSession.getWgFlag());

        flagRegistry.register(wgFlag);

        final FlagSession<Set<T>> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    public <K, V> Flag<Map<K, V>> registerCustomMapFlag(String name, Flag<K> keyFlagType, Flag<V> valueFlagType, fr.flo504.worldguardapi.api.region.flag.type.RegionGroup regionGroup){
        Objects.requireNonNull(name);
        Objects.requireNonNull(keyFlagType);
        Objects.requireNonNull(valueFlagType);
        Objects.requireNonNull(regionGroup);

        name = name.toLowerCase(Locale.ROOT);

        if(exist(name))
            throw new FlagRegisterException("The flag '"+name+"' already exist, choose another name");

        final Optional<FlagSession<K>> optionalFlagSessionKey = getSession(keyFlagType);

        if(!optionalFlagSessionKey.isPresent())
            throw new FlagRegisterException("Attempting to register a MapFlag with a FlagKey that is not registered");

        final FlagSession<K> keyFlagSession = optionalFlagSessionKey.get();

        final Optional<FlagSession<V>> optionalFlagSessionValue = getSession(valueFlagType);

        if(!optionalFlagSessionValue.isPresent())
            throw new FlagRegisterException("Attempting to register a MapFlag with a FlagValue that is not registered");

        final FlagSession<V> valueFlagSession = optionalFlagSessionValue.get();

        final Flag<Map<K, V>> flag = new Flag<>(name);

        final com.sk89q.worldguard.protection.flags.RegionGroup wgRegionGroup = regionGroupAdaptor.to(regionGroup);

        final FlagAdaptor<Map<K, V>> adaptor = new MapAdaptor<>(keyFlagSession.getAdaptor(), valueFlagSession.getAdaptor());
        final com.sk89q.worldguard.protection.flags.Flag<?> wgFlag = new MapFlag<>(
                name,
                wgRegionGroup,
                (com.sk89q.worldguard.protection.flags.Flag<?>) keyFlagSession.getWgFlag(),
                (com.sk89q.worldguard.protection.flags.Flag<?>)valueFlagSession.getAdaptor()
        );

        flagRegistry.register(wgFlag);

        final FlagSession<Map<K, V>> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    public final Flag<State> PASSTHROUGH = registerWorldGuardFlag(Flags.PASSTHROUGH);
    public final Flag<State> BUILD = registerWorldGuardFlag(Flags.BUILD);
    public final Flag<State> BLOCK_BREAK = registerWorldGuardFlag(Flags.BLOCK_BREAK);
    public final Flag<State> BLOCK_PLACE = registerWorldGuardFlag(Flags.BLOCK_PLACE);
    public final Flag<State> USE = registerWorldGuardFlag(Flags.USE);
    public final Flag<State> INTERACT = registerWorldGuardFlag(Flags.INTERACT);
    public final Flag<State> DAMAGE_ANIMALS = registerWorldGuardFlag(Flags.DAMAGE_ANIMALS);
    public final Flag<State> PVP = registerWorldGuardFlag(Flags.PVP);
    public final Flag<State> SLEEP = registerWorldGuardFlag(Flags.SLEEP);
    public final Flag<State> TNT = registerWorldGuardFlag(Flags.TNT);
    public final Flag<State> CHEST_ACCESS = registerWorldGuardFlag(Flags.CHEST_ACCESS);
    public final Flag<State> PLACE_VEHICLE = registerWorldGuardFlag(Flags.PLACE_VEHICLE);
    public final Flag<State> DESTROY_VEHICLE = registerWorldGuardFlag(Flags.DESTROY_VEHICLE);
    public final Flag<State> LIGHTER = registerWorldGuardFlag(Flags.LIGHTER);
    public final Flag<State> RIDE = registerWorldGuardFlag(Flags.RIDE);
    public final Flag<State> POTION_SPLASH = registerWorldGuardFlag(Flags.POTION_SPLASH);
    public final Flag<State> ITEM_FRAME_ROTATE = registerWorldGuardFlag(Flags.ITEM_FRAME_ROTATE);
    public final Flag<State> TRAMPLE_BLOCKS = registerWorldGuardFlag(Flags.TRAMPLE_BLOCKS);
    public final Flag<State> ITEM_PICKUP = registerWorldGuardFlag(Flags.ITEM_PICKUP);
    public final Flag<State> ITEM_DROP = registerWorldGuardFlag(Flags.ITEM_DROP);
    public final Flag<State> EXP_DROPS = registerWorldGuardFlag(Flags.EXP_DROPS);
    public final Flag<State> MOB_DAMAGE = registerWorldGuardFlag(Flags.MOB_DAMAGE);
    public final Flag<State> CREEPER_EXPLOSION = registerWorldGuardFlag(Flags.CREEPER_EXPLOSION);
    public final Flag<State> ENDERDRAGON_BLOCK_DAMAGE = registerWorldGuardFlag(Flags.ENDERDRAGON_BLOCK_DAMAGE);
    public final Flag<State> GHAST_FIREBALL = registerWorldGuardFlag(Flags.GHAST_FIREBALL);
    public final Flag<State> FIREWORK_DAMAGE = registerWorldGuardFlag(Flags.FIREWORK_DAMAGE);
    public final Flag<State> OTHER_EXPLOSION = registerWorldGuardFlag(Flags.OTHER_EXPLOSION);
    public final Flag<State> WITHER_DAMAGE = registerWorldGuardFlag(Flags.WITHER_DAMAGE);
    public final Flag<State> ENDER_BUILD = registerWorldGuardFlag(Flags.ENDER_BUILD);
    public final Flag<State> SNOWMAN_TRAILS = registerWorldGuardFlag(Flags.SNOWMAN_TRAILS);
    public final Flag<State> ENTITY_PAINTING_DESTROY = registerWorldGuardFlag(Flags.ENTITY_PAINTING_DESTROY);
    public final Flag<State> ENTITY_ITEM_FRAME_DESTROY = registerWorldGuardFlag(Flags.ENTITY_ITEM_FRAME_DESTROY);
    public final Flag<State> MOB_SPAWNING = registerWorldGuardFlag(Flags.MOB_SPAWNING);
    public final Flag<Set<EntityType>> DENY_SPAWN = registerWorldGuardFlag(Flags.DENY_SPAWN);
    public final Flag<State> PISTONS = registerWorldGuardFlag(Flags.PISTONS);
    public final Flag<State> FIRE_SPREAD = registerWorldGuardFlag(Flags.FIRE_SPREAD);
    public final Flag<State> LAVA_FIRE = registerWorldGuardFlag(Flags.LAVA_FIRE);
    public final Flag<State> LIGHTNING = registerWorldGuardFlag(Flags.LIGHTNING);
    public final Flag<State> SNOW_FALL = registerWorldGuardFlag(Flags.SNOW_FALL);
    public final Flag<State> SNOW_MELT = registerWorldGuardFlag(Flags.SNOW_MELT);
    public final Flag<State> ICE_FORM = registerWorldGuardFlag(Flags.ICE_FORM);
    public final Flag<State> ICE_MELT = registerWorldGuardFlag(Flags.ICE_MELT);
    public final Flag<State> FROSTED_ICE_MELT = registerWorldGuardFlag(Flags.FROSTED_ICE_MELT);
    public final Flag<State> FROSTED_ICE_FORM = registerWorldGuardFlag(Flags.FROSTED_ICE_FORM);
    public final Flag<State> MUSHROOMS = registerWorldGuardFlag(Flags.MUSHROOMS);
    public final Flag<State> LEAF_DECAY = registerWorldGuardFlag(Flags.LEAF_DECAY);
    public final Flag<State> GRASS_SPREAD = registerWorldGuardFlag(Flags.GRASS_SPREAD);
    public final Flag<State> MYCELIUM_SPREAD = registerWorldGuardFlag(Flags.MYCELIUM_SPREAD);
    public final Flag<State> VINE_GROWTH = registerWorldGuardFlag(Flags.VINE_GROWTH);
    public final Flag<State> CROP_GROWTH = registerWorldGuardFlag(Flags.CROP_GROWTH);
    public final Flag<State> SOIL_DRY = registerWorldGuardFlag(Flags.SOIL_DRY);
    public final Flag<State> WATER_FLOW = registerWorldGuardFlag(Flags.WATER_FLOW);
    public final Flag<State> LAVA_FLOW = registerWorldGuardFlag(Flags.LAVA_FLOW);
    public final Flag<WeatherType> WEATHER_LOCK = registerWorldGuardFlag(Flags.WEATHER_LOCK);
    public final Flag<String> TIME_LOCK = registerWorldGuardFlag(Flags.TIME_LOCK);
    public final Flag<State> SEND_CHAT = registerWorldGuardFlag(Flags.SEND_CHAT);
    public final Flag<State> RECEIVE_CHAT = registerWorldGuardFlag(Flags.RECEIVE_CHAT);
    public final Flag<Set<String>> BLOCKED_CMDS = registerWorldGuardFlag(Flags.BLOCKED_CMDS);
    public final Flag<Set<String>> ALLOWED_CMDS = registerWorldGuardFlag(Flags.ALLOWED_CMDS);
    public final Flag<Location> TELE_LOC = registerWorldGuardFlag(Flags.TELE_LOC);
    public final Flag<Location> SPAWN_LOC = registerWorldGuardFlag(Flags.SPAWN_LOC);
    public final Flag<State> INVINCIBILITY = registerWorldGuardFlag(Flags.INVINCIBILITY);
    public final Flag<State> FALL_DAMAGE = registerWorldGuardFlag(Flags.FALL_DAMAGE);
    public final Flag<State> ENTRY = registerWorldGuardFlag(Flags.ENTRY);
    public final Flag<State> EXIT = registerWorldGuardFlag(Flags.EXIT);
    public final Flag<Boolean> EXIT_OVERRIDE = registerWorldGuardFlag(Flags.EXIT_OVERRIDE);
    public final Flag<State> EXIT_VIA_TELEPORT = registerWorldGuardFlag(Flags.EXIT_VIA_TELEPORT);
    public final Flag<State> ENDERPEARL = registerWorldGuardFlag(Flags.ENDERPEARL);
    public final Flag<State> CHORUS_TELEPORT = registerWorldGuardFlag(Flags.CHORUS_TELEPORT);
    public final Flag<String> GREET_MESSAGE = registerWorldGuardFlag(Flags.GREET_MESSAGE);
    public final Flag<String> FAREWELL_MESSAGE = registerWorldGuardFlag(Flags.FAREWELL_MESSAGE);
    public final Flag<String> GREET_TITLE = registerWorldGuardFlag(Flags.GREET_TITLE);
    public final Flag<String> FAREWELL_TITLE = registerWorldGuardFlag(Flags.FAREWELL_TITLE);
    public final Flag<Boolean> NOTIFY_ENTER = registerWorldGuardFlag(Flags.NOTIFY_ENTER);
    public final Flag<Boolean> NOTIFY_LEAVE = registerWorldGuardFlag(Flags.NOTIFY_LEAVE);
    public final Flag<GameMode> GAME_MODE = registerWorldGuardFlag(Flags.GAME_MODE);
    public final Flag<Integer> HEAL_DELAY = registerWorldGuardFlag(Flags.HEAL_DELAY);
    public final Flag<Integer> HEAL_AMOUNT = registerWorldGuardFlag(Flags.HEAL_AMOUNT);
    public final Flag<Double> MIN_HEAL = registerWorldGuardFlag(Flags.MIN_HEAL);
    public final Flag<Double> MAX_HEAL = registerWorldGuardFlag(Flags.MAX_HEAL);
    public final Flag<Integer> FEED_DELAY = registerWorldGuardFlag(Flags.FEED_DELAY);
    public final Flag<Integer> FEED_AMOUNT = registerWorldGuardFlag(Flags.FEED_AMOUNT);
    public final Flag<Integer> MIN_FOOD = registerWorldGuardFlag(Flags.MIN_FOOD);
    public final Flag<Integer> MAX_FOOD = registerWorldGuardFlag(Flags.MAX_FOOD);
    public final Flag<String> DENY_MESSAGE = registerWorldGuardFlag(Flags.DENY_MESSAGE);
    public final Flag<String> ENTRY_DENY_MESSAGE = registerWorldGuardFlag(Flags.ENTRY_DENY_MESSAGE);
    public final Flag<String> EXIT_DENY_MESSAGE = registerWorldGuardFlag(Flags.EXIT_DENY_MESSAGE);

}
