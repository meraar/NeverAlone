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
    final AdaptadorUsers.OnItemClickListenerBorrar listenerB;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public interface OnItemClickListenerBorrar {
        void onItemClickborrar(int adapterPosition);
    }

    public AdaptadorUsers(List<Usuario> mData, Context context, AdaptadorUsers.OnItemClickListenerBorrar listenerB,AdaptadorUsers.OnItemClickListener listener, Peticion peticion) {
        this.mData      = mData;
        this.mInflater  = LayoutInflater.from(context);
        this.context    = context;
        this.listener   = listener;
        this.peticion   = peticion;
        this.listenerB  = listenerB;
    }

    @NonNull
    @Override
    public AdaptadorUsers.MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_usuario, null);

        return new AdaptadorUsers.MyViewHolderUser(view, listener, listenerB);
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

        ImageView iconImage, borrar;
        TextView name, titulo;
        OnItemClickListener onClickListener;
        OnItemClickListenerBorrar onClickListenerB;

        public MyViewHolderUser(@NonNull View itemView, OnItemClickListener onClickListener, OnItemClickListenerBorrar listenerB) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.fotoTipoPeticion);
            name      = itemView.findViewById(R.id.usuarioPeticion);
            titulo    = itemView.findViewById(R.id.tituloPeticion);
            borrar    = itemView.findViewById(R.id.enviarMensaje);
            this.onClickListener = onClickListener;
            onClickListenerB     = listenerB;
        }

        void bindData(final Usuario item){

            name.setText(item.getApellidos());
            titulo.setText(item.getNombre());
            iconImage.setImageResource(R.drawable.user);
            itemView.setOnClickListener(this);
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListenerB.onItemClickborrar(getAdapterPosition());
                }
            });
        }



        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }
}
