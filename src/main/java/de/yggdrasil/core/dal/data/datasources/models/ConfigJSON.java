package de.yggdrasil.core.dal.data.datasources.models;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * The {@code ConfigJSON} class represents a JSON object for storing configuration values.
 * It provides methods to add, retrieve, and convert values in JSON format.
 * It supposed to be used to handle saving all kinds of configuration values
 */
public class ConfigJSON {

    private final HashMap<String, Object> values = new HashMap<>();

    /**
     * Adds a value to the ConfigJSON object using the specified key.
     *
     * @param key   the key associated with the value
     * @param value the value to be added
     */
    public void add(String key, String value){
        values.put(key, value);
    }

    /**
     * Adds a ConfigJSON object to the values under the specified key.
     *
     * @param key   the key associated with the ConfigJSON object
     * @param value the ConfigJSON object to be added
     */
    public void add(String key, ConfigJSON value){
        if (value == this) return;
        values.put(key, value);
    }

    /**
     * Retrieves the value associated with the specified key from the ConfigJSON object.
     * If the value is a ConfigJSON object, it is returned. If it is a String, it returns null.
     *
     * @param key the key for which the value is retrieved
     * @return the ConfigJSON object associated with the key, or null if the value is not a ConfigJSON object
     */
    public ConfigJSON get(String key){
        if (values.get(key) instanceof ConfigJSON json){
            return json;
        }
        return null;
    }


    /**
     * Retrieves the value associated with the specified key from the ConfigJSON object.
     * If the value is a string, it is directly returned. If the value is a ConfigJSON object, it is converted to a JSON string representation using the toJSON() method before being
     * returned.
     *
     * @param key the key for which the value is retrieved
     * @return the value associated with the key, as a string if it is a string, or as a JSON string representation if it is a ConfigJSON object
     */
    public String getString(String key){
        if (values.get(key) instanceof String value){
            return value;
        }
        return ((ConfigJSON)values.get(key)).toJSON();
    }


    /**
     * Checks if the value stored at the specified key in the {@link ConfigJSON} object is an instance of {@link ConfigJSON}.
     *
     * @param key the key for which the value is checked
     * @return {@code true} if the value at the specified key is an instance of {@link ConfigJSON}, {@code false} otherwise
     */
    public boolean valueIsConfigJSON(String key){
        return values.get(key) instanceof ConfigJSON;
    }

    /**
     * Converts the current object to a JSON string representation.
     * to receive specific values, use the {@code getString()} method instead
     *
     * @return the JSON string representation of the object
     */
    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
