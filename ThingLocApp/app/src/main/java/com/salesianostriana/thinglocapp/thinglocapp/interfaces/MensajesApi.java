package com.salesianostriana.thinglocapp.thinglocapp.interfaces;

import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.Mensaje;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public interface MensajesApi {

    @GET("/api/mensajes")
    Call<Mensaje> obtenerMensajes(@Query(value="objeto")String id);
}
