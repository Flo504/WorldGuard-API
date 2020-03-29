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
            throw new FlagRegisterException("Unsupported type for this method, use registerCustomMapFlag method. For this you must cast the flag registry in (FlagRegistry7)");
        else if(wgFlag instanceof RegionGroupFlag)
            adaptor = (FlagAdaptor<T>) new RegionGroupAdaptor();
        else if(wgFlag instanceof RegistryFlag)
            throw new FlagRegisterException("Registry Flag are not supported yet by the API");
        else if(wgFlag instanceof SetFlag)
            throw new FlagRegisterException("Unsupported type for this method, use registerCustomSetFlag method");
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
        else if(flagType == Registry.class){
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

    public final Flag<State> ENTRY = registerWorldGuardFlag(Flags.ENTRY);

}
