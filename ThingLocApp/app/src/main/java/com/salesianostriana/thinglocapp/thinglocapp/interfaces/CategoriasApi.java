package com.salesianostriana.thinglocapp.thinglocapp.interfaces;

import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.Categoria;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.ResultCategoria;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public interface CategoriasApi {
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
}
