package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;

import org.w3c.dom.Text;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    private List<Peticion> mData;
    private LayoutInflater mInflater;
    private Context context;

    public Adaptador(List<Peticion> mData, Context context) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public Adaptador.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_peticion, null);

        return new Adaptador.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.MyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<Peticion> items){
        mData = items;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView name, titulo, estado;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.fotoTipoPeticion);
            name = itemView.findViewById(R.id.usuarioPeticion);
            titulo = itemView.findViewById(R.id.tituloPeticion);
            estado = itemView.findViewById(R.id.estado);
        }

        void bindData(final Peticion item){

            name.setText(item.getUser());
            titulo.setText(item.getCategoria());
            estado.setText(item.getEstado().toString());

            if(item.getCategoria().equals("Compras")){
                iconImage.setImageResource(R.drawable.compras);
            }else if (item.getCategoria().equals("Asesoramiento")){
                iconImage.setImageResource(R.drawable.asesoriamiento);
            }else if(item.getCategoria().equals("Acompañamiento")){
                iconImage.setImageResource(R.drawable.acompanamiento);
            }else iconImage.setImageResource(R.drawable.otros);
        }
    }
}
