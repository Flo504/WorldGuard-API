package fr.flo504.worldguardapi.api.exeptions;

public class RegionException extends WorldGuardAPIException{

    public RegionException() {
        super();
    }

    public RegionException(String message) {
        super(message);
    }

    public RegionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
