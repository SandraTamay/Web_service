package com.example.agendadb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "agenda.db";
    public static final String TABLE_CONTACTOS = "t_contactos";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_FAVORITOS = "t_fav";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "fecha TEXT NOT NULL," +
                "hora TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "usuario TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "repassword TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_FAVORITOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "idUsuario INTEGER ,"+
                "idContacto INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_CONTACTOS);
        db.execSQL("DROP TABLE " + TABLE_USUARIOS);
        //db.execSQL("DROP TABLE " + TABLE_FAVORITOS);
        onCreate(db);
    }
}
