package com.salesianostriana.thinglocapp.thinglocapp;

import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.AddObject;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.Objeto;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.Categoria;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.ResultCategoria;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.Mensaje;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Key;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.LoginUser;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Logout;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Me;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.RegistroUser;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.Usuario;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 16/02/2016.
 */
public interface ThingLocApi {

    /**
     * Permite el logueo de un usuario.
     * @param loginUser
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @POST("/rest-auth/login/")
    Call<Key> loguearse (@Body LoginUser loginUser);

    /**
     * Permite registrarse en la aplicación
     * @param registroUser
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @POST("/rest-auth/registration/")
    Call<Key> registrarse(@Body RegistroUser registroUser);


    /**
     * Obtiene los datos de un usuario dado el Token
     * @return
     */
    @GET("/rest-auth/user/")
    Call<Me> obtenerMisDatos();

    /**
     * Cierra la sesión del usuario que esté actualmente logueado.
     */
    @POST("/rest-auth/logout/")
    Call<Logout> cerrarSesion();


    /**
     * Obtiene los objetos de un usuario
     * @param user
     * @return
     */
    @GET("/api/objetos")
    Call<Objeto> obtenerObjetos(@Query(value="search") String user);

    /**
     * Obtiene todas las categorias
     * @return
     */
    @GET("/api/categorias")
    Call<Categoria> obtenerCategorias();


    /**
     * Obtiene una categoría según su id
     * @param id
     * @return
     */
    @GET("/api/categorias/{id}")
    Call<ResultCategoria> obtenerUnaCategoria(@Path("id")String id);

    @GET("/api/mensajes")
    Call<Mensaje> obtenerMensajes(@Query(value="objeto")String id);

    @GET("/api/users/{id}")
    Call<Usuario> obtenerUnUsuario(@Path("id")String id);


    @Headers("{Content-Type: multipart/form-data}")
    @POST("/api/objetos/")
    Call<AddObject> anyadirObjeto(@Body AddObject addObject);


    @Multipart
    @POST("/api/objetos/")
    Call<String> addObject(     @Part("nombre") RequestBody nombre,
                                @Part("recompensa") RequestBody recompensa,
                                @Part("perdido")RequestBody perdido,
                                @Part("foto\"; filename=\"image.png\" ") RequestBody file,
                                @Part("coordenadas")RequestBody coordenadas,
                                @Part("usuario") RequestBody usuario,
                                @Part("categoria") RequestBody categoria);


    @Multipart
    @POST("/api/categorias/")
    Call<String> addCategoria(@Part("categoria") RequestBody categoria);




}
