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

public class DatosAcademicosFragment extends Fragment {
    TextView tvEscuelaProcedencia, tvPeriodoIngreso, tvPeriodoActual, tvPeriodosValidados, tvCreditos, tvSituacion;

    public DatosAcademicosFragment() {
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
        return inflater.inflate(R.layout.fragment_datos_academicos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvEscuelaProcedencia = view.findViewById(R.id.tvEscuelaProcedencia);
        tvCreditos = view.findViewById(R.id.tvCreditos);
        tvPeriodoActual = view.findViewById(R.id.tvPeriodoActual);
        tvPeriodoIngreso = view.findViewById(R.id.tvPeriodoIngreso);
        tvSituacion = view.findViewById(R.id.tvSituacion);
        tvPeriodosValidados = view.findViewById(R.id.tvPeriodosValidados);

        Alumno alumno = Alumno.getAlumno();

        tvEscuelaProcedencia.setText(alumno.datosAcademicos.getEscuelaDeProcedencia());
        tvPeriodoIngreso.setText(alumno.datosAcademicos.getPeriodoDeIngreso());
        tvPeriodosValidados.setText(alumno.datosAcademicos.getPeriodosValidados());
        tvPeriodoActual.setText(alumno.datosAcademicos.getPeriodoActual());
        tvCreditos.setText(alumno.datosAcademicos.getCreditosAcumulados());
        tvSituacion.setText(alumno.datosAcademicos.getSituacion());

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