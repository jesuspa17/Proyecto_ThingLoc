package com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class ResultCategoria {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre_categoria")
    @Expose
    private String nombreCategoria;

    /**
     * No args constructor for use in serialization
     */
    public ResultCategoria() {
    }

    /**
     * @param id
     * @param nombreCategoria
     */
    public ResultCategoria(Integer id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The nombreCategoria
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * @param nombreCategoria The nombre_categoria
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

}