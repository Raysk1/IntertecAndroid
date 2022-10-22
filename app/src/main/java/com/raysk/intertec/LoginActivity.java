package com.raysk.intertec;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;

public class LoginActivity extends AppCompatActivity {
    private TextView control;
    private TextView password;
    private LoadingButton login;
    boolean error = false;
    private View snackBarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        control = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        snackBarContainer = findViewById(R.id.snackBar);

        control.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidarCampos();
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidarCampos();
            }
        });

        login.setOnClickListener(v -> {
            if (!ValidarControl()){
                control.requestFocus();
            }else if (!ValidarPassword()){
                password.requestFocus();
            }else {
                login.showLoading();
                new Connection().execute(control.getText().toString(),password.getText().toString());
            }
        });
    }

    private void ValidarCampos() {
        ValidarControl();
        ValidarPassword();
    }
    public  boolean ValidarControl(){
        if (control.getText().toString().isEmpty()) {
            control.setError("Debe llenar el campo");
            return false;
        } else if (control.getText().toString().length() < 8) {
            control.setError("Debe contener 8 numeros");
            return false;
        } else {
            return true;
        }
    }

    public boolean ValidarPassword(){
        if (password.getText().toString().isEmpty()) {
            password.setError("Debe llenar el campo");
            return false;
        } else {
            return true;
        }
    }

    private void MostrarMensaje(String Mensaje){
        Snackbar.make(snackBarContainer,Mensaje,Snackbar.LENGTH_LONG).show();
    }


    @SuppressLint("StaticFieldLeak")
    public class  Connection extends AsyncTask<String, Integer, Alumno> {
        String mensaje = "";
        boolean validado = false;
        Alumno alumno;
        @Override
        protected Alumno doInBackground(String... strings) {

            try {
                String control = strings[0];
                String password = strings[1];
                alumno = Alumno.getAlumno(control,password);
                validado = alumno.ValidarInicioDeSesion();

            } catch (Exception e) {
                alumno = null;
                mensaje = "Error de conexion";
                e.printStackTrace();
                login.setClickable(true);
            }
            return alumno;
        }

        @Override
        protected void onPostExecute(Alumno a) {
            if (a == null) {
                login.hideLoading();
                MostrarMensaje(mensaje);
                return;
            }
            if (validado) {
                Intent intent = new Intent(LoginActivity.this, ButtonNavigation.class);
                //Gson gson = new Gson();
                //String al = gson.toJson(a);
                //intent.putExtra("alumno", al);
                startActivity(intent);
                finish();
            } else {
                mensaje = "Usuario o Contraseña Incorrecto";
                MostrarMensaje(mensaje);
                login.hideLoading();
            }

        }
    }
}
