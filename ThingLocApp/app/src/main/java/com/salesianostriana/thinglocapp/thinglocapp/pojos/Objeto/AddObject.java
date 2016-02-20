package com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class AddObject {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("recompensa")
    @Expose
    private String recompensa;
    @SerializedName("perdido")
    @Expose
    private Boolean perdido;
    @SerializedName("foto")
    @Expose
    private Bitmap foto;
    @SerializedName("coordenadas")
    @Expose
    private String coordenadas;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("categoria")
    @Expose
    private String categoria;

    /**
     * No args constructor for use in serialization
     */
    public AddObject() {
    }

    /**
     * @param nombre
     * @param categoria
     * @param recompensa
     * @param usuario
     * @param perdido
     * @param coordenadas
     * @param foto
     */
    public AddObject(String nombre, String recompensa, Boolean perdido, Bitmap foto, String coordenadas, String usuario, String categoria) {
        this.nombre = nombre;
        this.recompensa = recompensa;
        this.perdido = perdido;
        this.foto = foto;
        this.coordenadas = coordenadas;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    /**
     * @return The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return The recompensa
     */
    public String getRecompensa() {
        return recompensa;
    }

    /**
     * @param recompensa The recompensa
     */
    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }

    /**
     * @return The perdido
     */
    public Boolean getPerdido() {
        return perdido;
    }

    /**
     * @param perdido The perdido
     */
    public void setPerdido(Boolean perdido) {
        this.perdido = perdido;
    }

    /**
     * @return The foto
     */
    public Bitmap getFoto() {
        return foto;
    }

    /**
     * @param foto The foto
     */
    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    /**
     * @return The coordenadas
     */
    public String getCoordenadas() {
        return coordenadas;
    }

    /**
     * @param coordenadas The coordenadas
     */
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * @return The usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario The usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return The categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria The categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}