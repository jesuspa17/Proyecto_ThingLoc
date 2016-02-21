package com.salesianostriana.thinglocapp.thinglocapp.interfaces;

import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Key;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.LoginUser;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Logout;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Me;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.RegistroUser;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.Users;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.Usuario;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public interface RestAuthApi {

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
     * Obtiene los datos de un usuario dado su id
     * @param id
     * @return
     */
    @GET("/api/users/{id}")
    Call<Usuario> obtenerUnUsuario(@Path("id")String id);

    /**
     * Obtiene todos los usuarios
     * @return
     */
    @GET("/api/users/")
    Call<Users> obtenerTodosUsuarios();
}
