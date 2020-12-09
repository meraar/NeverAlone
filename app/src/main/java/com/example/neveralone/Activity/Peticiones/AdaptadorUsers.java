package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Estado;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptadorUsers extends RecyclerView.Adapter<AdaptadorUsers.MyViewHolderUser> {
    private final String peticion;
    private List<Usuario> mData;
    private LayoutInflater mInflater;
    private Context context;

    final AdaptadorUsers.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Usuario p);
    }

    public AdaptadorUsers(List<Usuario> mData, Context context, AdaptadorUsers.OnItemClickListener listener, String peticion) {
        this.mData      = mData;
        this.mInflater  = LayoutInflater.from(context);
        this.context    = context;
        this.listener   = listener;
        this.peticion   = peticion;
    }


    @NonNull
    @Override
    public AdaptadorUsers.MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_usuario, null);

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

        ImageView iconImage,xat;
        TextView name, titulo, estado;


        public MyViewHolderUser(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.fotoTipoPeticion);
            name      = itemView.findViewById(R.id.usuarioPeticion);
            titulo    = itemView.findViewById(R.id.tituloPeticion);
            xat       = itemView.findViewById(R.id.enviarMensaje);

        }

        void bindData(final Usuario item){

            name.setText(item.getApellidos());
            titulo.setText(item.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("¿Está seguro que quiere borrar la petición?")
                            .setCancelable(false)
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Actualizar estado de la peticion
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Peticiones");

                                    reference.child(peticion).child("Estado").setValue(Estado.CURSO);

                                    reference = FirebaseDatabase.getInstance().getReference().child("User-Peticiones");

                                    reference.child("np2Es3nr6bNZL93gUKYJZAznjZg2").child(peticion).child("Estado").setValue(Estado.CURSO);

                                    //Borrar interacciones
                                    reference = FirebaseDatabase.getInstance().getReference().child("Interacciones");

                                    reference.child(peticion).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.hasChildren()) {
                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                    //el id debe ser item.getUID -> falta implementar la erika
                                                    if (!(ds.getValue().toString().equals("zzQA9zldñ1VSFLMNF2fYrgJvzgi1"))) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }


            });
            iconImage.setImageResource(R.drawable.otros);

            xat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Abrir chat
                }
            });
        }
    }
}
