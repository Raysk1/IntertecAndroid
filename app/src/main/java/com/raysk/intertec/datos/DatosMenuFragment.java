package com.raysk.intertec.datos;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.raysk.intertec.alumno.Alumno;
import com.raysk.intertec.R;


public class DatosMenuFragment extends Fragment {
    TextView tvUsername, tvUserNumControl, tvUsernameIcon;
    Button btDatosGenerales, btDatosPersonales, btDatosAcademicos;


    public DatosMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().getSupportFragmentManager().popBackStack();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.userName);
        tvUsernameIcon = view.findViewById(R.id.tvCalificacion);
        tvUserNumControl = view.findViewById(R.id.userNumControl);
        btDatosGenerales = view.findViewById(R.id.btDatosGenerales);
        btDatosPersonales = view.findViewById(R.id.btDatosPersonales);
        btDatosAcademicos = view.findViewById(R.id.btDatosAcademicos);
        Alumno alumno = Alumno.getAlumno();
        NavController navController = Navigation.findNavController(view);


        String nombre = alumno.datosGenerales.getNombre();
        tvUsername.setText(nombre);
        String icon = nombre.charAt(0) + "";
        tvUsernameIcon.setText(icon);
        tvUserNumControl.setText(alumno.getControl());


        btDatosGenerales.setOnClickListener(v -> navController.navigate(R.id.action_datosMenuFragment_to_datosGeneralesFragment));

        btDatosPersonales.setOnClickListener(v -> navController.navigate(R.id.action_datosMenuFragment_to_datosPersonalesFragment));

        btDatosAcademicos.setOnClickListener(v -> navController.navigate(R.id.action_datosMenuFragment_to_datosAcademicosFragment));


    }
}