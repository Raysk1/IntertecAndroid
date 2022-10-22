package com.raysk.intertec.datos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raysk.intertec.Alumno;
import com.raysk.intertec.R;

import org.json.JSONException;


public class DatosPersonalesFragment extends Fragment {
    TextView tvCiudad, tvColonia, tvCalle, tvNoCalle, tvCp, tvTelefono, tvCorreoPer, tvCorreoIns, tvFechaNac;

    public DatosPersonalesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_personales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCiudad = view.findViewById(R.id.tvCiudad);
        tvColonia = view.findViewById(R.id.tvColonia);
        tvCalle = view.findViewById(R.id.tvCalle);
        tvNoCalle = view.findViewById(R.id.tvNoCalle);
        tvCp = view.findViewById(R.id.tvCp);
        tvTelefono = view.findViewById(R.id.tvTelefono);
        tvCorreoPer = view.findViewById(R.id.tvCorreoPersonal);
        tvCorreoIns = view.findViewById(R.id.tvCorreoInstitucional);
        tvFechaNac = view.findViewById(R.id.tvFechaNac);

        Alumno alumno = Alumno.getAlumno();

        try {
            tvCiudad.setText(alumno.datosPersonales.getString("ciudad"));
            tvColonia.setText(alumno.datosPersonales.getString("colonia"));
            tvCalle.setText(alumno.datosPersonales.getString("calle"));
            tvNoCalle.setText(alumno.datosPersonales.getString("noCalle"));
            tvCp.setText(alumno.datosPersonales.getString("cp"));
            tvTelefono.setText(alumno.datosPersonales.getString("telefono"));
            tvCorreoPer.setText(alumno.datosPersonales.getString("correoPersonal"));
            tvCorreoIns.setText(alumno.datosPersonales.getString("correoInstitucional"));
            tvFechaNac.setText(alumno.datosPersonales.getString("fechaDeNacimiento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}