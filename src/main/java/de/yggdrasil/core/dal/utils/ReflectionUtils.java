package de.yggdrasil.core.dal.utils;

import de.yggdrasil.core.dal.requests.DALRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getMatchingGenericTypeArgument(Class<?> clazz, Class<?> expectedInterface) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                if (expectedInterface.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    for (Type typeArgument : typeArguments) {
                        if (DALRequest.class.isAssignableFrom(resolveClass(typeArgument))) {
                            return (Class<T>) resolveClass(typeArgument);
                        }
                    }
                }
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
