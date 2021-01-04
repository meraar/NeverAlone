package com.example.neveralone.Activity.ListaChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;

import java.util.List;

public class ListaAdaptador extends RecyclerView.Adapter<ListaAdaptador.ViewHolder>{
    private List<ElementosDeLista> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListaAdaptador.OnItemClickListener listener;
    private static final int FRIEND_TYPE_PETITION = 1;
    private static final int FRIEND_TYPE_TUTOR = 2;

    public interface OnItemClickListener{
        void onItemClick(ElementosDeLista item);
    }

    public ListaAdaptador(List<ElementosDeLista> itemList, Context context, OnItemClickListener listener){
        this.mInflater= LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ListaAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FRIEND_TYPE_PETITION) {
            View v = mInflater.inflate(R.layout.elementos_lista_contactos, null);
            return new ListaAdaptador.ViewHolder(v);
        }
        View v = mInflater.inflate(R.layout.elementos_lista_contactos_tutor, null);
        return new ListaAdaptador.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAdaptador.ViewHolder holder, final int position) {

        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {return mData.size();}

    public void setItems(List<ElementosDeLista> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, idPeticion;
        ViewHolder(@NonNull View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreUsuario);
        }

        void bindData(final ElementosDeLista item){
            nombre.setText(item.getNombre());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getIdPeticion().equals("Tutor")) {
            return FRIEND_TYPE_TUTOR;
        }
        return FRIEND_TYPE_PETITION;
    }


}
