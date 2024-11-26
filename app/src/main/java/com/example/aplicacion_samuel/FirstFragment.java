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
    ArrayList<String> jugadoresFutbol;
    private JugadorAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        jugadoresFutbol = new ArrayList<>();
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.jugadores_lista,
                R.id.txtNombreJugadores,
                jugadoresFutbol);
        binding.jugadoresList.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> jugadores = JugadorAPI.buscar();
            getActivity().runOnUiThread(() -> {
                for (Jugador p : jugadores) {
                    jugadoresFutbol.add(p.getName());
                }
                adapter.notifyDataSetChanged();
            });
        });

        binding.jugadoresList.setOnItemClickListener((adapterView, fragment, i, l) -> {
            String jugador = adapter.getItem(i);
            Bundle args = new Bundle();
            args.putSerializable("Jugador", jugador);
            Log.d("XXX", jugador.toString());
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Toast.makeText(getContext(), "Click!", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "Click");
        }

        if (id == R.id.action_settings) {
            Toast.makeText(getContext(), "Click!", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "Click");
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
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String posicion = preferences.getString("posicion", "");
            Toast.makeText(null, "", Toast.LENGTH_SHORT).show();
        });
    }
}
