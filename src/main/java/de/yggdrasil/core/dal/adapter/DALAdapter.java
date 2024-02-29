package de.yggdrasil.core.dal.adapter;

import java.lang.annotation.*;

/**
 * The DALAdapter annotation is used to mark a class as an adapter for converting data between different formats.
 *
 * Example Usage:
 *
 * // Interface definition
 * @DALAdapter
 * public interface Adapter<T> {
 *
 *     byte[] convertToData(T object);
 *
 *     T createFromData(byte[] data);
 *
 *     default Class<T> getAdapterType() {
 *         return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
 *                 .getActualTypeArguments()[0];
 *     }
 *
 * }
 *
 * // Class implementing Adapter interface
 * @DALAdapter
 * public class MyAdapter implements Adapter<String> {
 *
 *     @Override
 *     public byte[] convertToData(String object) {
 *         // Implementation
 *     }
 *
 *     @Override
 *     public String createFromData(byte[] data) {
 *         // Implementation
 *     }
 *
 * }
 *
 * // Class implementing Adapter interface but not annotated
 * public class NonAnnotatedAdapter implements Adapter<String> {
 *
 *     @Override
 *     public byte[] convertToData(String object) {
 *         // Implementation
 *     }
 *
 *     @Override
 *     public String createFromData(byte[] data) {
 *         // Implementation
 *     }
 *
 * }
 *
 * // Class implementing Adapter interface but annotated with HideFromDefaultClassCollector
 * @DALAdapter
 * @HideFromDefaultCollector
 * public class HiddenAdapter implements Adapter<String> {
 *
 *     @Override
 *     public byte[] convertToData(String object) {
 *         // Implementation
 *     }
 *
 *     @Override
 *     public String createFromData(byte[] data) {
 *         // Implementation
 *     }
 *
 * }
 *
 * // Class using the AdapterCollector to collect adapters
 * public class AdapterCollectorExample implements AdapterCollector {
 *
 *     @Override
 *     public Set<Class<? extends Adapter>> collectAdapters() {
 *         // Collecting all classes annotated with DALAdapter and
 *         // assignable from Adapter
 *         return ClassCollector.collect(Adapter.class, DALAdapter.class);
 *     }
 * }
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DALAdapter { }
