package fr.flo504.worldguardapi.api.region.flag;

import fr.flo504.worldguardapi.api.exeptions.FlagException;
import fr.flo504.worldguardapi.api.exeptions.FlagRegisterException;
import fr.flo504.worldguardapi.api.region.flag.type.RegionGroup;

import java.util.*;
import java.util.stream.Collectors;

public abstract class FlagRegistry {

    private final List<FlagSession<?>> sessions = new ArrayList<>();

    public List<Flag<?>> getFlags(){
        return sessions
                .stream()
                .map(FlagSession::getFlag)
                .collect(Collectors.toList());
    }

    public <T> FlagSession<T> getFlagSession(Flag<T> flag){
        final Optional<FlagSession<T>> session = getSession(flag);
        if(!session.isPresent())
            throw new FlagException("Attempting to access with a flag that is not register");
        return session.get();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<FlagSession<T>> getSession(Flag<T> flag){
        Objects.requireNonNull(flag, "The flag can not be null");
        return sessions
                .stream()
                .filter(session -> session.getFlag().equals(flag))
                .map(flagSession -> (FlagSession<T>) flagSession)
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    public <T> Flag<T> getFlag(String name){
        final Optional<FlagSession<T>> session = sessions
                .stream()
                .filter(flagSession -> flagSession.getFlag().getName().equalsIgnoreCase(name))
                .map(flagSession -> (FlagSession<T>) flagSession)
                .findFirst();
        return session.map(FlagSession::getFlag).orElse(null);
    }

    protected void registerFlag(FlagSession<?> session){
        final Flag<?> flag = session.getFlag();
        if(exist(flag))
            throw new FlagRegisterException("Attempting to register a flag that is already register with another name");
        sessions.add(session);
    }

    public boolean exist(String flag){
        return sessions
                .stream()
                .anyMatch(session -> session.getFlag().getName().equals(flag));
    }

    public boolean exist(Flag<?> flag) {
        return sessions
                .stream()
                .anyMatch(session -> session.getFlag().equals(flag));
    }

    public abstract boolean isValidName(String name);

    public <T> Flag<T> registerCustomFlag(String name, Class<T> flagType){
        return registerCustomFlag(name, flagType, RegionGroup.ALL, null);
    }

    public <T> Flag<T> registerCustomFlag(String name, Class<T> flagType, T defaultValue){
        return registerCustomFlag(name, flagType, RegionGroup.ALL, defaultValue);
    }

    public <T> Flag<T> registerCustomFlag(String name, Class<T> flagType, RegionGroup regionGroup){
        return registerCustomFlag(name, flagType, regionGroup, null);
    }

    public abstract <T> Flag<T> registerCustomFlag(String name, Class<T> flagType, RegionGroup regionGroup, T defaultValue);

    public <T> Flag<Set<T>> registerCustomSetFlag(String name, Flag<T> flagType){
        return registerCustomSetFlag(name, flagType, RegionGroup.ALL);
    }

    public abstract <T> Flag<Set<T>> registerCustomSetFlag(String name, Flag<T> flagType, RegionGroup regionGroup);

}
