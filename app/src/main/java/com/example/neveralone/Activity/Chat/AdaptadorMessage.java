package com.example.neveralone.Activity.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdaptadorMessage extends RecyclerView.Adapter<HolderMessage> {

    List<MessageRecibir> listMensaje = new ArrayList<>();
    public static final int MESSAGE_TYPE_ENVIAR = 1;
    public static final int MESSAGE_TYPE_RECIBIR = 2;
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
        System.out.println("El viewType es: " + viewType);
        if (viewType == MESSAGE_TYPE_ENVIAR) {
            View v = LayoutInflater.from(c).inflate(R.layout.chat_view_right, parent, false);
            return new HolderMessage(v);
        } else if (viewType == MESSAGE_TYPE_RECIBIR) {
            View v = LayoutInflater.from(c).inflate(R.layout.chat_view_left, parent, false);
            return new HolderMessage(v);
        }
        View v = LayoutInflater.from(c).inflate(R.layout.chat_view_left, parent, false);
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

    @Override
    public int getItemViewType(int position) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (listMensaje.get(position).getUserID().equals(userID)) {
            return MESSAGE_TYPE_ENVIAR;
        }
        return MESSAGE_TYPE_RECIBIR;
    }
}
