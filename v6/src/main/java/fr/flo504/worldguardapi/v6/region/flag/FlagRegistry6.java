package fr.flo504.worldguardapi.v6.region.flag;

import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.FlagRegistry;

public class FlagRegistry6 extends FlagRegistry {

    @Override
    public boolean isValidName(String name) {
        return true;
    }

    private <F, T> Flag<T> registerWorldGuardFlag(com.sk89q.worldguard.protection.flags.Flag<F> flag) {
        return null;
    }

    @Override
    public <T> Flag<T> registerCustomFlag(String name, Class<T> flagType) {
        return null;
    }


}