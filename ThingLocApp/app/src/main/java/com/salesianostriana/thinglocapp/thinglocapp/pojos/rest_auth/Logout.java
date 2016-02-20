package com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 20/02/2016.
 */
public class Logout {

    @SerializedName("success")
    @Expose
    private String success;

    /**
     *
     * @return
     * The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

}
