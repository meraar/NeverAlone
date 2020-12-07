package com.example.neveralone.Papelera;

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

    public TextView getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(TextView txtNombreUsurio) {
        this.txtNombre = txtNombreUsurio;
    }

    private CircleImageView fotoPerfil;
    private TextView txtNombre;
    public ContactoViewHolder(@NonNull View itemView) {
        super(itemView);
        fotoPerfil = itemView.findViewById(R.id.fotoPerfil);
        txtNombre = itemView.findViewById(R.id.nombreUsuario);

    }
}
