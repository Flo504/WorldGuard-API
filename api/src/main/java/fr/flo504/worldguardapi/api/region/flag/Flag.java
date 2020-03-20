package fr.flo504.worldguardapi.api.region.flag;

import java.util.Objects;

public abstract class Flag<F, T> {

    private final String name;

    public Flag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flag<?, ?> flag = (Flag<?, ?>) o;
        return Objects.equals(name, flag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "AbstractFlag{" +
                "name='" + name + '\'' +
                '}';
    }

}
