package fr.flo504.worldguardapi.api.region.flag;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

import java.util.Objects;

public class FlagSession<T> {

    private final Flag<T> flag;
    private final Object wgFlag;
    private final FlagAdaptor<T> adaptor;

    public FlagSession(Flag<T> flag, Object wgFlag, FlagAdaptor<T> adaptor) {
        Objects.requireNonNull(flag);
        Objects.requireNonNull(wgFlag);
        Objects.requireNonNull(adaptor);
        this.flag = flag;
        this.wgFlag = wgFlag;
        this.adaptor = adaptor;
    }

    public Flag<T> getFlag() {
        return flag;
    }

    public Object getWgFlag() {
        return wgFlag;
    }

    public FlagAdaptor<T> getAdaptor() {
        return adaptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlagSession<?> that = (FlagSession<?>) o;
        return Objects.equals(flag, that.flag) &&
                Objects.equals(wgFlag, that.wgFlag) &&
                Objects.equals(adaptor, that.adaptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag, wgFlag, adaptor);
    }

    @Override
    public String toString() {
        return "FlagSession{" +
                "flag=" + flag +
                ", wgFlag=" + wgFlag +
                ", adaptor=" + adaptor +
                '}';
    }
}
