package fr.flo504.worldguardapi.api.region.flag.adaptor;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapAdaptor<K, V> implements FlagAdaptor<Map<K, V>> {

    private final FlagAdaptor<K> keyAdaptor;
    private final FlagAdaptor<V> valueAdaptor;

    public MapAdaptor(FlagAdaptor<K> keyAdaptor, FlagAdaptor<V> valueAdaptor) {
        Objects.requireNonNull(keyAdaptor);
        Objects.requireNonNull(valueAdaptor);
        this.keyAdaptor = keyAdaptor;
        this.valueAdaptor = valueAdaptor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<K, V> from(Object object) {
        Objects.requireNonNull(object);
        final Map<Object, Object> map = (Map<Object, Object>) object;
        return map
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> keyAdaptor.from(entry.getKey()), entry -> valueAdaptor.from(entry.getValue())));
    }

    @Override
    public Object to(Map<K, V> adapt) {
        Objects.requireNonNull(adapt);
        return adapt
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> keyAdaptor.to(entry.getKey()), entry -> valueAdaptor.to(entry.getValue())));
    }

}
