package com.example.agendadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendadb.db.DbHelper;
import com.example.agendadb.db.DbUsuarios;
import com.example.agendadb.entidades.Contactos;
import com.example.agendadb.entidades.Usuarios;

public class LoginActivity extends AppCompatActivity {

    EditText txtusuario, txtpassword, txtrepassword;
    Button btnregister;
    TextView registrar, agenda;
    Usuarios usuario;
    //DbHelper db;

    DbUsuarios dbUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtusuario = findViewById(R.id.usuario);
        txtpassword = findViewById(R.id.password);
        txtrepassword = findViewById(R.id.repassword);
        registrar = findViewById(R.id.registrar);
        btnregister = findViewById(R.id.register);
        agenda = findViewById(R.id.agenda);
        txtrepassword.setVisibility(View.GONE);
        btnregister.setText("Iniciar sesion");

        dbUsuarios = new DbUsuarios(LoginActivity.this);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(agenda.getText().toString().equals("Registrar")){
                    agenda.setText("Agenda");
                    txtrepassword.setVisibility(View.GONE);
                    btnregister.setText("Iniciar sesion");
                    registrar.setText("Forgot your login details? Registarse");

                }else{
                    agenda.setText("Registrar");
                    txtrepassword.setVisibility(View.VISIBLE);
                    btnregister.setText("Registrar");
                    registrar.setText("Iniciar sesión");

                }

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agenda.getText().toString().equals("Registrar")){
                    registrarUsuario();
                }else{
                    login();
                }

            }
        });

    }
    public void registrarUsuario(){
        String user = txtusuario.getText().toString();
        String pass = txtpassword.getText().toString();
        String repass = txtrepassword.getText().toString();

        if(user.equals("") || pass.equals("") || repass.equals("")){
            Toast.makeText(LoginActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            if(pass.equals(repass)){
                Boolean usercheckResult = dbUsuarios.checkUsuario(user);
                if(usercheckResult == false){
                    Boolean regResult = dbUsuarios.insertarUsuario(user,pass);
                    if(regResult == true){
                        Toast.makeText(LoginActivity.this, "Registration Succeful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "User already Exists", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(LoginActivity.this, "Password not Match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(){
        String user = txtusuario.getText().toString();
        String pass = txtpassword.getText().toString();

        if(user.equals("") || pass.equals("")){
            Toast.makeText(LoginActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            Boolean result = dbUsuarios.checkusuarioPassword(user, pass);
            if(result){
                Toast.makeText(this, "Encontrado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);

                startActivity(intent);
            }else{
                Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();

            }
        }
    }


}