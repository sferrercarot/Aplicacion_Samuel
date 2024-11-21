package com.example.aplicacion_samuel;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;

import com.google.android.gms.common.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class JugadorAPI {
    String BASE_URL = "https://cmesgiwjcpziurvoeltr.supabase.co/rest/v1/Jugadores_futbol?apikey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNtZXNnaXdqY3B6aXVydm9lbHRyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE2NjE5MjMsImV4cCI6MjA0NzIzNzkyM30.RFFz-C9ihA-spGEBux0-l4BJu2ZCA_3bh5kNkOj-6tk";

    String getNombres(String nombre) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("equipo")
                .build();
        String url = builtUri.toString();
        return doCall(url);
    }

    @OptIn(markerClass = UnstableApi.class)
    public static ArrayList<Jugador> buscar(){
        ArrayList<Jugador> jugadors = new ArrayList<>();
        try{
            String response = HttpUtils.get("https://cmesgiwjcpziurvoeltr.supabase.co/rest/v1/Jugadores_futbol?apikey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNtZXNnaXdqY3B6aXVydm9lbHRyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE2NjE5MjMsImV4cCI6MjA0NzIzNzkyM30.RFFz-C9ihA-spGEBux0-l4BJu2ZCA_3bh5kNkOj-6tk");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jugadorObj = results.getJSONObject(i);
                String nombre = jugadorObj.getString("nombre");
                String url = jugadorObj.getString("url");

                String jugadorsDetailsResponse = HttpUtils.get(url);
                JSONObject jugadorDetails = new JSONObject(jugadorsDetailsResponse);

                Log.d("DEBUG", "JSON de " + nombre + ": " + jugadorDetails);

                int id = jugadorDetails.getInt("id");
                String name = jugadorDetails.getString("name");
                String team = jugadorDetails.getString("team");
                int age = jugadorDetails.getInt("age");
                String nationality = jugadorDetails.getString("nationality");
                double marketValue = jugadorDetails.getDouble("marketValue");
                String position = jugadorDetails.getString("position");
                String image = jugadorDetails.getString("image");

                Jugador jugador = new Jugador(id, name, team, age, nationality, marketValue, position, image);
                jugadors.add(jugador);

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jugadors;
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