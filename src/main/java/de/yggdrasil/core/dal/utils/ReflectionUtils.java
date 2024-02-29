package de.yggdrasil.core.dal.utils;

import de.yggdrasil.core.dal.requests.DALRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Utility class for reflection operations.
 */
public class ReflectionUtils {

    /**
     * Retrieves the matching generic type argument from a class that implements a specific interface.
     *
     * @param clazz              The class to inspect.
     * @param expectedInterface  The expected interface.
     * @param <T>                The type of the matching generic type argument.
     * @return The matching generic type argument, or null if not found.
     */
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

    /**
     * Resolves the class from a given type.
     *
     * @param type The type to be resolved.
     * @param <T> The type of the resolved class.
     * @return The resolved class, or null if not found.
     */
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
