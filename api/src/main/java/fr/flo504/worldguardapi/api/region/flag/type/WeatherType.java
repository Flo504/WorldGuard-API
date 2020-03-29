package fr.flo504.worldguardapi.api.region.flag.type;

public enum WeatherType {

    CLEAR(org.bukkit.WeatherType.CLEAR),
    RAIN(org.bukkit.WeatherType.DOWNFALL),
    THUNDER_STORM(org.bukkit.WeatherType.DOWNFALL);

    private org.bukkit.WeatherType bukkit;

    WeatherType(org.bukkit.WeatherType bukkitWeather){
        this.bukkit = bukkitWeather;
    }

    public org.bukkit.WeatherType getBukkit() {
        return bukkit;
    }

    public static WeatherType fromBukkit(org.bukkit.WeatherType weatherType){
        return weatherType == org.bukkit.WeatherType.DOWNFALL ? RAIN : CLEAR;
    }

}
