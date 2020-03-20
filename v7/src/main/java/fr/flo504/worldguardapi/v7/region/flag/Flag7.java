package fr.flo504.worldguardapi.v7.region.flag;

import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

import java.util.Objects;

public class Flag7<F, T> extends Flag<com.sk89q.worldguard.protection.flags.Flag<F>, T> {

    private final FlagAdaptor<F, T> flagAdaptor;
    private final com.sk89q.worldguard.protection.flags.Flag<F> wgFlag;

    public Flag7(String name, FlagAdaptor<F, T> flagAdaptor, com.sk89q.worldguard.protection.flags.Flag<F> wgFlag) {
        super(name);
        this.flagAdaptor = flagAdaptor;
        this.wgFlag = wgFlag;
    }

    public FlagAdaptor<F, T> getFlagAdaptor() {
        return flagAdaptor;
    }

    public com.sk89q.worldguard.protection.flags.Flag<F> getWgFlag() {
        return wgFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flag7<?, ?> flag7 = (Flag7<?, ?>) o;
        return Objects.equals(flagAdaptor, flag7.flagAdaptor) &&
                Objects.equals(wgFlag, flag7.wgFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flagAdaptor, wgFlag);
    }

    @Override
    public String toString() {
        return "Flag7{" +
                "flagAdaptor=" + flagAdaptor +
                ", wgFlag=" + wgFlag +
                '}';
    }
}
