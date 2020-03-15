package fr.flo504.worldguardapi.api.exeptions;

public class RegionAlreadyExistException extends RegionException{

    public RegionAlreadyExistException() {
        super();
    }

    public RegionAlreadyExistException(String message) {
        super(message);
    }

    public RegionAlreadyExistException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
