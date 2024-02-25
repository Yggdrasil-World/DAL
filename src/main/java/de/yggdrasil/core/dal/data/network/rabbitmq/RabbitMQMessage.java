package de.yggdrasil.core.dal.data.network.rabbitmq;

/**
 * The RabbitMQMessage class represents a message in RabbitMQ.
 * It contains a key and content as byte array.
 */
public record RabbitMQMessage(String key, byte[] content) { }
