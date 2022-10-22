package com.raysk.intertec.datos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lb.auto_fit_textview.AutoResizeTextView;
import com.raysk.intertec.Alumno;
import com.raysk.intertec.R;

import org.json.JSONException;


public class DatosGeneralesFragment extends Fragment {

    TextView tvNombre,tvNoControl,tvCurp,tvCarrera,tvEspecialidad,tvModalidad,tvPlan;
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
        View v = inflater.inflate(R.layout.fragment_datos_generales, container, false);
        return v;
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
        try {
            tvNombre.setText(alumno.datosGenerales.getString("nombre"));
            tvNoControl.setText(alumno.control);
            tvCurp.setText(alumno.datosGenerales.getString("curp"));
            tvCarrera.setText(alumno.datosGenerales.getString("carrera"));
            tvEspecialidad.setText(alumno.datosGenerales.getString("especialidad"));
            tvModalidad.setText("matutina");
            tvPlan.setText(alumno.datosGenerales.getString("planDeEstudios"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}