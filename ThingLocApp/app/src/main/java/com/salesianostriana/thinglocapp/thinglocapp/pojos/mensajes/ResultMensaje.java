package com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 20/02/2016.
 */
public class ResultMensaje {
    @SerializedName("usuarioEmisor")
    @Expose
    private String usuarioEmisor;
    @SerializedName("usuarioReceptor")
    @Expose
    private String usuarioReceptor;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("objeto")
    @Expose
    private String objeto;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    /**
     *
     * @return
     * The usuarioEmisor
     */
    public String getUsuarioEmisor() {
        return usuarioEmisor;
    }

    /**
     *
     * @param usuarioEmisor
     * The usuarioEmisor
     */
    public void setUsuarioEmisor(String usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }

    /**
     *
     * @return
     * The usuarioReceptor
     */
    public String getUsuarioReceptor() {
        return usuarioReceptor;
    }

    /**
     *
     * @param usuarioReceptor
     * The usuarioReceptor
     */
    public void setUsuarioReceptor(String usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }

    /**
     *
     * @return
     * The comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     *
     * @param comentario
     * The comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     *
     * @return
     * The objeto
     */
    public String getObjeto() {
        return objeto;
    }

    /**
     *
     * @param objeto
     * The objeto
     */
    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    /**
     *
     * @return
     * The fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     * The fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}