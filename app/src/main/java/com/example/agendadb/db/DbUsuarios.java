package com.example.agendadb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.agendadb.entidades.Contactos;

import java.util.ArrayList;

public class DbUsuarios extends DbHelper{
    Context context;
    public static int idUsuario=0;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Boolean insertarUsuario(String usuario, String password){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("usuario", usuario);
        values.put("password", password);
        values.put("repassword", password);



        long result = db.insert("t_usuarios", null, values);

        if(result == -1){
            return false;
        }else{
            return  true;
        }

    }

    public Boolean checkUsuario(String usuario){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM t_usuarios WHERE usuario = ?", new String[] {usuario});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkusuarioPassword(String usuario, String password){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM t_usuarios WHERE usuario = ? AND password = ?", new String[] {usuario, password });
        if(cursor.moveToFirst()){
            do{
                idUsuario = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
         if(cursor.getCount()>0){
             //idUsuario = cursor.getInt(0);
             //System.out.println(cursor.getInt(0));
             return true;
         }else{
             return false;
         }
    }


}
