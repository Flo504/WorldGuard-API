package fr.flo504.worldguardapi.api.exeptions;

public class WorldGuardAPIException extends RuntimeException {

    public WorldGuardAPIException(){
        super();
    }

    public WorldGuardAPIException(String message){
        super(message);
    }

    public WorldGuardAPIException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public final void throwException(){
        throw this;
    }

}
