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


public class DatosGeneralesFragment extends Fragment {

    TextView tvNombre, tvNoControl, tvCurp, tvCarrera, tvEspecialidad, tvModalidad, tvPlan;

    public DatosGeneralesFragment() {
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
        return inflater.inflate(R.layout.fragment_datos_generales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvNoControl = view.findViewById(R.id.tvNoControl);
        tvCurp = view.findViewById(R.id.tvCurp);
        tvCarrera = view.findViewById(R.id.tvCarrera);
        tvEspecialidad = view.findViewById(R.id.tvEspecialidad);
        tvModalidad = view.findViewById(R.id.tvModalidad);
        tvPlan = view.findViewById(R.id.tvPlan);

        Alumno alumno = Alumno.getAlumno();

        tvNombre.setText(alumno.datosGenerales.getNombre());
        tvNoControl.setText(alumno.getControl());
        tvCurp.setText(alumno.datosGenerales.getCurp());
        tvCarrera.setText(alumno.datosGenerales.getCarrera());
        tvEspecialidad.setText(alumno.datosGenerales.getEspecialidad());
        tvModalidad.setText("matutina");
        tvPlan.setText(alumno.datosGenerales.getPlanDeEstudios());


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }
}