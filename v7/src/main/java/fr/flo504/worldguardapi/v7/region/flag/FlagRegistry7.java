package fr.flo504.worldguardapi.v7.region.flag;

import fr.flo504.worldguardapi.api.exeptions.FlagRegisterException;
import fr.flo504.worldguardapi.api.region.flag.Flag;
import fr.flo504.worldguardapi.api.region.flag.FlagRegistry;

public class FlagRegistry7 extends FlagRegistry {

    @Override
    public boolean isValidName(String name) {
        return false;
    }

    private void registerFlag(Flag<?, ?> flag){
        if(exist(flag.getName()))
            throw new FlagRegisterException("Attempting to register a flag with the name '"+ flag.getName()+"' but there is already one with this name");
        if(exist(flag))
            throw new FlagRegisterException("Attempting to register a flag ('"+ flag.getName()+"') that is already registered with another name");
        this.flags.put(flag.getName(), flag);
    }

    private <F> Flag<F, ?> registerWorldGuardFlag(com.sk89q.worldguard.protection.flags.Flag<F> flagName) {
        //TODO Switch with all possib flags type to link with dedicated adaptor


        return null;
    }

    @Override
    public <T> Flag<?, T> registerCustomFlag(String name, Class<T> flagType) {
        //TODO Switch with all possib class type to link with dedicated adaptor

        return null;
    }
}
