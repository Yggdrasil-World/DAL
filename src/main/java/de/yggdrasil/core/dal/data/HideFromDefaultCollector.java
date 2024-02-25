package de.yggdrasil.core.dal.data;

/**
 * This annotation is used to mark a class or interface that should be hidden from the default collector.
 * Classes or interfaces annotated with this annotation will not be collected by the {@link ClassCollector} when searching for specific annotations.
 */
public @interface HideFromDefaultCollector {
}
