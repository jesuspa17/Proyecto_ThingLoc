package com.salesianostriana.thinglocapp.thinglocapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.RestAuthApi;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Key;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.RegistroUser;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class RegistroActivity extends AppCompatActivity {

    EditText username,password1,password2,nombre,apellidos,email;
    Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        username = (EditText) findViewById(R.id.editTextUsuarioRegistro);
        password1 = (EditText) findViewById(R.id.editTextPassword1);
        password2 = (EditText) findViewById(R.id.editTextPassword2);
        nombre = (EditText) findViewById(R.id.editTextNombreRegistro);
        apellidos = (EditText) findViewById(R.id.editTextApellidosRegistro);
        registro = (Button) findViewById(R.id.btnRegistrarse);
        email = (EditText) findViewById(R.id.editTextEmailRegistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usr = username.getText().toString();
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();
                String nom = nombre.getText().toString();
                String ape = apellidos.getText().toString();
                String em = email.getText().toString();

                if(usr.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || nom.isEmpty()|| ape.isEmpty() || em.isEmpty()){
                    Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    RegistroUser nuevoUsuario = new RegistroUser();
                    nuevoUsuario.setUsername(usr);
                    nuevoUsuario.setPassword1(pass1);
                    nuevoUsuario.setPassword2(pass2);
                    nuevoUsuario.setEmail(em);
                    nuevoUsuario.setFirstName(nom);
                    nuevoUsuario.setLastName(ape);
                    new RegistroTask().execute(nuevoUsuario);
                }
            }
        });
    }

    private class RegistroTask extends AsyncTask<RegistroUser, Void, String>{

        @Override
        protected String doInBackground(RegistroUser... params) {
            if(params!=null){
                Call<Key> keyCall = Servicio.instanciarServicio(RestAuthApi.class,"").registrarse(params[0]);
                Response<Key> keyResponse = null;

                try {
                    keyResponse = keyCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert keyResponse != null;
                if(keyResponse.code() == 201){
                    return "Registro completado";
                }else{
                    return "El usuario o email ya están en uso, por favor, introduzca alguno diferente." +
                            "También recuerde que la contraseña debe tener mas de 6 caracteres";
                }
            }else{
                return null;
            }

        }

        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            if(msg.equals("Registro completado")){
                Toast.makeText(RegistroActivity.this, msg, Toast.LENGTH_SHORT).show();
                RegistroActivity.this.finish();
            }else{
                Toast.makeText(RegistroActivity.this, msg, Toast.LENGTH_LONG).show();
            }

        }
    }
}
