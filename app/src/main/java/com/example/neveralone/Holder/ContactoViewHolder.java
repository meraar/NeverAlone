package com.example.neveralone.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactoViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(CircleImageView fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public TextView getTxtNombreUsurio() {
        return txtNombreUsurio;
    }

    public void setTxtNombreUsurio(TextView txtNombreUsurio) {
        this.txtNombreUsurio = txtNombreUsurio;
    }

    private CircleImageView fotoPerfil;
    private TextView txtNombreUsurio;
    public ContactoViewHolder(@NonNull View itemView) {
        super(itemView);
        fotoPerfil = itemView.findViewById(R.id.fotoPerfil);
        txtNombreUsurio = itemView.findViewById(R.id.nombreUsuario);

    }
}
