package fr.flo504.worldguardapi.api.region.flag.adaptor.standard;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

public final class BooleanAdaptor implements FlagAdaptor<Boolean> {

    @Override
    public Boolean from(Object value) {
        return (Boolean) value;
    }

    @Override
    public Boolean to(Boolean value) {
        return value;
    }
}
