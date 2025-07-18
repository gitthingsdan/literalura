package com.aluracursos.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteApi {
	private static final HttpClient client = HttpClient.newHttpClient();

	public static String enviarSolicitud(String urlString) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(urlString))
				.build();
		HttpResponse<String> response;

		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("Ha ocurrido un error en el envío de la solicitud: " + e.getLocalizedMessage());
			throw new RuntimeException(e);
		}

		return response.body();
	}
}
