package com.example.agendadb.adaptadores;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendadb.EditarActivity;
import com.example.agendadb.MainActivity;
import com.example.agendadb.R;
import com.example.agendadb.VerActivity;
import com.example.agendadb.db.DbContactos;
import com.example.agendadb.db.DbFavoritos;
import com.example.agendadb.db.DbHelper;
import com.example.agendadb.db.DbUsuarios;
import com.example.agendadb.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<Contactos> listaContactos;
    int id = 0;
    DbFavoritos dbFavoritos;
    Context context;

    public ListaContactosAdapter(ArrayList<Contactos> listaContactos, Context context){
        this.listaContactos = listaContactos;
        this.context = context;
    }
    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contactos contactos = listaContactos.get(position);
        holder.viewNombre.setText(listaContactos.get(position).getNombre() + " " +listaContactos.get(position).getApellido());
        //holder.viewApellido.setText(listaContactos.get(position).getApellido());
        holder.viewTelefono.setText(listaContactos.get(position).getTelefono());
        holder.viewFecha.setText(listaContactos.get(position).getFecha());
        holder.viewHora.setText(listaContactos.get(position).getHora());
        DbHelper dbHelper = new DbHelper(context.getApplicationContext());

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM t_fav WHERE idUsuario = ? AND idContacto = ? ", new String[] {String.valueOf(DbUsuarios.idUsuario),String.valueOf(contactos.getId())});

        if(cursor.moveToFirst()){
            do{
                holder.fav.setImageResource(R.drawable.ic_favorite);
                holder.favorito = true;
            }while(cursor.moveToNext());

        }else{
            holder.fav.setImageResource(R.drawable.ic_favorite2);
            holder.favorito = false;
        }
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.favorito){
                    holder.fav.setImageResource(R.drawable.ic_favorite2);
                    holder.favorito = false;
                    dbFavoritos = new DbFavoritos(context.getApplicationContext());
                    dbFavoritos.eliminarFav(contactos.getId(),DbUsuarios.idUsuario);


                }else{
                    dbFavoritos = new DbFavoritos(context.getApplicationContext());

                    dbFavoritos.insertarFavorito(DbUsuarios.idUsuario,contactos.getId());

                    System.out.println(contactos.getId());
                    System.out.println(DbUsuarios.idUsuario);
                    holder.fav.setImageResource(R.drawable.ic_favorite);
                    holder.favorito = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
       return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewApellido, viewTelefono, viewFecha, viewHora;
        FloatingActionButton favEditar;
        FloatingActionButton favEliminar;
        ImageView fav;
        Boolean favorito = true;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewFecha = itemView.findViewById(R.id.viewFecha);
            viewHora = itemView.findViewById(R.id.viewHora);

            favEditar = itemView.findViewById(R.id.fabEditar);
            favEliminar = itemView.findViewById(R.id.fabEliminar);
            fav = itemView.findViewById(R.id.btnfav);

            favEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EditarActivity.class);
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
            favEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbContactos dbContactos = new DbContactos(v.getContext());
                    dbContactos.eliminarContacto(listaContactos.get(getAdapterPosition()).getId());
                    dbFavoritos = new DbFavoritos(v.getContext());
                    dbFavoritos.eliminarFav(listaContactos.get(getAdapterPosition()).getId(),DbUsuarios.idUsuario);

                    listaContactos.remove(getAdapterPosition());
                    notifyDataSetChanged();

                }
            });
        }
    }
}
