package com.salesianostriana.thinglocapp.thinglocapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.thinglocapp.thinglocapp.R;
import com.salesianostriana.thinglocapp.thinglocapp.activities.DetalleObjetoActivity;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.Objeto;
import com.salesianostriana.thinglocapp.thinglocapp.pojos.Objeto.ResultObjeto;
import com.squareup.picasso.Picasso;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class ObjetosAdapter extends RecyclerView.Adapter<ObjetosAdapter.ViewHolder>{

    private Objeto objeto;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgObjeto;
        TextView nombre, recompensa;
        CheckBox cbPerdido;
        View mi_vista;

        public ViewHolder(View v) {
            super(v);

            imgObjeto = (ImageView) v.findViewById(R.id.imageViewObjetoAdd);
            nombre = (TextView) v.findViewById(R.id.txtNombreObj);
            recompensa = (TextView) v.findViewById(R.id.txtRecompensa);
            cbPerdido = (CheckBox) v.findViewById(R.id.checkBoxPerdido);
            mi_vista = v;
        }
    }

    public ObjetosAdapter(Objeto objeto){
        this.objeto = objeto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_objeto, parent, false);

        context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ResultObjeto objActual = objeto.getResults().get(position);
        if(objActual!=null){
            holder.nombre.setText(objActual.getNombre());
            holder.recompensa.setText("Recompensa: "+ String.valueOf(objActual.getRecompensa())+ "€");
            holder.cbPerdido.setChecked(objActual.getPerdido());
            Picasso.with(context).load(objActual.getFoto()).resize(100,100).into(holder.imgObjeto);

        }

        holder.mi_vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DetalleObjetoActivity.class);
                i.putExtra("id_objeto",objActual.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objeto.getResults().size();
    }




}
