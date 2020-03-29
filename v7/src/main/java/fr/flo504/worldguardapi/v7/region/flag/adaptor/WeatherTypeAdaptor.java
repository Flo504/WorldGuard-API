package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldedit.world.weather.WeatherTypes;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import fr.flo504.worldguardapi.api.region.flag.type.WeatherType;

public class WeatherTypeAdaptor implements FlagAdaptor<WeatherType> {

    @Override
    public WeatherType from(Object object) {
        final com.sk89q.worldedit.world.weather.WeatherType weatherType = (com.sk89q.worldedit.world.weather.WeatherType) object;
        if(weatherType.equals(WeatherTypes.RAIN))
            return WeatherType.RAIN;
        else if(weatherType.equals(WeatherTypes.THUNDER_STORM))
            return WeatherType.THUNDER_STORM;
        else
            return WeatherType.CLEAR;

    }

    @Override
    public Object to(WeatherType adapt) {
        switch (adapt){
            case RAIN:
                return WeatherTypes.RAIN;
            case THUNDER_STORM:
                return WeatherTypes.THUNDER_STORM;
            default:
                return WeatherTypes.CLEAR;
        }
    }
}
