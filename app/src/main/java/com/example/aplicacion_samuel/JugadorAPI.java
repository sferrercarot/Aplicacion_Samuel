package com.example.aplicacion_samuel;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class JugadorAPI {
    private final String BASE_URL = "https://cmesgiwjcpziurvoeltr.supabase.co/rest/v1/Jugadores_futbol?apikey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNtZXNnaXdqY3B6aXVydm9lbHRyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE2NjE5MjMsImV4cCI6MjA0NzIzNzkyM30.RFFz-C9ihA-spGEBux0-l4BJu2ZCA_3bh5kNkOj-6tk";
    private final String API_KEY = "<api-key>";

    String getJugadorsMesCaros(String marketValue) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("jugadores")
                .appendQueryParameter("marketValue", marketValue)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        String url = builtUri.toString();

        return doCall(url);
    }

    @OptIn(markerClass = UnstableApi.class)
    public static ArrayList<Jugador> buscar() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        try {
            String response = HttpUtils.get("https://cmesgiwjcpziurvoeltr.supabase.co/rest/v1/Jugadores_futbol?apikey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNtZXNnaXdqY3B6aXVydm9lbHRyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE2NjE5MjMsImV4cCI6MjA0NzIzNzkyM30.RFFz-C9ihA-spGEBux0-l4BJu2ZCA_3bh5kNkOj-6tk");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jugadorObj = jsonArray.getJSONObject(i);
                int id = jugadorObj.getInt("id");
                String name = jugadorObj.getString("name");
                String team = jugadorObj.getString("team");
                int age = jugadorObj.getInt("age");
                String nationality = jugadorObj.getString("nationality");
                String marketValue = jugadorObj.getString("market value");
                String position = jugadorObj.getString("position");
                String image = jugadorObj.getString("image");


                String jugaforDetailsResponse = HttpUtils.get("https://cmesgiwjcpziurvoeltr.supabase.co/rest/v1/Jugadores_futbol?apikey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNtZXNnaXdqY3B6aXVydm9lbHRyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE2NjE5MjMsImV4cCI6MjA0NzIzNzkyM30.RFFz-C9ihA-spGEBux0-l4BJu2ZCA_3bh5kNkOj-6tk");
                JSONArray jugadorDetalils = new JSONArray(jugaforDetailsResponse);

                Log.d("DEBUG", "JSON de " + name + ": " + jugadorDetalils);

                Jugador jugador = new Jugador(id, name, team, age, nationality, marketValue, position, image);
                jugadores.add(jugador);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jugadores;
    }

    private String doCall(String url) {
        try {
            String JsonResponse = HttpUtils.get(url);
            return JsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}



