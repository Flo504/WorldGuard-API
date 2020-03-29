package fr.flo504.worldguardapi.api.region.flag.adaptor;

public class StandardAdaptor implements FlagAdaptor<Object> {
    @Override
    public Object from(Object object) {
        return object;
    }

    @Override
    public Object to(Object adapt) {
        return adapt;
    }
}
