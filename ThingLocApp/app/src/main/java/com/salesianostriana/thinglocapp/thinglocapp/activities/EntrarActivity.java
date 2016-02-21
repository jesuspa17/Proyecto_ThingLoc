package com.salesianostriana.thinglocapp.thinglocapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.RestAuthApi;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Key;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.LoginUser;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class EntrarActivity extends AppCompatActivity {

    Button btnLogin;
    EditText editUsuario, editPassword;
    TextView txtRegistrate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        //Obtengo los elementos de la interfaz gráfica
        btnLogin = (Button) findViewById(R.id.btnLogin);
        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editPassword = (EditText) findViewById(R.id.editPassword);
        txtRegistrate = (TextView) findViewById(R.id.textViewRegistrate);

        //Inicializo preferencias.
        Preferencias.iniciarPreferencias(this);

        //Compruebo que el campo "token" almacenado en las preferencias no esté vacío. Si no lo está
        //se iniciará automaticamente la App ya que significará que el usuario ya se ha logueado.
        if(Preferencias.preferencias.getString("token",null)!=null){
            startActivity(new Intent(EntrarActivity.this, MainActivity.class));
            this.finish();

        }

        //Boton de logeo
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtengo el usuario y la contraseña
                String usObtenido = editUsuario.getText().toString();
                String passObtenida = editPassword.getText().toString();

                //Compruebo que los campos estén rellenos y ejecuto el asyntask de logueo.
                if(!(usObtenido.isEmpty() && passObtenida.isEmpty())){
                    LoginUser loginUser = new LoginUser();
                    loginUser.setUsername(usObtenido);
                    loginUser.setPassword(passObtenida);
                    new LoginTask().execute(loginUser);
                }else{
                    Toast.makeText(EntrarActivity.this, "Rellene los campos vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntrarActivity.this,RegistroActivity.class));
            }
        });
    }

    //AsyncTask que realiza el login de un usuario. Se le debe de pasar el cuerpo del mismo. En este caso se hace a través
    //de la clase LoginUser. Devuelve el token correspondiente al usuario.
    private class LoginTask extends AsyncTask<LoginUser, Void, Key>{

        @Override
        protected Key doInBackground(LoginUser... params) {
            if(params!=null){

                Call<Key> keyCall = Servicio.instanciarServicio(RestAuthApi.class,"").loguearse(params[0]);
                Response<Key> keyResponse = null;
                try {
                    keyResponse = keyCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert keyResponse != null;
                if(keyResponse.code() == 200){
                     return keyResponse.body();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(Key key) {
            super.onPostExecute(key);
            if(key!=null){

                //Guardamos la key obtenida en preferencias para luego usarla dentro de la App
                String key_obtenida = key.getKey();
                Preferencias.editor.putString("token", "Token "+key_obtenida);
                Preferencias.editor.apply();
                Preferencias.editor.commit();

                //Inicio el activity principal una vez logueado.
                Intent i = new Intent(EntrarActivity.this, MainActivity.class);
                startActivity(i);
                EntrarActivity.this.finish();

            }else{
                Toast.makeText(EntrarActivity.this,"Datos incorrectos, pruebe otra vez!",Toast.LENGTH_SHORT).show();
            }

        }
    }


}
