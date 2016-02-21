package com.salesianostriana.thinglocapp.thinglocapp.interfaces;

import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.Objeto;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;


/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public interface ObjetosApi {

    /**
     * Obtiene los objetos de un usuario
     * @param user
     * @return
     */
    @GET("/api/objetos")
    Call<Objeto> obtenerObjetos(@Query(value="search") String user);

    @Headers("{Content-Type: multipart/form-data}")
    @Multipart
    @POST("/api/objetos/")
    Call<String> addObject(     @Part("nombre") RequestBody nombre,
                                @Part("recompensa") RequestBody recompensa,
                                @Part("perdido")RequestBody perdido,
                                @Part("foto\"; filename=\"image.png\" ") RequestBody file,
                                @Part("coordenadas")RequestBody coordenadas,
                                @Part("usuario") RequestBody usuario,
                                @Part("categoria") RequestBody categoria);
}
