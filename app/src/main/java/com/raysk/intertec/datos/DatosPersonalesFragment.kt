package com.raysk.intertec.datos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raysk.intertec.alumno.Alumno;
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


        tvCiudad.setText(alumno.datosPersonales.getCiudad());
        tvColonia.setText(alumno.datosPersonales.getColonia());
        tvCalle.setText(alumno.datosPersonales.getCalle());
        tvNoCalle.setText(alumno.datosPersonales.getNoCalle());
        tvCp.setText(alumno.datosPersonales.getCp());
        tvTelefono.setText(alumno.datosPersonales.getTelefono());
        tvCorreoPer.setText(alumno.datosPersonales.getCorreoPersonal());
        tvCorreoIns.setText(alumno.datosPersonales.getCorreoInstitucional());
        tvFechaNac.setText(alumno.datosPersonales.getFechaDeNacimiento());

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}