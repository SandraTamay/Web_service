package com.example.agendadb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbFavoritos extends DbHelper {

    Context context;
    public DbFavoritos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /*public long insertarFav(int idUsuario, int idContacto){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario", idUsuario);
            values.put("idContacto", idContacto);

            id = db.insert(TABLE_FAVORITOS, null, values);
            Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            ex.toString();
            Toast.makeText(context, "No Agregado a favoritos", Toast.LENGTH_SHORT).show();
        }
        return  id;
    }*/
    public void insertarFavorito(int idUsuario, int idContacto){
        DbHelper dbHelper = new DbHelper(context.getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idUsuario", idUsuario);
        values.put("idContacto", idContacto);



        long result = db.insert("t_fav", null, values);

        if(result == -1){
            Toast.makeText(context, "No Agregado a favoritos", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean eliminarFav(int id, int idUsuario2){
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_FAVORITOS + " WHERE idContacto = '" + id + "' AND idUsuario = '" + idUsuario2 + "'");
            Toast.makeText(context, "Se elimino de favoritos", Toast.LENGTH_SHORT).show();
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return  correcto;
    }

}
