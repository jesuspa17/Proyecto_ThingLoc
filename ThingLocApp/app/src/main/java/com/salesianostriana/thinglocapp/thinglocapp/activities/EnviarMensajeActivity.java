package com.salesianostriana.thinglocapp.thinglocapp.activities;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.adapters.UsuariosAdapter;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.MensajesApi;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.RestAuthApi;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.mensajes.ResultMensaje;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.ResultUsuario;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.users.Users;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Call;
import retrofit.Response;

public class EnviarMensajeActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    Spinner sp_usuarios;
    EditText editContenidoMensaje;
    Button enviar;

    String token;
    String id_usuario;
    String url_usuarioEmisor;
    int id_objeto;

    SoundPool soundPool;
    int sonido_enviado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensaje);

        //Incializo los elementos de la UI
        sp_usuarios = (Spinner) findViewById(R.id.spinnerUsuariosMensajes);
        editContenidoMensaje = (EditText) findViewById(R.id.editTextContenidoMensaje);
        enviar = (Button) findViewById(R.id.btn_enviarMensaje);

        //Inicializo los elementos del sonido de enviar mensaje
        AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(aa)
                .build();

        //inicializo el sonido que voy a reproducir al enviar mensaje
        sonido_enviado = soundPool.load(this, R.raw.enviar, 1);

        Bundle extras = getIntent().getExtras();
        //Recojo el extra obtenido
        if(extras!=null){
            id_objeto = extras.getInt("id_objeto");
            Log.i("url_objeto","url_objeto: "+id_objeto);
        }
        //Obtengo el token necesario
        token =  Preferencias.preferencias.getString("token",null);

        //Relleno el spinner de los usuarios
        new ObtenerTodosUsuarios().execute();

        //Eventos asociados al botón enviar.
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String conten = editContenidoMensaje.getText().toString();
                if(conten.isEmpty()){
                    Toast.makeText(EnviarMensajeActivity.this, "Rellene los campos para enviar el mensaje", Toast.LENGTH_SHORT).show();
                }else{
                    url_usuarioEmisor = Preferencias.preferencias.getString("url_usuario", null);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String fec = sdf.format(date);
                    String userEmisor = Servicio.URL_BASE+"api/users/"+url_usuarioEmisor+"/";
                    String obj = Servicio.URL_BASE+"api/objetos/"+id_objeto+"/";
                    String userReceptor = Servicio.URL_BASE+"api/users/"+id_usuario+"/";

                    Log.i("MENSAJE_EMISOR", userEmisor);
                    Log.i("MENSAJE_CONTENIDO", conten);
                    Log.i("MENSAJE_RECEPTOR",userReceptor);
                    Log.i("MENSAJE_OBJETO", obj);
                    Log.i("MENSAJE_FECHA",fec);


                    //Creo un nuevo mensaje con los elementos recogidos anteriormente y ejecuto el asyntask de enviar mensaje
                    ResultMensaje nuevoMensaje = new ResultMensaje();
                    nuevoMensaje.setUsuarioEmisor(userEmisor);
                    nuevoMensaje.setComentario(conten);
                    nuevoMensaje.setFecha(fec);
                    nuevoMensaje.setObjeto(obj);
                    nuevoMensaje.setUsuarioReceptor(userReceptor);

                    new EnviarMensajeTask().execute(nuevoMensaje);
                }
            }
        });

        sp_usuarios.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ResultUsuario usuarioSeleccionado = (ResultUsuario) parent.getSelectedItem();
        id_usuario = String.valueOf(usuarioSeleccionado.getId());
        Log.i("MENSAJE_SELECCIONADO","MENSAJE_SELECCIONADO "+id_usuario);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class EnviarMensajeTask extends AsyncTask<ResultMensaje,Void,Integer>{

        @Override
        protected Integer doInBackground(ResultMensaje... params) {
            Call<ResultMensaje> call = Servicio.instanciarServicio(MensajesApi.class,token).enviarMensaje(params[0]);
            Response<ResultMensaje> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.code();
        }

        @Override
        protected void onPostExecute(Integer code) {
            super.onPostExecute(code);
            Log.i("MENSAJE_ENVIADO", "MENSAJE_ENVIADO " + code);

            //Muestro un toast y reproduzco el sonido
            Toast.makeText(EnviarMensajeActivity.this, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
            soundPool.play(sonido_enviado,1,1,0,0,1);

            //Inicializo el activity de detalles de nuevo
            Intent i = new Intent(EnviarMensajeActivity.this,DetalleObjetoActivity.class);
            i.putExtra("id_objeto",id_objeto);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

            //Finalizo este activity.
            EnviarMensajeActivity.this.finish();
        }
    }

    private class ObtenerTodosUsuarios extends AsyncTask<Void, Void, Users>{

        @Override
        protected Users doInBackground(Void... params) {
            Call<Users> call = Servicio.instanciarServicio(RestAuthApi.class, token).obtenerTodosUsuarios();
            Response<Users> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(Users usuarios) {
            super.onPostExecute(usuarios);
            UsuariosAdapter adp = new UsuariosAdapter(EnviarMensajeActivity.this, R.layout.item_spinner_categorias, usuarios.getResults());
            sp_usuarios.setAdapter(adp);
            sp_usuarios.setSelection(0);
        }
    }
}
