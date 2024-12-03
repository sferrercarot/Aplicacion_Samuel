package com.example.aplicacion_samuel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.aplicacion_samuel.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<Jugador> jugadoresFutbol; // Lista de objetos Jugador que se mostrará en el ListView
    ArrayAdapter<Jugador> adapter;
    JugadoresViewModel viewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false); // Inicialización de binding
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Carga de jugadores desde la API en un hilo separado
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> jugadores = JugadorAPI.buscar(); // Llamada a la API para obtener jugadores
            getActivity().runOnUiThread(() -> {
                for (Jugador p : jugadores) {
                    jugadoresFutbol.add(p); // Se agregan los jugadores obtenidos a la lista
                }
                adapter.notifyDataSetChanged(); // Notifica al adaptador para actualizar la vista
            });
        });

        adapter = new JugadorAdapter(getContext(), R.layout.jugadores_lista, jugadoresFutbol);
        binding.jugadoresList.setAdapter(adapter);

        binding.jugadoresList.setOnItemClickListener((adapterView, fragment, i, l) -> {
            Jugador jugador = adapter.getItem(i); // Obtiene el jugador seleccionado
            Toast.makeText(getContext(), "Cargando...", Toast.LENGTH_SHORT).show(); // Mensaje de carga
            Log.d("XXX", jugador.toString());
            Bundle args = new Bundle(); //
            args.putSerializable("Jugador", jugador); // Agrega el jugador al Bundle
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_fragmentDetails, args);
        });

        // Observa cambios en el ViewModel para actualizar la lista de jugadores
        viewModel = new ViewModelProvider(this).get(JugadoresViewModel.class);
        viewModel.getJugadores().observe(getViewLifecycleOwner(), jugadoresFutbol -> {
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Obtiene el ID del ítem seleccionado en el menú

        if (id == R.id.action_refresh) { // Si se selecciona la opción de actualizar
            Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "Actualizado");
            refresh(); // Llama al método refresh
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Libera el binding
    }

    // Método para refrescar la lista de jugadores
    void refresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> jugadores = JugadorAPI.buscar(); // Obtiene jugadores desde la API
            jugadoresFutbol.clear(); // Limpia la lista de jugadores actual

            getActivity().runOnUiThread(() -> { // Actualiza la lista
                for (Jugador p : jugadores) {
                    Log.d("XXX", p.toString());
                    jugadoresFutbol.add(p); // Agrega los nuevos jugadores a la lista
                }
                adapter.notifyDataSetChanged();
                viewModel.reload();
            });
        });
    }
}
