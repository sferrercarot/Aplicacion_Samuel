package com.example.aplicacion_samuel;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.aplicacion_samuel.databinding.FragmentDetailsBinding;

public class JugadorDetails extends Fragment {

    public static JugadorDetails newInstance() {
        return new JugadorDetails();
    }
    public JugadorDetails() {
    }

    private FragmentDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            Jugador jugador = (Jugador) args.getSerializable("Jugador");
            if (jugador != null){
                Log.d("XXXDetail", jugador.toString());
                mostrarJugador(jugador);
            }

        }

    }

    private void mostrarJugador(Jugador jugador) {
        Log.d("JUGADOR", jugador.toString());
        binding.txtJugadorNombreDetalles.setText(jugador.getName());
        binding.txtJugadorPosicionDetalles.setText(jugador.getPosition());
        binding.txtJugadorEquipoDetalles.setText(jugador.getTeam());
        binding.txtJugadorEdadDetalles.setText((jugador.getAge()) + " años");
        binding.txtJugadorNacionalidadDetalles.setText(jugador.getNationality());
        binding.txtJugadorValorDetalles.setText(jugador.getMarketValue());
        Glide.with(requireContext()).load(jugador.getImage()).into(binding.imgJugadorDetalles);
    }

}