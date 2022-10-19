package com.raysk.intertec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {
    private TextView nameIcon;
    private TextView userName;
    private  TextView userNumControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nameIcon = findViewById(R.id.nameIcon);
        userName = findViewById(R.id.userName);
        userNumControl = findViewById(R.id.userNumControl);

       // String al =  getIntent().getExtras().getString("alumno");
        //Gson gson = new Gson();
        Alumno alumno = Alumno.getAlumno();
        try {
            userName.setText(alumno.datosGenerales.getString("nombre"));
            nameIcon.setText(alumno.datosGenerales.getString("nombre").charAt(0) + "");
            userNumControl.setText(alumno.control);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}