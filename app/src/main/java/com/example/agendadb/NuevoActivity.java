package com.example.agendadb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.agendadb.db.DbContactos;

import java.util.Calendar;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtTelefono;
    TextView txtHora, txtFecha;
    int hour, minuto;
    int day, month, year;
    Button btnGuarda;
    Calendar c;
    DatePickerDialog dpd;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtFecha = findViewById(R.id.txtFecha);
        txtHora = findViewById(R.id.txtHora);
        btnGuarda = findViewById(R.id.btnGuarda);

        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NuevoActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                minuto = minute;

                                c = Calendar.getInstance();

                                c.set(0,0,0, hour, minuto);

                                txtHora.setText(DateFormat.format("hh:mm aa", c));
                            }
                        },12,0,false
                );

                timePickerDialog.updateTime(hour,minuto);
                timePickerDialog.show();
            }
        });
        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(NuevoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFecha.setText(DateFormat.format("dd/mm/yyyy", c));
                    }
                },day,month,year);
                dpd.show();
            }
        });


        btnGuarda.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                long id = dbContactos.insertarContacto(txtNombre.getText().toString(), txtApellido.getText().toString(), txtTelefono.getText().toString(), txtFecha.getText().toString(), txtHora.getText().toString());

                if(id > 0){
                    Toast.makeText(NuevoActivity.this, "Registro Guardado", Toast.LENGTH_LONG).show();
                    limpiar();
                }else{
                    Toast.makeText(NuevoActivity.this, "Registro no guardado", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void limpiar(){
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtFecha.setText("");
        txtHora.setText("");
    }

}