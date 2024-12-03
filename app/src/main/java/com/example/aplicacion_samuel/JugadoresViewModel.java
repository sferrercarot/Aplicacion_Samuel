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

        // Inicializalizamos las variables
        this.app = application;
        this.basedeDatos = BaseDatos.getDatabase(this.getApplication());
        this.jugadorDAO = basedeDatos.getJugadorDao();
    }

    // Obetnemos la lista de jugadores como LiveData
    public LiveData<List<Jugador>> getJugadores() {
        return jugadorDAO.getJugadores();
    }

    // Método para recargar los datos de los jugadores
    public void reload() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }

    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {
        public RefreshDataTask() {}

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                    app.getApplicationContext()
            );

            // Crea una instancia de la API para buscar jugadores
            JugadorAPI api = new JugadorAPI();
            ArrayList<Jugador> result;
            // Llama al método buscar() de la API para obtener los jugadores
            result = api.buscar();

            // Registra los resultados en el log
            Log.d("XXX", result.toString());
            // Elimina los jugadores existentes en la base de datos
            JugadorDAO.deleteJugador();
            // Agrega los nuevos jugadores a la base de datos
            JugadorDAO.addJugadores(result);

            return null;
        }
    }
}