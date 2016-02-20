package com.salesianostriana.thinglocapp.thinglocapp;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Jesus Pallares on 16/02/2016.
 */
public class Servicio {

    //192.168.65.2
    final public static String URL_BASE = "http:/192.168.65.1:8000/";

    /**
     * Servirá para inicializar el servicio que nos permitirá conectarnos a la API.
     * @return el serivicio creado.
     */
    public static ThingLocApi instanciarServicio(final String token) {

        Interceptor interceptor = new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build();

                return chain.proceed(newRequest);
            }
        };
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ThingLocApi service = retrofit.create(ThingLocApi.class);

        return service;
    }

}

