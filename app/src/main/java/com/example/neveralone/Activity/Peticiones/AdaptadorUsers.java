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
    private List<Usuario> mData;
    private LayoutInflater mInflater;
    private Context context;
    final AdaptadorUsers.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Usuario p);
    }

    public AdaptadorUsers(List<Usuario> mData, Context context, AdaptadorUsers.OnItemClickListener listener) {
        this.mData      = mData;
        this.mInflater  = LayoutInflater.from(context);
        this.context    = context;
        this.listener   = listener;
    }


    @NonNull
    @Override
    public AdaptadorUsers.MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_peticion, null);

        return new AdaptadorUsers.MyViewHolderUser(view);
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

    public class MyViewHolderUser extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView name, titulo, estado;


        public MyViewHolderUser(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.fotoTipoPeticion);
            name      = itemView.findViewById(R.id.usuarioPeticion);
            titulo    = itemView.findViewById(R.id.tituloPeticion);
        }

        void bindData(final Usuario item){

            name.setText(item.getApellidos());
            titulo.setText(item.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            iconImage.setImageResource(R.drawable.otros);

        }
    }
}
