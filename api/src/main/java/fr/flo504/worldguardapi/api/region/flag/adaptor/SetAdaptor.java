package fr.flo504.worldguardapi.api.region.flag.adaptor;


import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SetAdaptor<T> implements FlagAdaptor<Set<T>>{

    private final FlagAdaptor<T> adaptor;

    public SetAdaptor(FlagAdaptor<T> adaptor) {
        Objects.requireNonNull(adaptor);
        this.adaptor = adaptor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<T> from(Object object) {
        Objects.requireNonNull(object);
        final Set<Object> set = (Set<Object>) object;
        return set
                .stream()
                .map(adaptor::from)
                .collect(Collectors.toSet());
    }

    @Override
    public Object to(Set<T> adapt) {
        Objects.requireNonNull(adapt);
        return adapt
                .stream()
                .map(adaptor::to)
                .collect(Collectors.toSet());
    }

}
