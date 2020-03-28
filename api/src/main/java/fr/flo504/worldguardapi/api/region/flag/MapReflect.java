package fr.flo504.worldguardapi.api.region.flag;

import fr.flo504.worldguardapi.api.exeptions.FlagException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public final class MapReflect {

    private final static Method REMOVE;
    private final static Method GET;
    private final static Method CONTAINS;

    static{
        try {
            REMOVE = Map.class.getDeclaredMethod("remove", Object.class);
        } catch (NoSuchMethodException e) {
            throw new FlagException("Can not register REMOVE method", e);
        }
        try {
            GET = Map.class.getDeclaredMethod("get", Object.class);
        } catch (NoSuchMethodException e) {
            throw new FlagException("Can not register GET method", e);
        }
        try {
            CONTAINS = Map.class.getDeclaredMethod("containsKey", Object.class);
        } catch (NoSuchMethodException e) {
            throw new FlagException("Can not register CONTAINS method", e);
        }
    }

    private static Object invokeSilent(Method method, String errorMessage, Object instance, Object... arg){
        try {
            return method.invoke(instance, arg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new FlagException(errorMessage, e);
        }
    }

    public static Object get(Map<?, ?> flags, Object flag){
        return invokeSilent(GET, "Can not get flag", flags, flag);
    }

    public static void remove(Map<?, ?> flags, Object flag){
        invokeSilent(REMOVE, "Can not remove flag", flags, flag);
    }

    public static boolean contains(Map<?, ?> flags, Object flag){
        return (boolean) invokeSilent(CONTAINS, "Can not check flag", flags, flag);
    }

}
