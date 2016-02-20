package com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class Key {

    @SerializedName("key")
    @Expose
    private String key;

    /**
     * No args constructor for use in serialization
     */
    public Key() {
    }

    /**
     * @param key
     */
    public Key(String key) {
        this.key = key;
    }

    /**
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(String key) {
        this.key = key;
    }

}