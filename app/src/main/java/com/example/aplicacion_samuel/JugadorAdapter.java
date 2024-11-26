package com.example.aplicacion_samuel;

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

    public JugadorAdapter(@NonNull Context context, @NonNull List<Jugador> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Jugador jugador = getItem(position);
        LayoutInflater inflater;
        if (convertView == null) {
            inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.jugadores_lista, parent, false);
        }
        TextView txtNombreJugador = convertView.findViewById(R.id.txtNombreJugadores);
        txtNombreJugador.setText(jugador.getName());
        ImageView imagenJugador = convertView.findViewById(R.id.imgJugadores);
        Glide.with(getContext()).load(
                jugador.getImage())
                .into(imagenJugador);

        return convertView;
    }
}

