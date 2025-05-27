package com.coderhouse.apis;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class FechaUtil {

    public static LocalDateTime obtenerFechaDesdeAPI() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://worldclockapi.com/api/json/utc/now"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            String dateTimeStr = json.getString("currentDateTime");

            // Puede venir en formato "2024-05-26T21:50Z", quitamos la Z si es necesario
            dateTimeStr = dateTimeStr.replace("Z", "");

            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(dateTimeStr, formatter);

        } catch (Exception e) {
            System.out.println("⚠️ Error al obtener la fecha desde la API. Usando fecha local.");
            return LocalDateTime.now();
        }
    }
}


