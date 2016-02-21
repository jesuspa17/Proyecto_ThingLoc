package com.salesianostriana.thinglocapp.thinglocapp.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.RestAuthApi;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.ResultMensaje;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.Usuario;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public class MensajesRecyclerAdapter extends RecyclerView.Adapter<MensajesRecyclerAdapter.ViewHolder> {

    private List<ResultMensaje> lista_datos;
    Context contexto;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userReceptor,userEmisor,contenido,fecha;

        public ViewHolder(View v) {
            super(v);

            userEmisor = (TextView) v.findViewById(R.id.textViewEmisor);
            contenido = (TextView) v.findViewById(R.id.textViewMensajeRecibido);
            fecha = (TextView) v.findViewById(R.id.textViewFecha);
            userReceptor =(TextView) v.findViewById(R.id.textViewReceptor);


        }
    }

    public MensajesRecyclerAdapter(List<ResultMensaje> lista_datos) {this.lista_datos = lista_datos;}

    @Override
    public MensajesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_mensajes, viewGroup, false);


        contexto = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ResultMensaje msgActual = lista_datos.get(position);

        String id_use = msgActual.getUsuarioEmisor().substring(msgActual.getUsuarioEmisor().length() - 2, msgActual.getUsuarioEmisor().length() - 1);
        String id_receptor = msgActual.getUsuarioReceptor().substring(msgActual.getUsuarioEmisor().length() - 2, msgActual.getUsuarioEmisor().length() - 1);



        new GetUnUsuario(){
            @Override
            protected void onPostExecute(Usuario usuario) {
                super.onPostExecute(usuario);
                Log.i("USER_ACTUAL", Preferencias.preferencias.getString("username", null));
                if(usuario.getUsername().equalsIgnoreCase(Preferencias.preferencias.getString("username", null))){
                    holder.userEmisor.setText("De: mí");

                }else {
                    holder.userReceptor.setText("De: " + usuario.getUsername());
                }

                holder.contenido.setText(msgActual.getComentario());
                holder.fecha.setText(msgActual.getFecha());

            }
        }.execute(id_use);

        new GetUnUsuario(){
            @Override
            protected void onPostExecute(Usuario usuario) {
                super.onPostExecute(usuario);

                if(usuario.getUsername().equals(Preferencias.preferencias.getString("username", null))){
                    holder.userEmisor.setText("Para: mí");

                }else {
                    holder.userReceptor.setText("Para: " +  usuario.getUsername());
                }

                holder.contenido.setText(msgActual.getComentario());
                holder.fecha.setText(msgActual.getFecha());

            }
        }.execute(id_receptor);



    }


    @Override
    public int getItemCount() {
        return lista_datos.size();
    }

    private class GetUnUsuario extends AsyncTask<String, Void, Usuario> {
        @Override
        protected Usuario doInBackground(String... params) {
            Call<Usuario> call = Servicio.instanciarServicio(RestAuthApi.class, Preferencias.preferencias.getString("token", null)).obtenerUnUsuario(params[0]);
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