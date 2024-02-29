package de.yggdrasil.core.dal.utils;

import java.lang.reflect.ParameterizedType;

public class GenericTypeUtils {

    public static Class<?> getParameterTypeClass(Class c){
        if (c.getGenericInterfaces().length == 0
                || !(c.getGenericInterfaces()[0] instanceof ParameterizedType type)) return null;
        if (type.getActualTypeArguments().length == 0) return null;
        return (Class<?>) type.getActualTypeArguments()[0];
    }

}
