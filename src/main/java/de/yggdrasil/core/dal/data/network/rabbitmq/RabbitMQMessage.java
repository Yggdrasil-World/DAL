package de.yggdrasil.core.dal.data.network.rabbitmq;

/**
 * The RabbitMQMessage class represents a message delivered through RabbitMQ.
 * It contains a key and content as byte array.
 */
public record RabbitMQMessage(String key, byte[] content) { }
