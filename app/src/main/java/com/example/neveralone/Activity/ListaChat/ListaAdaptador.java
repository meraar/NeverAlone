package com.example.neveralone.Activity.ListaChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;

import java.util.List;

public class ListaAdaptador extends RecyclerView.Adapter<ListaAdaptador.ViewHolder>{
    private List<ElementosDeLista> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListaAdaptador.OnItemClickListener listener;
    public ListaAdaptador(List<ElementosDeLista> itemList, Context context, OnItemClickListener listener){
        this.mInflater= LayoutInflater.from(context);
        this.context = context;
        mData = itemList;
        this.listener = listener;
    }
    @Override
    public int getItemCount() {return mData.size();}

    @Override
    public ListaAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.elementos_lista_contactos, parent, false);
        return new ListaAdaptador.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListaAdaptador.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ElementosDeLista> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre,idPeticion;
        ViewHolder(View itemView){
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
    public interface OnItemClickListener{
        void onItemClick(ElementosDeLista item);
    }
}
