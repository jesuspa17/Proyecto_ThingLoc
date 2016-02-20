package com.salesianostriana.thinglocapp.thinglocapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.fragments.ObjetosFragment;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Logout;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth.Me;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String token;
    TextView usuario, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializo las preferencias
        Preferencias.iniciarPreferencias(this);
        //Almaceno el token necesario para realizar las consultas necesarias.
        token = Preferencias.preferencias.getString("token",null);

        Log.i("KEY_OBTENIDA", token);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AnyadirObjectActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View cabecera = navigationView.getHeaderView(0);
        usuario = (TextView) cabecera.findViewById(R.id.textViewNavUser);
        email = (TextView) cabecera.findViewById(R.id.textViewNavEmail);

        new ObtenerMisDatosTask().execute(token);

        navigationView.setNavigationItemSelectedListener(this);
        transicionFragment(new ObjetosFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_object) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_objetos) {

        } else if (id == R.id.nav_qr) {

            startActivity(new Intent(MainActivity.this,DecoderActivity.class));

        } else if (id == R.id.nav_cerrar) {new CerrarSesionTask().execute();}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void transicionFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, f).commit();
    }

    //AsyncTask que obtiene los datos de un usuario dado el token.
    private class ObtenerMisDatosTask extends AsyncTask<String, Void, Me>{

        @Override
        protected Me doInBackground(String... params) {
            if(params!=null){
                Call<Me> meCall = Servicio.instanciarServicio(params[0]).obtenerMisDatos();
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
            if(me.getUsername()!=null){
                usuario.setText(me.getUsername());
                Preferencias.editor.putString("username", me.getUsername());
                Preferencias.editor.apply();
            }
            if(me.getEmail()!=null){
                email.setText(me.getEmail());
            }


        }
    }

    private class CerrarSesionTask extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            Call<Logout> call = Servicio.instanciarServicio(token).cerrarSesion();
            Response<Logout> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.code();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 200){
                Preferencias.editor.remove("token");
                Preferencias.editor.apply();
                MainActivity.this.finish();
                Intent i = new Intent(MainActivity.this,EntrarActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

        }
    }

}
