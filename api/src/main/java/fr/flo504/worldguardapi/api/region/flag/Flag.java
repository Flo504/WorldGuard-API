package fr.flo504.worldguardapi.api.region.flag;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

import java.util.Objects;

public class Flag<T> {

    private final String name;
    private final FlagAdaptor<T> adaptor;

    public Flag(String name, FlagAdaptor<T> adaptor) {
        this.name = name;
        this.adaptor = adaptor;
    }

    public String getName() {
        return name;
    }

    public FlagAdaptor<T> getAdaptor() {
        return adaptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flag<?> flag = (Flag<?>) o;
        return Objects.equals(name, flag.name) &&
                Objects.equals(adaptor, flag.adaptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, adaptor);
    }

    @Override
    public String toString() {
        return "Flag{" +
                "name='" + name + '\'' +
                ", adaptor=" + adaptor +
                '}';
    }
}
