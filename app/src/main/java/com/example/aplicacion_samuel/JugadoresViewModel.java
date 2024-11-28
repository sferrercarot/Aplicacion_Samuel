package com.example.aplicacion_samuel;

import android.app.Application;
import android.content.SharedPreferences;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import org.chromium.base.Log;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;

public class JugadoresViewModel extends AndroidViewModel {
    private final Application app;
    private final BaseDatos basedeDatos;
    private final JugadorDAO jugadorDAO;

    public JugadoresViewModel(Application application) {
        super(application);

        this.app = application;
        this.basedeDatos = BaseDatos.getDatabase(this.getApplication());
        this.jugadorDAO = basedeDatos.getJugadorDao();
    }

    public LiveData<List<Jugador>> getJugadores() {
        return jugadorDAO.getJugadores();
    }

    public void reload() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }


    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {
        public RefreshDataTask() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                    app.getApplicationContext()
            );

            JugadorAPI api = new JugadorAPI();
            ArrayList<Jugador> result;
            result = api.buscar();

            Log.d("XXX", result.toString());
            JugadorDAO.deleteJugador();
            JugadorDAO.addJugadores(result);

            return null;
        }
    }
}