package fr.flo504.worldguardapi.api.region.flag.adaptor;

public interface FlagAdaptor<T> {

    T from(Object object);

    Object to(T adapt);

}
