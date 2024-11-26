package com.example.aplicacion_samuel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.aplicacion_samuel.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<Jugador> jugadoresFutbol;
    ArrayAdapter<Jugador> adapter;
    JugadorDetailsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        jugadoresFutbol = new ArrayList<>();
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jugadoresFutbol = new ArrayList<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
                    ArrayList<Jugador> jugadores = JugadorAPI.buscar();
                    getActivity().runOnUiThread(() -> {
                        for (Jugador p : jugadores) {
                            jugadoresFutbol.add(p);
                        }
                        adapter.notifyDataSetChanged();
                    });
                });
        adapter = new JugadorAdapter(getContext(), R.layout.jugadores_lista, jugadoresFutbol);
        binding.jugadoresList.setAdapter(adapter);


        binding.jugadoresList.setOnItemClickListener((adapterView, fragment, i, l) -> {
            Jugador jugador = adapter.getItem(i);
            Toast.makeText(getContext(), "CLICK!", Toast.LENGTH_SHORT).show();
            Log.d("XXX", jugador.toString());
            Bundle args = new Bundle();
            args.putSerializable("Jugador", jugador);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "Actualizado");
        }

        if (id == R.id.action_settings) {
            Intent i = new Intent(getActivity(), SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void refresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> jugadores = JugadorAPI.buscar();
            jugadoresFutbol.clear();

            getActivity().runOnUiThread(() -> {
                for (Jugador p : jugadores) {
                    Log.d("XXX", p.toString());

                    jugadoresFutbol.add(p);
                }
                adapter.notifyDataSetChanged();
            });
        });

        binding.jugadoresList.setOnItemClickListener((adapterView, fragment, i, l) -> {
            Jugador jugador = adapter.getItem(i);
            Bundle args = new Bundle();
            args.putSerializable("Jugador", jugador);
            Log.d("XXX", jugador.toString());
        });


    }
}
