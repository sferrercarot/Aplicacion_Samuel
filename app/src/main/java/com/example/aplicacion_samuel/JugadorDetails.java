package com.example.aplicacion_samuel;


import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jugador.databinding.FragmentPokemonsDetailsBinding;


public class JugadorDetails extends Fragment {

    public static JugadorDetails newInstance() {
        return new JugadorDetails();
    }
    public JugadorDetails() {
    }

    private JugadorDetailsViewModel mViewModel;
    private FragmentPokemonsDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPokemonsDetailsBinding.inflate(inflater, container, false);
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
        Log.d("JUGADOR",jugador.toString());
        binding.txtPokemonNamesDetails.setText(jugador.getName());
        binding.txtPokemonSpriteDetails.setText(jugador.getPosition());
        binding.txtPokemonSpeciesDetails.setText(jugador.getTeam());
        Glide.with(getContext()).load(jugador.getName()).into(binding.imgPokemonSpriteDetails);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JugadorDetailsViewModel.class);
    }

}