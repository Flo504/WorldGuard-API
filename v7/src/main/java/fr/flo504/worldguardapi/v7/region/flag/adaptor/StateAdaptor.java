package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldguard.protection.flags.StateFlag;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import fr.flo504.worldguardapi.api.region.flag.type.State;

public class StateAdaptor implements FlagAdaptor<StateFlag.State, State> {

    private final static StateAdaptor instance = new StateAdaptor();

    public static StateAdaptor getInstance() {
        return instance;
    }

    @Override
    public State from(StateFlag.State state) {
        switch (state){
            case ALLOW:
                return State.ALLOW;
            case DENY:
                return State.DENY;
            default:
                return State.NONE;
        }
    }

    @Override
    public StateFlag.State to(State state) {
        switch (state){
            case ALLOW:
                return StateFlag.State.ALLOW;
            case DENY:
                return StateFlag.State.DENY;
            default:
                return null;
        }
    }
}
