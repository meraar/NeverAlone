package com.example.neveralone.Activity.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdaptadorMessage extends RecyclerView.Adapter<HolderMessage> {

    List<MessageRecibir> listMensaje = new ArrayList<>();
    private Context c;

    public AdaptadorMessage(Context c) {
        this.c = c;
    }

    public void addMessage(MessageRecibir m) {
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @Override
    public HolderMessage onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_message_recibir, parent, false);
        return new HolderMessage(v);
    }

    @Override
    public void onBindViewHolder(HolderMessage holder, int position) {
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());

        Long codigoHora = listMensaje.get(position).getHora();
        Date data = new Date(codigoHora);
        SimpleDateFormat formatoData = new SimpleDateFormat("HH:mm");
        holder.getHora().setText(formatoData.format(data));
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
