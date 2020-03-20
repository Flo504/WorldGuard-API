package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

public class StringAdaptor implements FlagAdaptor<String, String> {

    private final static StringAdaptor instance = new StringAdaptor();

    public static StringAdaptor getInstance() {
        return instance;
    }

    @Override
    public String from(String value) {
        return value;
    }

    @Override
    public String to(String value) {
        return value;
    }
}
