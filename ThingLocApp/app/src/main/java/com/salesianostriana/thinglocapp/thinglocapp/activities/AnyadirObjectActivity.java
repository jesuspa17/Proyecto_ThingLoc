package com.salesianostriana.thinglocapp.thinglocapp.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.salesianostriana.thinglocapp.thinglocapp.Preferencias;
import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.Servicio;
import com.salesianostriana.thinglocapp.thinglocapp.adapters.CategoriasAdapter;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.CategoriasApi;
import com.salesianostriana.thinglocapp.thinglocapp.interfaces.ObjetosApi;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.AddObject;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.Categoria;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.ResultCategoria;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class AnyadirObjectActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView imgObjeto;
    EditText nombre, recompensa;
    Spinner sp_categorias;
    CheckBox perdido;
    Button examinar, guardar;

    int id_categoria;
    String token;
    String ruta_imagen;
    Uri uriData;

    private static int FILE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_object);

        //Obtengo los elementos de la UI
        imgObjeto = (ImageView) findViewById(R.id.imageViewObjetoAdd);
        nombre = (EditText) findViewById(R.id.editTextNombreObjeto);
        recompensa = (EditText) findViewById(R.id.editTextRecompensaAdd);
        sp_categorias = (Spinner) findViewById(R.id.spinnerCategorias);
        perdido = (CheckBox) findViewById(R.id.checkBoxPerdidoAdd);
        examinar = (Button) findViewById(R.id.btn_examinar);
        guardar = (Button) findViewById(R.id.btn_guardar);

        //Token necesario para hacer las peticiones
        token = Preferencias.preferencias.getString("token", null);

        //Boton Examinar; abrirá el ActivityForResult del FilePicker
        examinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnyadirObjectActivity.this, FilePickerActivity.class);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(i, FILE_CODE);
            }
        });

        //Boton Guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recojo los datos del objeto
                String nom = nombre.getText().toString();
                String rec = recompensa.getText().toString();
                Boolean per = perdido.isChecked();
                String cat = url_categoria(String.valueOf(id_categoria));
                String usr = Preferencias.preferencias.getString("url_usuario", null);



                if (nom.isEmpty() || rec.isEmpty() || cat.isEmpty() || ruta_imagen.isEmpty()) {
                    Toast.makeText(AnyadirObjectActivity.this, "Por favor, asegurese de rellenar todos los campos del formulario", Toast.LENGTH_SHORT).show();
                } else {

                     //String nom_imagen = ruta_imagen.split("/")[ruta_imagen.split("/").length - 1];
                    //Aquí iría el trozo de código que permitiría dar de alta un objeto.

                    Log.i("CATEGORIA", "CATEGORIA: " + id_categoria);
                    Log.i("IMAGEN", ruta_imagen);
                    Log.i("PERDIDO",String.valueOf(per));

                    ObjetosApi service = Servicio.instanciarServicio(ObjetosApi.class,token);

                    RequestBody nombre =
                            RequestBody.create(MediaType.parse("multipart/form-data"), nom);
                    RequestBody recompensa =
                            RequestBody.create(MediaType.parse("multipart/form-data"), rec);
                    RequestBody perdido =
                            RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(per));
                    File file = new File(ruta_imagen);
                    RequestBody foto =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    RequestBody coordenadas =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "0,0");

                    RequestBody usuario =
                            RequestBody.create(MediaType.parse("multipart/form-data"), usr);

                    RequestBody categoria =
                            RequestBody.create(MediaType.parse("multipart/form-data"), cat);

                    Call<String> call = service.addObject(nombre,recompensa,perdido,foto,coordenadas,usuario,categoria);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {
                            Toast.makeText(AnyadirObjectActivity.this, "Objeto creado correctamente", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });

                    AddObject nuevo_objeto = new AddObject();
                    nuevo_objeto.setNombre(nom);
                    nuevo_objeto.setRecompensa(rec);
                    nuevo_objeto.setPerdido(per);
                    // nuevo_objeto.setFoto(selectedImage);
                    nuevo_objeto.setCategoria(cat);
                    nuevo_objeto.setUsuario(usr);
                    nuevo_objeto.setCoordenadas("0,0");

                    //new AddObjectTask().execute(nuevo_objeto);

                }
            }

        });

        Log.i("URL_USUARIO", Preferencias.preferencias.getString("url_usuario", null));
        Log.i("URL_CATEGORIA", Preferencias.preferencias.getString("url_categoria", null));

        //Ejecuto el Asyntask que muestra las categorias en el spinner
        new GetCategoriasTask().execute();
        sp_categorias.setOnItemSelectedListener(this);

    }


    /**
     * Este el método que recogerá el archivo que hemos seleccionado mediante el FilePicker.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();
                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();

                        }
                    }

                    //Para otras versiones de Android más antiguas.
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);
                    if (paths != null) {
                        for (String path : paths) {
                            Uri uri = Uri.parse(path);
                        }
                    }
                }

            } else {
                Uri uri = data.getData();
                uriData = uri;
                try {

                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgObjeto.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Log.i("DIRECCION", uri.getPath());
                ruta_imagen = uri.getPath();

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ResultCategoria resultCategoria = (ResultCategoria) parent.getSelectedItem();
        id_categoria = resultCategoria.getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Por aquí voy...!
    public String url_categoria(String num) {
        return Servicio.URL_BASE + "api/categorias/" + num + "/";
    }

    /*private class AddObjectTask extends AsyncTask<AddObject, Void, Integer> {

        @Override
        protected Integer doInBackground(AddObject... params) {
            if (params != null) {

                Call<AddObject> addObjectCall = Servicio.instanciarServicio(token).addObject();
                Response<AddObject> response = null;

                try {
                    response = addObjectCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response.code();

            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            Log.i("CODIGO", "CODIGO: " + aVoid);
            super.onPostExecute(aVoid);
        }
    }*/


    //Obtiene el nombre de todas las categorias y las muestra en un Spinner
    private class GetCategoriasTask extends AsyncTask<Void, Void, Categoria> {

        @Override
        protected Categoria doInBackground(Void... params) {
            Call<Categoria> categoriaCall = Servicio.instanciarServicio(CategoriasApi.class,token).obtenerCategorias();
            Response<Categoria> categoriaResponse = null;

            try {
                categoriaResponse = categoriaCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return categoriaResponse.body();
        }

        @Override
        protected void onPostExecute(Categoria categoria) {
            super.onPostExecute(categoria);
            CategoriasAdapter adp = new CategoriasAdapter(AnyadirObjectActivity.this, R.layout.item_spinner_categorias, categoria.getResults());
            sp_categorias.setAdapter(adp);
            sp_categorias.setSelection(0);
        }
    }
}
