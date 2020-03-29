package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldedit.world.gamemode.GameModes;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import org.bukkit.GameMode;

public class GamemodeAdaptor implements FlagAdaptor<GameMode> {
    @Override
    public GameMode from(Object object) {
        final com.sk89q.worldedit.world.gamemode.GameMode gameMode = (com.sk89q.worldedit.world.gamemode.GameMode)object;
        if(gameMode.equals(GameModes.ADVENTURE))
            return GameMode.ADVENTURE;
        else if(gameMode.equals(GameModes.CREATIVE))
            return GameMode.CREATIVE;
        else if(gameMode.equals(GameModes.SPECTATOR))
            return GameMode.SPECTATOR;
        else
            return GameMode.SURVIVAL;

    }

    @Override
    public Object to(GameMode adapt) {
        switch (adapt){
            case CREATIVE:
                return GameModes.CREATIVE;
            case ADVENTURE:
                return GameModes.ADVENTURE;
            case SPECTATOR:
                return GameModes.SPECTATOR;
            default:
                return GameModes.SURVIVAL;
        }
    }
}
