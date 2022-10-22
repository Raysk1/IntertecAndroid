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

        try {
            tvEscuelaProcedencia.setText(alumno.datosAcademicos.getString("escuelaDeProcedencia"));
            tvPeriodoIngreso.setText(alumno.datosAcademicos.getString("periodoDeIngreso"));
            tvPeriodosValidados.setText(alumno.datosAcademicos.getString("periodosValidados"));
            tvPeriodoActual.setText(alumno.datosAcademicos.getString("periodoActual"));
            tvCreditos.setText(alumno.datosAcademicos.getString("creditosAcumulados"));
            tvSituacion.setText(alumno.datosAcademicos.getString("situacion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}