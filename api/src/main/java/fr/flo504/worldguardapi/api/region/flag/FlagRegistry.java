package fr.flo504.worldguardapi.api.region.flag;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class FlagRegistry {

    protected final Map<String, Flag<?>> flags = new HashMap<>();
    protected final Map<Flag<?>, Object> wgFlags = new HashMap<>();

    public Flag<?> getFlag(String name){
        Objects.requireNonNull(name);
        return flags.get(name);
    }

    protected Object getWorldGuardFlag(Flag<?> flag){
        if(flag == null)return null;
        return wgFlags.get(flag);
    }

    protected <K, V> Set<K> getKeysOfValue(Map<K, V> map, V value){
        return map
                .entrySet()
                .stream()
                .filter((entry)-> entry.getValue().equals(value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public abstract boolean isValidName(String name);

    public boolean exist(String flag){
        return flags.containsKey(flag);
    }

    public boolean exist(Flag<?> flag){
        return !getKeysOfValue(flags, flag).isEmpty();
    }

    protected abstract <T> Flag<T> registerWorldGuardFlag(String flagName, FlagAdaptor<T> adaptor);

    public abstract <T> Flag<T> registerCustomFlag(Flag<T> flag);

}
