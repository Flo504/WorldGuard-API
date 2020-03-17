package fr.flo504.worldguardapi.v6.region.flag;

import com.sk89q.worldguard.protection.flags.Flag;
import fr.flo504.worldguardapi.api.region.flag.FlagRegistry;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;

public class FlagRegistry6 extends FlagRegistry {

    public Flag<?> getWorldGuardFlag(fr.flo504.worldguardapi.api.region.flag.Flag<?> flag){
        return (Flag<?>) super.getWorldGuardFlag(flag);
    }

    @Override
    public boolean isValidName(String name) {
        return false;
    }

    @Override
    protected <T> fr.flo504.worldguardapi.api.region.flag.Flag<T> registerWorldGuardFlag(String flagName, FlagAdaptor<T> adaptor) {
        return null;
    }

    @Override
    public <T> fr.flo504.worldguardapi.api.region.flag.Flag<T> registerCustomFlag(fr.flo504.worldguardapi.api.region.flag.Flag<T> flag) {
        return null;
    }
}