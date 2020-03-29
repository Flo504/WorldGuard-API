package fr.flo504.worldguardapi.v6.region.flag;

import com.sk89q.worldguard.protection.flags.*;
import fr.flo504.worldguardapi.api.exeptions.FlagRegisterException;
import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.FlagRegistry;
import fr.flo504.worldguardapi.api.region.flag.FlagSession;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import fr.flo504.worldguardapi.api.region.flag.adaptor.SetAdaptor;
import fr.flo504.worldguardapi.api.region.flag.adaptor.StandardAdaptor;
import fr.flo504.worldguardapi.api.region.flag.type.RegionGroup;
import fr.flo504.worldguardapi.api.region.flag.type.State;
import fr.flo504.worldguardapi.api.region.flag.type.WeatherType;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.LocationAdaptor;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.RegionGroupAdaptor;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.StateAdaptor;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.VectorAdaptor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class FlagRegistry6 extends FlagRegistry {

    private final RegionGroupAdaptor regionGroupAdaptor;

    public FlagRegistry6() {
        this.regionGroupAdaptor = new RegionGroupAdaptor();
    }

    @Override
    public boolean isValidName(String name) {
        return true;
    }

    @SuppressWarnings("unchecked")
    private <T> FlagAdaptor<T> getAdaptor(com.sk89q.worldguard.protection.flags.Flag<?> wgFlag){
        final FlagAdaptor<T> adaptor;
        if(wgFlag instanceof BooleanFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof StringFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof DoubleFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof EntityTypeFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof IntegerFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else if(wgFlag instanceof LocationFlag)
            adaptor = (FlagAdaptor<T>) new LocationAdaptor();
        else if(wgFlag instanceof RegionGroupFlag)
            adaptor = (FlagAdaptor<T>) new RegionGroupAdaptor();
        else if(wgFlag instanceof SetFlag)
            adaptor = (FlagAdaptor<T>) new SetAdaptor<>(getAdaptor(((SetFlag<?>)wgFlag).getType()));
        else if(wgFlag instanceof StateFlag)
            adaptor = (FlagAdaptor<T>) new StateAdaptor();
        else if(wgFlag instanceof VectorFlag)
            adaptor = (FlagAdaptor<T>) new VectorAdaptor();
        else if(wgFlag instanceof EnumFlag)
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
        else
            throw new FlagRegisterException("Can not find adaptor for the flag "+wgFlag.getName());
        return adaptor;
    }

    private <T> Flag<T> registerWorldGuardFlag(com.sk89q.worldguard.protection.flags.Flag<?> wgFlag) {

        final Flag<T> flag = new Flag<>(wgFlag.getName());

        final FlagAdaptor<T> adaptor = getAdaptor(wgFlag);

        final FlagSession<T> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> Flag<T> registerCustomFlag(String name, Class<T> flagType, RegionGroup regionGroup, T defaultValue) {
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
            wgFlag = new StringFlag(name, wgRegionGroup,  defaultValue == null ? null : (String) adaptor.to(defaultValue));
        }
        else if(flagType == Double.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new DoubleFlag(name, wgRegionGroup);
        }
        else if(flagType == EntityType.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new EntityTypeFlag(name, wgRegionGroup);
        }
        else if(flagType == Integer.class){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new IntegerFlag(name, wgRegionGroup);
        }
        else if(flagType == Location.class){
            adaptor = (FlagAdaptor<T>) new LocationAdaptor();
            wgFlag = new LocationFlag(name, wgRegionGroup);
        }
        else if(flagType == RegionGroup.class){
            adaptor = (FlagAdaptor<T>) new RegionGroupAdaptor();
            wgFlag = new RegionGroupFlag(name, wgRegionGroup);
        }
        else if(flagType == Set.class){
            throw new FlagRegisterException("Unsupported type for this method, use registerCustomSetFlag method");
        }
        else if(flagType == State.class){
            adaptor = (FlagAdaptor<T>) new StateAdaptor();
            wgFlag = new StateFlag(name, defaultValue == State.ALLOW, wgRegionGroup);
        }
        else if(flagType == Vector.class){
            adaptor = (FlagAdaptor<T>) new VectorAdaptor();
            wgFlag = new VectorFlag(name, wgRegionGroup);
        }
        else if(flagType.isEnum()){
            adaptor = (FlagAdaptor<T>) new StandardAdaptor();
            wgFlag = new EnumFlag(name, flagType, wgRegionGroup);
        }
        else
            throw new FlagRegisterException("Can not find flag type for "+flagType.getSimpleName());

        final FlagSession<T> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Flag<Set<T>> registerCustomSetFlag(String name, Flag<T> flagType, RegionGroup regionGroup) {
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
        final com.sk89q.worldguard.protection.flags.Flag<?> wgFlag = new SetFlag<>(name, wgRegionGroup, (com.sk89q.worldguard.protection.flags.Flag<Object>) flagSession.getWgFlag());

        final FlagSession<Set<T>> session = new FlagSession<>(flag, wgFlag, adaptor);
        registerFlag(session);
        return flag;
    }

    public final Flag<State> PASSTHROUGH = registerWorldGuardFlag(DefaultFlag.PASSTHROUGH);
    @Deprecated
    public final Flag<RegionGroup> CONSTRUCT = registerWorldGuardFlag(DefaultFlag.CONSTRUCT);
    public final Flag<State> BUILD = registerWorldGuardFlag(DefaultFlag.BUILD);
    public final Flag<State> BLOCK_BREAK = registerWorldGuardFlag(DefaultFlag.BLOCK_BREAK);
    public final Flag<State> BLOCK_PLACE = registerWorldGuardFlag(DefaultFlag.BLOCK_PLACE);
    public final Flag<State> USE = registerWorldGuardFlag(DefaultFlag.USE);
    public final Flag<State> INTERACT = registerWorldGuardFlag(DefaultFlag.INTERACT);
    public final Flag<State> DAMAGE_ANIMALS = registerWorldGuardFlag(DefaultFlag.DAMAGE_ANIMALS);
    public final Flag<State> PVP = registerWorldGuardFlag(DefaultFlag.PVP);
    public final Flag<State> SLEEP = registerWorldGuardFlag(DefaultFlag.SLEEP);
    public final Flag<State> TNT = registerWorldGuardFlag(DefaultFlag.TNT);
    public final Flag<State> CHEST_ACCESS = registerWorldGuardFlag(DefaultFlag.CHEST_ACCESS);
    public final Flag<State> PLACE_VEHICLE = registerWorldGuardFlag(DefaultFlag.PLACE_VEHICLE);
    public final Flag<State> DESTROY_VEHICLE = registerWorldGuardFlag(DefaultFlag.DESTROY_VEHICLE);
    public final Flag<State> LIGHTER = registerWorldGuardFlag(DefaultFlag.LIGHTER);
    public final Flag<State> RIDE = registerWorldGuardFlag(DefaultFlag.RIDE);
    public final Flag<State> POTION_SPLASH = registerWorldGuardFlag(DefaultFlag.POTION_SPLASH);
    public final Flag<State> ITEM_PICKUP = registerWorldGuardFlag(DefaultFlag.ITEM_PICKUP);
    public final Flag<State> ITEM_DROP = registerWorldGuardFlag(DefaultFlag.ITEM_DROP);
    public final Flag<State> EXP_DROPS = registerWorldGuardFlag(DefaultFlag.EXP_DROPS);
    public final Flag<State> MOB_DAMAGE = registerWorldGuardFlag(DefaultFlag.MOB_DAMAGE);
    public final Flag<State> MOB_SPAWNING = registerWorldGuardFlag(DefaultFlag.MOB_SPAWNING);
    public final Flag<State> CREEPER_EXPLOSION = registerWorldGuardFlag(DefaultFlag.CREEPER_EXPLOSION);
    public final Flag<State> ENDERDRAGON_BLOCK_DAMAGE = registerWorldGuardFlag(DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE);
    public final Flag<State> GHAST_FIREBALL = registerWorldGuardFlag(DefaultFlag.GHAST_FIREBALL);
    public final Flag<State> OTHER_EXPLOSION = registerWorldGuardFlag(DefaultFlag.OTHER_EXPLOSION);
    public final Flag<State> FIRE_SPREAD = registerWorldGuardFlag(DefaultFlag.FIRE_SPREAD);
    public final Flag<State> LAVA_FIRE = registerWorldGuardFlag(DefaultFlag.LAVA_FIRE);
    public final Flag<State> LIGHTNING = registerWorldGuardFlag(DefaultFlag.LIGHTNING);
    public final Flag<State> WATER_FLOW = registerWorldGuardFlag(DefaultFlag.WATER_FLOW);
    public final Flag<State> LAVA_FLOW = registerWorldGuardFlag(DefaultFlag.LAVA_FLOW);
    public final Flag<State> PISTONS = registerWorldGuardFlag(DefaultFlag.PISTONS);
    public final Flag<State> SNOW_FALL = registerWorldGuardFlag(DefaultFlag.SNOW_FALL);
    public final Flag<State> SNOW_MELT = registerWorldGuardFlag(DefaultFlag.SNOW_MELT);
    public final Flag<State> ICE_FORM = registerWorldGuardFlag(DefaultFlag.ICE_FORM);
    public final Flag<State> ICE_MELT = registerWorldGuardFlag(DefaultFlag.ICE_MELT);
    public final Flag<State> MUSHROOMS = registerWorldGuardFlag(DefaultFlag.MUSHROOMS);
    public final Flag<State> LEAF_DECAY = registerWorldGuardFlag(DefaultFlag.LEAF_DECAY);
    public final Flag<State> GRASS_SPREAD = registerWorldGuardFlag(DefaultFlag.GRASS_SPREAD);
    public final Flag<State> MYCELIUM_SPREAD = registerWorldGuardFlag(DefaultFlag.MYCELIUM_SPREAD);
    public final Flag<State> VINE_GROWTH = registerWorldGuardFlag(DefaultFlag.VINE_GROWTH);
    public final Flag<State> SOIL_DRY = registerWorldGuardFlag(DefaultFlag.SOIL_DRY);
    public final Flag<State> ENDER_BUILD = registerWorldGuardFlag(DefaultFlag.ENDER_BUILD);
    public final Flag<State> INVINCIBILITY = registerWorldGuardFlag(DefaultFlag.INVINCIBILITY);
    public final Flag<State> SEND_CHAT = registerWorldGuardFlag(DefaultFlag.SEND_CHAT);
    public final Flag<State> RECEIVE_CHAT = registerWorldGuardFlag(DefaultFlag.RECEIVE_CHAT);
    public final Flag<State> ENTRY = registerWorldGuardFlag(DefaultFlag.ENTRY);
    public final Flag<State> EXIT = registerWorldGuardFlag(DefaultFlag.EXIT);
    public final Flag<State> ENDERPEARL = registerWorldGuardFlag(DefaultFlag.ENDERPEARL);
    public final Flag<State> ENTITY_PAINTING_DESTROY = registerWorldGuardFlag(DefaultFlag.ENTITY_PAINTING_DESTROY);
    public final Flag<State> ENTITY_ITEM_FRAME_DESTROY = registerWorldGuardFlag(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY);
    public final Flag<State> FALL_DAMAGE = registerWorldGuardFlag(DefaultFlag.FALL_DAMAGE);
    public final Flag<String> DENY_MESSAGE = registerWorldGuardFlag(DefaultFlag.DENY_MESSAGE);
    public final Flag<String> ENTRY_DENY_MESSAGE = registerWorldGuardFlag(DefaultFlag.ENTRY_DENY_MESSAGE);
    public final Flag<String> EXIT_DENY_MESSAGE = registerWorldGuardFlag(DefaultFlag.EXIT_DENY_MESSAGE);
    public final Flag<Boolean> EXIT_OVERRIDE = registerWorldGuardFlag(DefaultFlag.EXIT_OVERRIDE);
    public final Flag<State> EXIT_VIA_TELEPORT = registerWorldGuardFlag(DefaultFlag.EXIT_VIA_TELEPORT);
    public final Flag<String> GREET_MESSAGE = registerWorldGuardFlag(DefaultFlag.GREET_MESSAGE);
    public final Flag<String> FAREWELL_MESSAGE = registerWorldGuardFlag(DefaultFlag.FAREWELL_MESSAGE);
    public final Flag<Boolean> NOTIFY_ENTER = registerWorldGuardFlag(DefaultFlag.NOTIFY_ENTER);
    public final Flag<Boolean> NOTIFY_LEAVE = registerWorldGuardFlag(DefaultFlag.NOTIFY_LEAVE);
    public final Flag<Set<EntityType>> DENY_SPAWN = registerWorldGuardFlag(DefaultFlag.DENY_SPAWN);
    public final Flag<WeatherType> GAME_MODE = registerWorldGuardFlag(DefaultFlag.GAME_MODE);
    public final Flag<String> TIME_LOCK = registerWorldGuardFlag(DefaultFlag.TIME_LOCK);
    public final Flag<GameMode> WEATHER_LOCK = registerWorldGuardFlag(DefaultFlag.WEATHER_LOCK);
    public final Flag<Integer> HEAL_DELAY = registerWorldGuardFlag(DefaultFlag.HEAL_DELAY);
    public final Flag<Integer> HEAL_AMOUNT = registerWorldGuardFlag(DefaultFlag.HEAL_AMOUNT);
    public final Flag<Double> MIN_HEAL = registerWorldGuardFlag(DefaultFlag.MIN_HEAL);
    public final Flag<Double> MAX_HEAL = registerWorldGuardFlag(DefaultFlag.MAX_HEAL);
    public final Flag<Integer> FEED_DELAY = registerWorldGuardFlag(DefaultFlag.FEED_DELAY);
    public final Flag<Integer> FEED_AMOUNT = registerWorldGuardFlag(DefaultFlag.FEED_AMOUNT);
    public final Flag<Integer> MIN_FOOD = registerWorldGuardFlag(DefaultFlag.MIN_FOOD);
    public final Flag<Integer> MAX_FOOD = registerWorldGuardFlag(DefaultFlag.MAX_FOOD);
    public final Flag<Location> TELE_LOC = registerWorldGuardFlag(DefaultFlag.TELE_LOC);
    public final Flag<Location> SPAWN_LOC = registerWorldGuardFlag(DefaultFlag.SPAWN_LOC);
    public final Flag<State> ENABLE_SHOP = registerWorldGuardFlag(DefaultFlag.ENABLE_SHOP);
    public final Flag<Boolean> BUYABLE = registerWorldGuardFlag(DefaultFlag.BUYABLE);
    public final Flag<Double> PRICE = registerWorldGuardFlag(DefaultFlag.PRICE);
    public final Flag<Set<String>> BLOCKED_CMDS = registerWorldGuardFlag(DefaultFlag.BLOCKED_CMDS);
    public final Flag<Set<String>> ALLOWED_CMDS = registerWorldGuardFlag(DefaultFlag.ALLOWED_CMDS);

}