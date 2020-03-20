package fr.flo504.worldguardapi.api.exeptions;

public class FlagException extends RegionException {

    public FlagException() {
        super();
    }

    public FlagException(String message) {
        super(message);
    }

    public FlagException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
