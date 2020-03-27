package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldguard.protection.flags.StateFlag;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import fr.flo504.worldguardapi.api.region.flag.type.State;

public class StateAdaptor implements FlagAdaptor<State> {

    @Override
    public State from(Object object) {
        final StateFlag.State state = (StateFlag.State) object;
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
