package com.example.neveralone.Activity.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorMessage extends RecyclerView.Adapter<HolderMessage> {

    List<Message> listMensaje = new ArrayList<>();
    private Context c;

    public AdaptadorMessage(Context c) {
        this.c = c;
    }

    public void addMessage(Message m) {
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @Override
    public HolderMessage onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_message, parent, false);
        return new HolderMessage(v);

    }

    @Override
    public void onBindViewHolder(HolderMessage holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        holder.getHora().setText(listMensaje.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
