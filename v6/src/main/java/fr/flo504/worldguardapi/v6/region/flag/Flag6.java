package fr.flo504.worldguardapi.v6.region.flag;

import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

import java.util.Objects;

public class Flag6<F, T> extends Flag<com.sk89q.worldguard.protection.flags.Flag<F>, T> {

    private final FlagAdaptor<F, T> flagAdaptor;
    private final com.sk89q.worldguard.protection.flags.Flag<F> wgFlag;

    public Flag6(String name, FlagAdaptor<F, T> flagAdaptor, com.sk89q.worldguard.protection.flags.Flag<F> wgFlag) {
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
        Flag6<?, ?> flag7 = (Flag6<?, ?>) o;
        return Objects.equals(flagAdaptor, flag7.flagAdaptor) &&
                Objects.equals(wgFlag, flag7.wgFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flagAdaptor, wgFlag);
    }

    @Override
    public String toString() {
        return "Flag6{" +
                "flagAdaptor=" + flagAdaptor +
                ", wgFlag=" + wgFlag +
                '}';
    }
}
