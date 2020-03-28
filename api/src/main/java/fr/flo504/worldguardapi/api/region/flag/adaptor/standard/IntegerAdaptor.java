package fr.flo504.worldguardapi.api.region.flag.adaptor.standard;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

public class IntegerAdaptor implements FlagAdaptor<Integer> {

    @Override
    public Integer from(Object object) {
        return (Integer) object;
    }

    @Override
    public Integer to(Integer adapt) {
        return adapt;
    }
}
