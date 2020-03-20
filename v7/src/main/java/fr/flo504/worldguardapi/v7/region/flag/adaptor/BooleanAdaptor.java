package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

public class BooleanAdaptor implements FlagAdaptor<Boolean, Boolean> {

    private final static BooleanAdaptor instance = new BooleanAdaptor();

    public static BooleanAdaptor getInstance() {
        return instance;
    }

    @Override
    public Boolean from(Boolean value) {
        return value;
    }

    @Override
    public Boolean to(Boolean value) {
        return value;
    }
}
