package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;

import java.util.List;

public class AdaptadorUsers extends RecyclerView.Adapter<AdaptadorUsers.MyViewHolderUser> {
    private final Peticion peticion;
    private List<Usuario> mData;
    private LayoutInflater mInflater;
    private Context context;

    final AdaptadorUsers.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public AdaptadorUsers(List<Usuario> mData, Context context, AdaptadorUsers.OnItemClickListener listener, Peticion peticion) {
        this.mData      = mData;
        this.mInflater  = LayoutInflater.from(context);
        this.context    = context;
        this.listener   = listener;
        this.peticion = peticion;
    }

    public void setItemViewNotClickable(){

    }

    @NonNull
    @Override
    public AdaptadorUsers.MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_usuario, null);

        return new AdaptadorUsers.MyViewHolderUser(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsers.MyViewHolderUser holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<Usuario> items){
        mData = items;
    }

    public class MyViewHolderUser extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView iconImage,xat;
        TextView name, titulo;
        OnItemClickListener onClickListener;

        public MyViewHolderUser(@NonNull View itemView, OnItemClickListener onClickListener) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.fotoTipoPeticion);
            name      = itemView.findViewById(R.id.usuarioPeticion);
            titulo    = itemView.findViewById(R.id.tituloPeticion);
            xat       = itemView.findViewById(R.id.enviarMensaje);
            this.onClickListener = onClickListener;
        }

        void bindData(final Usuario item){

            name.setText(item.getApellidos());
            titulo.setText(item.getNombre());
            iconImage.setImageResource(R.drawable.otros);
            itemView.setOnClickListener(this);
            xat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Abrir chat
                }
            });
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }
}
