package com.salesianostriana.thinglocapp.thinglocapp.interfaces;

import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.Mensaje;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.ResultMensaje;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public interface MensajesApi {

    /**
     * Obtiene todos los mensajes
     * @param id
     * @return
     */
    @GET("/api/mensajes/")
    Call<Mensaje> obtenerMensajes(@Query(value="objeto")String id);


    /**
     * Almacena un mensaje nuevo
     * @param msg
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @POST("/api/mensajes/")
    Call<ResultMensaje> enviarMensaje(@Body ResultMensaje msg);
}
