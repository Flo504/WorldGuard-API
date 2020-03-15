package fr.flo504.worldguardapi.api.exeptions;

public class RegionNotFoundException extends RegionException{

    public RegionNotFoundException() {
        super();
    }

    public RegionNotFoundException(String message) {
        super(message);
    }

    public RegionNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
