package com.example.aplicacion_samuel;

// Importaciones necesarias para la clase
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class JugadorAdapter extends ArrayAdapter<Jugador> {

    public JugadorAdapter(@NonNull Context context, int resource, @NonNull List<Jugador> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int id, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtiene el id del jugador
        Jugador jugador = getItem(id);

        // Si la vista convertida es nula, infla una nueva vista
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.jugadores_lista, parent, false);
        }

        // Obtiene el TextView para mostrar el nombre del jugador
        TextView txtNombreJugador = convertView.findViewById(R.id.txtNombreJugadores);
        // Establece el nombre del jugador en el TextView
        txtNombreJugador.setText(jugador.getName());

        // Obtiene el ImageView para mostrar la imagen del jugador
        ImageView imagenJugador = convertView.findViewById(R.id.imgJugadores);
        // Carga la imagen del jugador utilizando Glide
        Glide.with(getContext()).load(jugador.getImage()).into(imagenJugador);

        // Retorna la vista convertida con los datos del jugador
        return convertView;
    }
}