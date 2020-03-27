package fr.flo504.worldguardapi.api.region.flag.adaptor.standard;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

public final class StringAdaptor implements FlagAdaptor<String> {

    @Override
    public String from(Object value) {
        return (String) value;
    }

    @Override
    public String to(String value) {
        return value;
    }
}
