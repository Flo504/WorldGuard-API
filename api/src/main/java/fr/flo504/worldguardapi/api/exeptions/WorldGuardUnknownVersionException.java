package fr.flo504.worldguardapi.api.exeptions;

public class WorldGuardUnknownVersionException extends WorldGuardAPIException {

    public WorldGuardUnknownVersionException() {
        super();
    }

    public WorldGuardUnknownVersionException(String message) {
        super(message);
    }

    public WorldGuardUnknownVersionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
