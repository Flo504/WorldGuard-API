package fr.flo504.worldguardapi.api.region.flag.adaptor;

public interface FlagAdaptor<F, T> {

    T from(F object);

    F to(T adapt);

}
