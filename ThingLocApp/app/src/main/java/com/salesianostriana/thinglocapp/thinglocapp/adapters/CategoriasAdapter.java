package com.salesianostriana.thinglocapp.thinglocapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria.ResultCategoria;

import java.util.List;

/**
 * Created by Jesus Pallares on 20/02/2016.
 */
public class CategoriasAdapter extends ArrayAdapter<ResultCategoria> {

    Context context;
    List<ResultCategoria> lista_categoria;

    public CategoriasAdapter(Context context,int textViewResourceId,List<ResultCategoria> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.lista_categoria = objects;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.item_spinner_categorias, parent, false);

        ResultCategoria catActual = lista_categoria.get(position);

        TextView nom = (TextView) v.findViewById(R.id.textViewSPCat);
        nom.setText(catActual.getNombreCategoria());
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
