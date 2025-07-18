package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.Resultados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static Resultados convertirDatos(String json) {
		objectMapper.enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature());
		try {
			return objectMapper.readValue(json, Resultados.class);
		} catch (JsonProcessingException e) {
			System.out.println("Ha ocurrido un error en la conversión de los datos: " + e.getLocalizedMessage());
			throw new RuntimeException(e);
		}
	}
}
