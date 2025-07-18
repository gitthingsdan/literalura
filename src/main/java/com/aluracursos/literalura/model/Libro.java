package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;

import java.util.List;

@Entity @Table(name="libros") @JsonIgnoreProperties(ignoreUnknown = true)
public final class Libro {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
	private Long id;
	@JsonAlias("title")
	private String titulo;
	private String idioma;
	@JsonAlias("download_count")
	private int numeroDescargas;
	@ManyToOne
	private Autor autor;

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@JsonSetter("authors")
	public void setAutorFromJson(List<Autor> autores) {
		if (autores != null && !autores.isEmpty()) {
			this.autor = autores.getFirst();
		}
	}

	@JsonSetter("languages")
	public void setIdiomaFromJson(List<String> idiomas) {
		if (idiomas != null && !idiomas.isEmpty()) {
			this.idioma = idiomas.getFirst();
		}
	}

	public int getNumeroDescargas() {
		return numeroDescargas;
	}

	@Override
	public String toString() {
		return "----- LIBRO -----\n" +
				"Título: \t\t\t\t" + titulo + "\n" +
				"Autor: \t\t\t\t\t" + autor.getNombre() + "\n" +
				"Idioma: \t\t\t\t" + idioma + "\n" +
				"Número de descargas: \t" + numeroDescargas + "\n" +
				"-----------------\n";
	}
}
