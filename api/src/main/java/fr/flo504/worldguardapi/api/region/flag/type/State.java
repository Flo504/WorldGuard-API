package fr.flo504.worldguardapi.api.region.flag.type;

public enum State {
    ALLOW(true),
    DENY(false),
    NONE(null);

    private final Boolean bool;

    State(Boolean bool) {
        this.bool = bool;
    }

    public Boolean getBoolean() {
        return bool;
    }
}
