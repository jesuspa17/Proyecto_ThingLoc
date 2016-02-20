package com.salesianostriana.thinglocapp.thinglocapp.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.ResultMensaje;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.Usuario;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Created by Jesus Pallares on 20/02/2016.
 */
public class MensajesAdapter extends ArrayAdapter<ResultMensaje>{

    Context context;
    List<ResultMensaje> lista_mensajes;

    public MensajesAdapter(Context context, List<ResultMensaje> objects) {
        super(context, 0, objects);
        this.context=context;
        this.lista_mensajes = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_mensajes, parent, false);

        final ResultMensaje msgActual = lista_mensajes.get(position);

        String id_use = msgActual.getUsuarioEmisor().substring(msgActual.getUsuarioEmisor().length() - 2, msgActual.getUsuarioEmisor().length() - 1);
        String id_receptor = msgActual.getUsuarioReceptor().substring(msgActual.getUsuarioEmisor().length() - 2, msgActual.getUsuarioEmisor().length() - 1);

        final TextView us = (TextView) v.findViewById(R.id.textViewEmisor);
        final TextView msg = (TextView) v.findViewById(R.id.textViewMensajeRecibido);
        final TextView fec = (TextView) v.findViewById(R.id.textViewFecha);
        final TextView us_rec =(TextView) v.findViewById(R.id.textViewReceptor);

        new GetUnUsuario(){
            @Override
            protected void onPostExecute(Usuario usuario) {
                super.onPostExecute(usuario);
                Log.i("USER_ACTUAL",Preferencias.preferencias.getString("username",null));
                if(usuario.getUsername().equalsIgnoreCase(Preferencias.preferencias.getString("username", null))){
                    us.setText("De: mí");

                }else {
                    us.setText("De: " + usuario.getUsername());
                }

                msg.setText(msgActual.getComentario());
                fec.setText(msgActual.getFecha());

            }
        }.execute(id_use);

        new GetUnUsuario(){
            @Override
            protected void onPostExecute(Usuario usuario) {
                super.onPostExecute(usuario);

                if(usuario.getUsername().equals(Preferencias.preferencias.getString("username", null))){
                    us_rec.setText("Para: mí");

                }else {
                    us_rec.setText("Para: " +  usuario.getUsername());
                }

                msg.setText(msgActual.getComentario());
                fec.setText(msgActual.getFecha());

            }
        }.execute(id_receptor);

        return v;
    }
    private class GetUnUsuario extends AsyncTask<String, Void, Usuario>{
        @Override
        protected Usuario doInBackground(String... params) {
            Call<Usuario> call = Servicio.instanciarServicio(Preferencias.preferencias.getString("token",null)).obtenerUnUsuario(params[0]);
            Response<Usuario> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res.body();
        }

    }


}
