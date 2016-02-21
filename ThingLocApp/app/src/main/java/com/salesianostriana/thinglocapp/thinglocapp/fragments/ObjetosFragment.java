package com.salesianostriana.thinglocapp.thinglocapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salesianostriana.thinglocapp.thinglocapp.interfaces.ObjetosApi;
import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.RestAuthApi;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.adapters.ObjetosAdapter;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.Objeto;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Me;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;


public class ObjetosFragment extends Fragment {

    private RecyclerView recyclerView;
    ObjetosAdapter objetosAdapter;
    String token;

    public ObjetosFragment() {
        //
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_objetos,container,false);

        //Inicializo los elementos de la UI
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewObjetos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Obtengo el token almacenado en preferencias
        Preferencias.iniciarPreferencias(getContext());
        token = Preferencias.preferencias.getString("token", null);

        //Obtengo mis datos para obtener mi id y lanzar la consulta que me devuleva los objetos asociados a mí
        new ObtenerMisDatosTask().execute();

        return v;
    }

    private class ObtenerMisDatosTask extends AsyncTask<Void, Void, Me>{

        @Override
        protected Me doInBackground(Void... params) {
            if(params!=null){
                Call<Me> meCall = Servicio.instanciarServicio(RestAuthApi.class,token).obtenerMisDatos();
                Response<Me> response = null;

                try {
                    response = meCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert response != null;
                if(response.code() == 200){
                    return response.body();
                }else{
                    return null;
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Me me) {
            super.onPostExecute(me);
            //Lanzo la consulta que me devuelve la lista con los objetos asociados a mí
            new ObtenerObjetosTask().execute(me.getUsername());
        }
    }

    private class ObtenerObjetosTask extends AsyncTask<String, Void, Objeto>{

        @Override
        protected Objeto doInBackground(String... params) {
            if(params!=null){
                Call<Objeto> objetoCall = Servicio.instanciarServicio(ObjetosApi.class,token).obtenerObjetos(params[0]);
                Response<Objeto> objetoResponse = null;

                try {
                    objetoResponse = objetoCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert objetoResponse != null;
                if(objetoResponse.code()==200){
                    return objetoResponse.body();
                }else{
                    return null;
                }

            }else{
                return null;
            }

        }

        @Override
        protected void onPostExecute(Objeto objeto) {
            super.onPostExecute(objeto);

            if(objeto!=null){
                //Guardo el url del usuario obtenido y el de la categoria para posterior uso en la aplicación
                //y adapto el recycler con la lista obtenida de la consulta
                Preferencias.editor.putString("url_usuario",objeto.getResults().get(0).getUsuario());
                Preferencias.editor.putString("url_categoria",objeto.getResults().get(0).getCategoria());
                Preferencias.editor.apply();
                objetosAdapter = new ObjetosAdapter(objeto);
                recyclerView.setAdapter(objetosAdapter);
            }
        }
    }

}
