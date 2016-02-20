package com.salesianostriana.thinglocapp.thinglocapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.adapters.MensajesAdapter;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.Objeto;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.ResultCategoria;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.Mensaje;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class DetalleObjetoActivity extends AppCompatActivity {

    /////////////////////////////////////
    ImageView imgObjeto;
    TextView nombre, categoria, recompensa;
    CheckBox checkBox;
    AppBarLayout appBarLayout;
    ListView listViewMensajes;


    /////////////////////////////////////
    Integer id_objeto;
    String id_categoria;
    String token;
    String nombreInsert,imagenInsert;
    Double recompensaInsert;
    Boolean perdidoInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_objeto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Obtengo los elementos de la UI
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        imgObjeto = (ImageView) findViewById(R.id.imageViewDetalleObjeto);
        nombre = (TextView) findViewById(R.id.textViewNombreDetalle);
        categoria = (TextView) findViewById(R.id.textViewCategoriaDetalle);
        recompensa = (TextView) findViewById(R.id.textViewRecompensaDetalle);
        checkBox = (CheckBox) findViewById(R.id.checkBoxDetalle);
        listViewMensajes = (ListView) findViewById(R.id.listViewMensajes);

        //Inicializo el Bundle por el que obtendré el id del objeto que vendrá desde el MainActivity
        //al pulsar sobre algún elemento de la lista
        Bundle extras = getIntent().getExtras();
        //Recojo el extra obtenido
        if(extras!=null){
            id_objeto = extras.getInt("id_objeto");
            Log.i("ID","ID: "+id_objeto);
        }

        //Obtengo el token necesario para realizar las peticiones
        token = Preferencias.preferencias.getString("token", null);
        Log.i("ID", "ID: " + token);

        //Ejecuto el asyntask que mostrará todos los detalles del objeto en los elementos de la interfaz gráfica.
        new ObtenerUnObjetoTask().execute(id_objeto);
        new GetMensajesTask().execute(String.valueOf(id_objeto));

        //FloatingButton que servirá para mandar mensajes.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Asyntask que realiza la consulta que obtiene los datos de un objeto concreto dado su id.
    private class ObtenerUnObjetoTask extends AsyncTask<Integer, Void, Objeto>{

        @Override
        protected Objeto doInBackground(Integer... params) {
            Call<Objeto> call = Servicio.instanciarServicio(token).obtenerObjetos(String.valueOf(params[0]));
            Response<Objeto> res = null;

            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (res.code() == 200) {
                return res.body();
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Objeto obj) {
            super.onPostExecute(obj);
            String cat ="";

            //Se almacenan los datos obtenidos en las variables correspondientes, para luego, en el asyntask que obtiene el nombre
            //de la categoría (el ultimo que se ejecuta), actualizar los elementos de la interfaz gráfica.
            for(int i= 0;i<obj.getResults().size();i++){
                cat = obj.getResults().get(i).getCategoria();
                nombreInsert = obj.getResults().get(i).getNombre();
                id_categoria = cat.substring(cat.length() - 2, cat.length() - 1);
                recompensaInsert  = obj.getResults().get(i).getRecompensa();
                perdidoInsert = obj.getResults().get(i).getPerdido();
                imagenInsert = obj.getResults().get(i).getFoto();
            }
            //Ejecuto la consulta que obtiene el nombre de la categoría.
            new ObtenerNombreCategoria().execute(id_categoria);
        }
    }

    //AsyncTask que obtiene el nombre de la categoría dado su id.
    private class ObtenerNombreCategoria extends AsyncTask<String, Void, ResultCategoria>{

        @Override
        protected ResultCategoria doInBackground(String... params) {
            Call<ResultCategoria> call = Servicio.instanciarServicio(token).obtenerUnaCategoria(params[0]);
            Response<ResultCategoria> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(ResultCategoria resultCategoria) {
            super.onPostExecute(resultCategoria);

            //Actualizo los elementos de la interfaz gráfica.
            categoria.setText(resultCategoria.getNombreCategoria());
            nombre.setText(nombreInsert);
            recompensa.setText(String.valueOf(recompensaInsert)+ "€");
            checkBox.setChecked(perdidoInsert);
            Picasso.with(DetalleObjetoActivity.this).load(imagenInsert).fit().into(imgObjeto);

        }
    }

    private class GetMensajesTask extends AsyncTask<String,Void,Mensaje>{

        @Override
        protected Mensaje doInBackground(String... params) {
            Call<Mensaje> call = Servicio.instanciarServicio(token).obtenerMensajes(params[0]);
            Response<Mensaje> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(Mensaje mensaje) {
            super.onPostExecute(mensaje);
            listViewMensajes.setAdapter(new MensajesAdapter(DetalleObjetoActivity.this,mensaje.getResults()));
        }
    }
}
