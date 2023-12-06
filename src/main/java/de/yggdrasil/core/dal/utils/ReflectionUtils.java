package de.yggdrasil.core.dal.utils;

import de.yggdrasil.core.dal.requests.DALRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getMatchingGenericTypeArgument(Class<?> clazz, Class<?> expectedInterface) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (!(genericInterface instanceof ParameterizedType parameterizedType)) continue;
            if (!expectedInterface.isAssignableFrom((Class<?>) parameterizedType.getRawType())) continue;
            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                if (!DALRequest.class.isAssignableFrom(resolveClass(typeArgument))) continue;
                return (Class<T>) resolveClass(typeArgument);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> resolveClass(Type type) {
        if (type instanceof Class) {
            return (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) type).getRawType();
        }
        return null;
    }

}
