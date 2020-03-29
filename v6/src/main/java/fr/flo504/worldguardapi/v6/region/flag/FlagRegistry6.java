package fr.flo504.worldguardapi.v6.region.flag;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
import fr.flo504.worldguardapi.v6.region.flag.adaptor.LocationAdaptor;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.RegionGroupAdaptor;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.StateAdaptor;
import fr.flo504.worldguardapi.v6.region.flag.adaptor.VectorAdaptor;
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

    @SuppressWarnings("unchecked")
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


}