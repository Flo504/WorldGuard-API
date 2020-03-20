package fr.flo504.worldguardapi.api.exeptions;

public class FlagRegisterException extends FlagException{

    public FlagRegisterException() {
        super();
    }

    public FlagRegisterException(String message) {
        super(message);
    }

    public FlagRegisterException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
